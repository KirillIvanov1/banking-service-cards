package ru.banking_service.cards.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.banking_service.cards.model.Card;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByCardNumber (String cardNumber);
    Optional<List<Card>> findByOwnerId (Long ownerId);
}
