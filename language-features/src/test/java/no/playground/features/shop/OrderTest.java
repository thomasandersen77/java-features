package no.playground.features.shop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class OrderTest {

    @Test
    void createsOrderFromCustomerOrderItemsAndProduct() {
        var customer = new Customer();
        var product = new Product(2, new BigDecimal("149.90"));
        var item = new OrderItem(product);
        var items = new ArrayList<OrderItem>();
        items.add(item);

        var order = new Order(UUID.randomUUID(), customer, items);

        assertNotNull(order.getId());
        assertEquals(customer, order.getCustomer());
        assertEquals(1, order.getOrderItems().size());
        assertEquals(product, order.getOrderItems().getFirst().product());
    }
}
