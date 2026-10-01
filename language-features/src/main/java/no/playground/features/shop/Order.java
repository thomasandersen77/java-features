package no.playground.features.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Order {
    private final List<OrderItem> items;
    private final Customer customer;
    private final UUID id;

    public Order(
            UUID id,
            Customer customer,
            ArrayList<OrderItem> orderItems
    ) {
        if(orderItems.isEmpty()) {
            throw new IllegalArgumentException("OrderItems can not be empty");
        }

        this.items = orderItems;
        this.id = id;
        this.customer = customer;
    }

    public List<OrderItem> getOrderItems() {
        return items;
    }

    public Customer getCustomer() {
        return customer;
    }

    public UUID getId() {
        return id;
    }

    public void addOrder(OrderItem orderItem) {
        isValidItem(orderItem);
        items.add(orderItem);
    }

    private void isValidItem(OrderItem orderItem) {
        Objects.requireNonNull(orderItem);
        Objects.requireNonNull(orderItem.product());
    }
}
