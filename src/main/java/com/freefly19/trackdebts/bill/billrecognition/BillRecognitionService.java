package com.freefly19.trackdebts.bill.billrecognition;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillRecognitionService {
    public Optional<String> callHttpClient(ReceiveImageCommand command) {
        int statusCode;

        try {
            ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost("http://localhost:5000/image-content");
            request.addHeader("content-type", "application/json");
            request.setEntity(new StringEntity(objectWriter.writeValueAsString(command)));
            HttpResponse response = httpClient.execute(request);

            statusCode = response.getStatusLine().getStatusCode();

            String content = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.of("Connection to server is refused");
        }
        return statusCode >= 400 ? Optional.of("The picture cannot be processed") : Optional.empty();
    }
}
