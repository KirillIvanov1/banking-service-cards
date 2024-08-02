package ru.banking_service.cards.controller;

import jakarta.validation.Valid;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.banking_service.cards.model.Card;
import ru.banking_service.cards.service.CardService;
import ru.banking_service.cards.util.DTO.CardEmissionDTO;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping(value = "/cards/emit",
        consumes = MediaType.APPLICATION_JSON_VALUE)
    public void emit(@RequestBody @Valid CardEmissionDTO cardEmissionDTO) {
        cardService.emitCard(cardEmissionDTO);
    }

    @GetMapping(value = "/cards",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCardsByOwnerId(@RequestParam Long ownerId) {
        List<Card> cards = cardService.getCards(ownerId);
        if (!cards.isEmpty()) {
            return new ResponseEntity<>(cards, HttpStatus.OK);
        }
        return new ResponseEntity<>("No cards found for this owner", HttpStatus.OK);
    }
    @GetMapping(value = "/card",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCard(@RequestParam @CreditCardNumber(ignoreNonDigitCharacters = true) String cardNumber) {
        Card card = cardService.getCard(cardNumber);
        if (card != null) {
            return new ResponseEntity<>(card, HttpStatus.OK);
        }
        return new ResponseEntity<>("No card with such number found", HttpStatus.OK);
    }

    //public ResponseEntity<Boolean> tellIfCardWithThisNumberExists

}
