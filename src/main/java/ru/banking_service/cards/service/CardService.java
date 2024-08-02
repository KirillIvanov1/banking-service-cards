package ru.banking_service.cards.service;

import jakarta.validation.Valid;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.banking_service.cards.feignclient.UserFeignClient;
import ru.banking_service.cards.model.Card;
import ru.banking_service.cards.model.User;
import ru.banking_service.cards.repository.CardRepository;
import ru.banking_service.cards.util.ApplicationException;
import ru.banking_service.cards.util.DTO.CardEmissionDTO;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    private CardRepository cardRepository;
    private UserFeignClient userFeignClient;

    @Autowired
    public CardService(CardRepository cardRepository, UserFeignClient userFeignClient) {
        this.cardRepository = cardRepository;
        this.userFeignClient = userFeignClient;
    }

    public void updateBalance(String cardNumber, MonetaryAmount balance) {
        Card cardBefore = getCard(cardNumber);
        Card cardAfter = Card.builder()
                .balance(balance)
                .isActive(true)
                .cardNumber(cardNumber)
                .ownerId(cardBefore.getOwnerId())
                .build();
        cardRepository.save(cardAfter);
    }

    @Transactional
    public Card getCard(String cardNumber) {
        Optional<Card> card = cardRepository.findByCardNumber(cardNumber);
        return card.orElse(null);
    }

    @Transactional
    public List<Card> getCards(Long ownerId) {
        Optional<List<Card>> cards = cardRepository.findByOwnerId(ownerId);
        return cards.orElse(null);
    }

    @Transactional
    public boolean cardWithThisNumberExists(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber).isPresent();
    }


    @Transactional
    public Card emitCard(@Valid CardEmissionDTO cardEmissionDTO) {
        String cardNumber = cardEmissionDTO.getCardNumber();
        if (cardWithThisNumberExists(cardNumber)) {
            throw new ApplicationException(
                    "card-already-exists",
                    String.format("Card with number=%s already exists", cardNumber),
                    HttpStatus.BAD_REQUEST);

        }
        //проверяем, существует ли юзер
        Long ownerId = cardEmissionDTO.getOwnerId();
        String currencyCode;
        //проверяем, является ли переданное значение настоящей валютой
        try {
            CurrencyUnit currencyUnit = cardEmissionDTO.getCurrencyUnit();
            currencyCode = currencyUnit.getCurrencyCode();
        } catch (MonetaryException e) {
            throw new ApplicationException("unknown-currency",
                    String.format("Provided currency with currencyCode=%s does not exist", cardEmissionDTO.getCurrencyCode()),
                    HttpStatus.BAD_REQUEST);
        }
        System.out.println("Before getting user with FeignClient");
        ResponseEntity<User> response = userFeignClient.getUser(ownerId);
        User user = response.getBody();
        System.out.println(user);
        if (user == null || user.getId() == null) {
            throw new ApplicationException(
                    "user-does-not-exist",
                    String.format("User with id=%s does not exist", ownerId.toString()),
                    HttpStatus.BAD_REQUEST
            );
        }
        Card card = new Card(cardNumber, ownerId, Money.of(BigDecimal.ZERO, currencyCode), true);
        cardRepository.save(card);
        return card;
    }
}
