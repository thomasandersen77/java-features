package no.playground.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class FeaturesAppTest {

    @Test
    void describePointUsesPatternMatching() {
        assertEquals("Point at (3, 4)", FeaturesApp.describe(new FeaturesApp.Point(3, 4)));
    }

    @Test
    void describeHandlesBlankString() {
        assertEquals("Blank string", FeaturesApp.describe(""));
        assertEquals("Non-blank string: hello", FeaturesApp.describe("hello"));
    }

    @Test
    void runtimeIsAtLeastJava26() {
        assertTrue(Runtime.version().feature() >= 26);
    }
}
