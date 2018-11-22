package com.freefly19.trackdebts.liqpay;

import com.freefly19.trackdebts.security.UserRequestContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LiqpayController {
    private final LiqpayService liqpayService;

    @GetMapping("/liqpay/generate")
    public DataWithSig formDataWithSig(@RequestParam long receiver,
                                       @RequestParam BigDecimal amount,
                                       @ApiIgnore UserRequestContext context) {
        return liqpayService.generate(receiver, amount, context);
    }

    static class DataWithSig {
        public final String data;
        public final String signature;

        public DataWithSig(String data, String signature) {
            this.data = data;
            this.signature = signature;
        }
    }

    @PostMapping("/liqpay/status")
    public void statusCallback(HttpServletRequest request) {
        String data = new String(Base64Utils.decodeFromString(request.getParameter("data")));
        log.info(data);
        liqpayService.onWebHook(data);
    }
}
