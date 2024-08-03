package ru.banking_service.cards.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "ru.banking_service.cards.feignclient")
@Configuration
public class FeignConfig {

}
