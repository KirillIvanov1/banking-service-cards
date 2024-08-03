package ru.banking_service.cards.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.jackson.datatype.money.MoneyModule;

@Configuration
public class JacksonMoneyConfig {
    @Bean
    public MoneyModule moneyModule() {
        return new MoneyModule();
    }

}
