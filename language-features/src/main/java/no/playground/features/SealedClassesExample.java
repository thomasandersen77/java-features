package no.playground.features;

/**
 * Sealed classes (Java 17): restrict which types may implement/extend a type.
 * Combined with pattern matching for switch (Java 21), the compiler can prove exhaustiveness.
 */
public final class SealedClassesExample {

    private SealedClassesExample() {}

    /** Closed set of payment outcomes — only the types in {@code permits} may implement this. */
    public sealed interface PaymentResult
            permits PaymentResult.Success, PaymentResult.Declined, PaymentResult.Error {

        record Success(String transactionId, long amountOre) implements PaymentResult {}

        record Declined(String reasonCode, String message) implements PaymentResult {}

        record Error(String code, Throwable cause) implements PaymentResult {}
    }

    /** Exhaustive switch — no {@code default} needed because the hierarchy is sealed. */
    public static String describe(PaymentResult result) {
        return switch (result) {
            case PaymentResult.Success(var txId, var amount) ->
                    "Paid %d øre (tx=%s)".formatted(amount, txId);
            case PaymentResult.Declined(var reason, var message) ->
                    "Declined [%s]: %s".formatted(reason, message);
            case PaymentResult.Error(var code, var cause) ->
                    "Error [%s]: %s".formatted(code, cause.getMessage());
        };
    }

    public static void run() {
        var samples = java.util.List.of(
                new PaymentResult.Success("tx-42", 12_500L),
                new PaymentResult.Declined("INSUFFICIENT_FUNDS", "Not enough balance"),
                new PaymentResult.Error("TIMEOUT", new IllegalStateException("gateway timed out"))
        );

        System.out.println("--- sealed classes ---");
        samples.forEach(r -> System.out.println(describe(r)));
    }
}
