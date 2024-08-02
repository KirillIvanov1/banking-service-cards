package ru.banking_service.cards.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import javax.money.MonetaryAmount;
import java.io.IOException;

public class MonetaryAmountSerializer extends JsonSerializer<MonetaryAmount> {
    @Override
    public void serialize(MonetaryAmount monetaryAmount, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("amount", monetaryAmount.getNumber().toString());
        jsonGenerator.writeStringField("currency", monetaryAmount.getCurrency().getCurrencyCode());
        jsonGenerator.writeEndObject();
    }
}
