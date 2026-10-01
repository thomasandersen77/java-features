package no.playground.features.shop;

import java.math.BigDecimal;
import java.util.Objects;

public record Product(
        int quantity,
        BigDecimal price
) {
    public Product {
       Objects.requireNonNull(price);
       validateQuantity(quantity);
    }

    private void validateQuantity(int quantity) {
        if(quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
    }
}
