package ru.banking_service.cards.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    private final Long id;
    private final String firstName;
    private final String lastName;
}
