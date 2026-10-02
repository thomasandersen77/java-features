package no.playground.features.shop;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Orkestrerer handlekurv-flyten (pure Java — ikke Spring).
 * Du eier ShoppingCart — denne klassen skal bare koordinere.
 *
 * <p>Øvings-TODOs (Java-features):
 * <ol>
 *   <li>Lag {@code ShoppingCart} (f.eks. record eller klasse med List&lt;OrderItem&gt;).</li>
 *   <li>Bruk compact constructor / validering (som i Product/OrderItem).</li>
 *   <li>Implementer addToCart: finn eksisterende vare eller legg til ny (Streams? plain loop?).</li>
 *   <li>Beregn totalsum med BigDecimal + Stream (map/reduce) — ikke double.</li>
 *   <li>Returner Optional&lt;OrderItem&gt; fra find-metode.</li>
 *   <li>checkout(Customer): bygg Order fra cart (composition du allerede har).</li>
 *   <li>Bonus: sealed interface for CartEvent / pattern matching i en describe-metode.</li>
 *   <li>Bonus: virtual thread bare for moro hvis du simulerer "lagre ordre".</li>
 * </ol>
 *
 * <p>Når du er ferdig her, speil flyten i Spring-skjelettet
 * {@code no.playground.order}.
 */
public class CartService {

    private final Cart cart;

    public CartService(Cart cart) {
        this.cart = cart;
    }

    public void addToCart(Product product, int quantity) {
        cart.addItem(new OrderItem(product, quantity));
        describe(new CartEvent.ItemAdded(product, quantity));
    }

    public void removeFromCart(Product product) {
        cart.removeProduct(product);
        describe(new CartEvent.ItemRemoved(product));
    }

    public Order checkout(Customer customer) {
        validateOrderDetails(customer, cart.getItems());
        Order order = new Order(UUID.randomUUID(), customer, cart.getItems());
        describe(new CartEvent.CartCheckedOut(customer));
        return order;
    }

    private void validateOrderDetails(
            Customer customer,
            List<OrderItem> items) {
        Objects.requireNonNull(customer, "Customer can not be null");
        Objects.requireNonNull(items, "OrderItems can not be null");

        if(items.isEmpty()) {
            throw new IllegalArgumentException("OrderItems can not be empty");
        }
    }

    public BigDecimal getTotal() {
        return cart.total();
    }

    void describe(CartEvent event) {
        switch (event) {
            case CartEvent.ItemAdded(var product, var quantity) ->
                    IO.println("Added %d x %s to cart".formatted(quantity, product.name()));
            case CartEvent.ItemRemoved(var product) ->
                    IO.println("Removed %s from cart".formatted(product.name()));
            case CartEvent.CartCheckedOut(var customer) ->
                    IO.println("Checked out cart for %s".formatted(customer));
        }
    }

    sealed interface CartEvent permits CartEvent.ItemAdded, CartEvent.ItemRemoved, CartEvent.CartCheckedOut {
        record ItemAdded(Product product, int quantity) implements CartEvent {}
        record ItemRemoved(Product product) implements CartEvent {}
        record CartCheckedOut(Customer customer) implements CartEvent {}
    }
}
