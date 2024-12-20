package com.ns.solve.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("solve-proejct")
                .description("hello world")
                .version("1.0.0");

        return new OpenAPI()
                .info(info);

    }


}
