package no.playground.features.shop;

import java.math.BigDecimal;
import java.util.Objects;

public record Product(
        String name,
        String description,
        BigDecimal price
) {
    public Product {
       Objects.requireNonNull(price);
       Objects.requireNonNull(name);
       validateName(name);
    }

    private void validateName(String name) {
        if(name.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be blank");
        }
    }
}
