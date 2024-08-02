package ru.banking_service.cards.util.DTO;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Length;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.List;

@Data
public class CardEmissionDTO {
    public CurrencyUnit getCurrencyUnit() {
       return Monetary.getCurrency(currencyCode);
    }
    private static final List<CurrencyUnit> CURRENCY_UNITS = Monetary.getCurrencies(new String[0]).stream().toList();
    @CreditCardNumber(ignoreNonDigitCharacters = true, message = "Invalid card number")
    private final String cardNumber;
    @Positive(message = "Owner id cannot be negative")
    @Digits(integer = 1000, fraction = 0, message = "Owner id should be an integer")
    private final Long ownerId;
    @Length(min = 3, max = 3, message = "Currency code should be 3 characters long")
    private final String currencyCode;
}
