package no.playground.features.shop;

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

    // TODO: hold på (eller motta) ShoppingCart — din modell
    // private final ShoppingCart cart;

    public CartService() {
        // TODO: initialiser cart
    }

    public void addToCart(Product product) {
        // TODO: valider product, wrap i OrderItem, legg i cart
        throw new UnsupportedOperationException("not implemented — din tur");
    }

    public void removeFromCart(Product product) {
        // TODO
        throw new UnsupportedOperationException("not implemented — din tur");
    }

    public Order checkout(Customer customer) {
        // TODO: cart -> Order(id, customer, items)
        throw new UnsupportedOperationException("not implemented — din tur");
    }
}
