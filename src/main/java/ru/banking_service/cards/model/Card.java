package ru.banking_service.cards.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.hypersistence.utils.hibernate.type.money.MonetaryAmountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.CompositeType;
import org.hibernate.validator.constraints.CreditCardNumber;
import ru.banking_service.cards.util.MonetaryAmountSerializer;

import javax.money.MonetaryAmount;
import java.io.Serializable;

@Entity
@Table(name = "cards")
@NoArgsConstructor(access = AccessLevel.PACKAGE, force = true)
@AllArgsConstructor
@Builder
public class Card implements Serializable {

    @Id
    @Column(name = "card_number", nullable = false, updatable = false, length = 16)
    @Getter
    @CreditCardNumber(ignoreNonDigitCharacters = true)
    private final String cardNumber;

    @Column(name = "owner_id", nullable = false, updatable = false)
    @NotNull
    @Positive
    @Getter
    private final Long ownerId;

    @AttributeOverride(name = "amount", column = @Column(name = "balance", nullable = false))
    @AttributeOverride(name = "currency", column = @Column(name = "currency", nullable = false, updatable = false))
    @CompositeType(MonetaryAmountType.class)
    @NotNull
    @Getter
    @JsonSerialize(using = MonetaryAmountSerializer.class)
    private MonetaryAmount balance;
    @Column(name = "is_active", nullable = false)
    @NotNull
    @Setter
    private boolean isActive;

}
