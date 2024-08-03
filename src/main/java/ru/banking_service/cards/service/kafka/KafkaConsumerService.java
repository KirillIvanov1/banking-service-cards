package ru.banking_service.cards.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.banking_service.cards.service.CardService;
import ru.banking_service.cards.util.DTO.MonetaryAmountDTO;
import ru.banking_service.cards.util.DTO.UpdateBalanceDTO;

@Slf4j
@Service
@KafkaListener(topics = "transactions.to.cards")
public class KafkaConsumerService {
    private final CardService cardService;

    @Autowired
    public KafkaConsumerService(CardService cardService) {
        this.cardService = cardService;
    }

    @KafkaHandler
    public void handleUpdateBalance(UpdateBalanceDTO updateBalanceDTO) {
        cardService.updateBalance(updateBalanceDTO.getCardNumber(), MonetaryAmountDTO.convertToMonetaryAmount(updateBalanceDTO.getMonetaryAmount()));
    }
}
