package no.playground.features;

import java.util.List;

import no.playground.features.shop.CartService;

/**
 * Entry point for experimenting with the latest Java language features.
 * Not a Spring component — run via {@code java} / exec plugin, not Boot.
 */
public class FeaturesApp {

    static void main(String[] args) {
        var runtime = Runtime.version();
        System.out.printf("Running on Java %s%n", runtime.feature());

        examples();
        SealedClassesExample.run();
        cartSketch();
    }

    /** Skjelett-kall — fylles ut når ShoppingCart + CartService er implementert. */
    static void cartSketch() {
        var cartService = new CartService();
        // TODO: cartService.addToCart(new Product(...));
        // TODO: cartService.checkout(new Customer());
        System.out.println("CartService klar: " + cartService.getClass().getSimpleName());
    }

    static void examples() {
        var features = List.of(
                "records",
                "pattern matching",
                "sealed classes",
                "virtual threads",
                "structured concurrency"
        );

        features.stream()
                .map(FeatureDemo::new)
                .forEach(System.out::println);

        System.out.println(describe(new Point(3, 4)));
    }

    static String describe(Object value) {
        return switch (value) {
            case Point(int x, int y) -> "Point at (%d, %d)".formatted(x, y);
            case String s when !s.isBlank() -> "Non-blank string: " + s;
            case String ignored -> "Blank string";
            case null -> "null";
            default -> "Something else: " + value;
        };
    }

    record Point(int x, int y) {}

    record FeatureDemo(String name) {
        @Override
        public String toString() {
            return "Ready to explore: " + name;
        }
    }
}
