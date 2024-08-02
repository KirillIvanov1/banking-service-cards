package ru.banking_service.cards.util.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;

public class MonetaryAmountDTO {
    @NotNull
    @Positive
    @Getter
    @Setter
    private final BigDecimal amount;

    @NotNull
    @Size(min = 3, max = 3)
    @Getter
    @Setter
    private final String currency;


    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public MonetaryAmountDTO(@JsonProperty("amount") BigDecimal amount,
                             @JsonProperty("currency") String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static MonetaryAmount convertToMonetaryAmount(MonetaryAmountDTO monetaryAmountDTO) {
        if (monetaryAmountDTO.amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(" Input MonetaryAmount amount cannot be less or equal to zero");
        }
        return Money.of(monetaryAmountDTO.amount, monetaryAmountDTO.currency);
    }

    public static MonetaryAmountDTO of(MonetaryAmount monetaryAmount) {
        Money money = (Money) monetaryAmount;
        return new MonetaryAmountDTO(money.getNumberStripped(), money.getCurrency().getCurrencyCode());
    }
}

