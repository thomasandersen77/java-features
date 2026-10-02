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
        final var cart = new Cart();
        final var cartService = new CartService(cart);
        final var customer = new Customer();
        final var product = new Product("name", "desc", new BigDecimal("149.90"));
        var item = new OrderItem(product, 2);

        cart.addItem(item);

        var order = cartService.checkout(customer);

        assertNotNull(order.getId());
        assertEquals(customer, order.getCustomer());
        assertEquals(1, order.getOrderItems().size());
        OrderItem orderItem = order.getOrderItems().getFirst();
        assertEquals(2, orderItem.quantity());
        Product product1 = orderItem.product();
        assertEquals(product, product1);
        assertEquals("name", product1.name());
        assertEquals("desc", product1.description());
    }
}
