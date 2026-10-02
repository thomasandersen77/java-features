package no.playground.features.shop;

import java.math.BigDecimal;
import java.util.Objects;

public record OrderItem(Product product, int quantity) {
    public OrderItem {
        Objects.requireNonNull(product);
        validateQuantity(quantity);
    }

    public BigDecimal price() {
        return product.price()
                .multiply(BigDecimal.valueOf(quantity));
    }

    private void validateQuantity(int quantity) {
        if(quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
    }
}
