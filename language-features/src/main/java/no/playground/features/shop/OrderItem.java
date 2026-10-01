package no.playground.features.shop;

import java.util.Objects;

public record OrderItem(Product product) {
    public OrderItem {
        Objects.requireNonNull(product);
    }
}
