package com.freefly19.trackdebts.util;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

import static com.freefly19.trackdebts.security.AuthenticationFilter.AUTHORIZATION_HEADER;

@EnableSwagger2
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {
    @Bean
    public Docket api() {
        SecurityContext securityContext = SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(path -> PathSelectors.ant("/**").apply(path))
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(new ApiKey("Bearer {accessToken}", AUTHORIZATION_HEADER, "header")))
                .securityContexts(Collections.singletonList(securityContext));
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = {new AuthorizationScope("global", "accessEverything")};
        return Collections.singletonList(new SecurityReference("Bearer {accessToken}", authorizationScopes));
    }

}
