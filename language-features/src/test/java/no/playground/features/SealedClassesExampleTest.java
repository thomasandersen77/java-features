package no.playground.features;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class SealedClassesExampleTest {

    @Test
    void describe_coversAllSealedSubtypes() {
        assertEquals(
                "Paid 12500 øre (tx=tx-42)",
                SealedClassesExample.describe(new SealedClassesExample.PaymentResult.Success("tx-42", 12_500L))
        );
        assertEquals(
                "Declined [INSUFFICIENT_FUNDS]: Not enough balance",
                SealedClassesExample.describe(
                        new SealedClassesExample.PaymentResult.Declined("INSUFFICIENT_FUNDS", "Not enough balance"))
        );
        assertEquals(
                "Error [TIMEOUT]: gateway timed out",
                SealedClassesExample.describe(
                        new SealedClassesExample.PaymentResult.Error(
                                "TIMEOUT", new IllegalStateException("gateway timed out")))
        );
    }

    @Test
    void permittedTypes_areNestedRecords() {
        SealedClassesExample.PaymentResult result =
                new SealedClassesExample.PaymentResult.Success("tx-1", 100L);

        assertInstanceOf(SealedClassesExample.PaymentResult.Success.class, result);
        var success = (SealedClassesExample.PaymentResult.Success) result;
        assertEquals("tx-1", success.transactionId());
        assertEquals(100L, success.amountOre());
    }
}
