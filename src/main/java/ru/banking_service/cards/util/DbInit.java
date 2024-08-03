package ru.banking_service.cards.util;

import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.banking_service.cards.model.Card;
import ru.banking_service.cards.repository.CardRepository;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Component
public class DbInit {
    @Autowired
    private CardRepository cardRepository;

    @PostConstruct
    private void dbInit() {
        Card card = new Card("5311912361550519", Long.parseLong("1"), Money.of(new BigDecimal("1000"), "RUB"), true);
        cardRepository.save(card);
        card = new Card("5138846902997204", Long.parseLong("2"), Money.of(new BigDecimal("537.55"), "RUB"), true);
        cardRepository.save(card);
        card = new Card("5128698861045331", Long.parseLong("3"), Money.of(new BigDecimal("250000.89"), "RUB"), true);
        cardRepository.save(card);
    }
}
