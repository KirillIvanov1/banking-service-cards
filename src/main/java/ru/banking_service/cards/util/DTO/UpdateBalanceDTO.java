package ru.banking_service.cards.util.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
public class UpdateBalanceDTO {
    private final String cardNumber;
    private final MonetaryAmountDTO monetaryAmount;
}
