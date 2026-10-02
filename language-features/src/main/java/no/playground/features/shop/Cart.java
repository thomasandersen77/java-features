package no.playground.features.shop;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Cart {

    private final List<OrderItem> orderItems = new ArrayList<>();

    public Cart() {
    }

    public void addItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    public BigDecimal total() {
        return orderItems.stream()
                .map(OrderItem::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public List<OrderItem> getItems() {
        return List.copyOf(orderItems);
    }

    public void removeProduct(Product product) {
        this.orderItems.removeIf(
                orderItem -> orderItem.product().equals(product)
        );
    }
}
