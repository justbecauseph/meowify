package com.meowify;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MeowifierTest {

    @Test
    public void testNullAndEmpty() {
        assertNull(Meowifier.meowify(null, MeowifyMode.FURRY, true, false, false));
        assertEquals("", Meowifier.meowify("", MeowifyMode.FURRY, true, false, false));
        assertEquals("   ", Meowifier.meowify("   ", MeowifyMode.FURRY, true, false, false));
    }

    @Test
    public void testFurryModeTransformsPhoneticsAndVocabulary() {
        String input = "Hello world! I am looking for diamonds right now.";
        String result = Meowifier.meowify(input, MeowifyMode.FURRY, true, false, false);

        assertNotNull(result);
        // "Hello" should become "Hewwo" (or with stutter "H-Hewwo")
        assertTrue(result.toLowerCase().contains("hewwo"));
        // "right now" should become "right meow" (or "wight meow")
        assertTrue(result.toLowerCase().contains("meow"));
        // "looking" should turn 'l' into 'w' -> "wooking"
        assertTrue(result.toLowerCase().contains("wooking"));
        // Should contain cat/furry emotes or actions
        assertTrue(result.contains(":3") || result.contains("owo") || result.contains("uwu")
                || result.contains(">w<") || result.contains("^w^") || result.contains("*")
                || result.contains("nya"));
    }

    @Test
    public void testFurryModePreservesUrls() {
        String input = "Check this rule at https://minecraft.net/rules right now";
        String result = Meowifier.meowify(input, MeowifyMode.FURRY, true, false, false);

        assertTrue(result.contains("https://minecraft.net/rules"));
        assertTrue(result.toLowerCase().contains("meow"));
    }

    @Test
    public void testFurryModePreservesNumbers() {
        String input = "Coords: 100 64 -200";
        String withPreserve = Meowifier.meowify(input, MeowifyMode.FURRY, true, true, false);

        assertTrue(withPreserve.contains("100"));
        assertTrue(withPreserve.contains("64"));
        assertTrue(withPreserve.contains("200"));
    }

    @Test
    public void testPureMeowModePreservesPunctuationAndSpacing() {
        String input = "Hello, world! How are you?";
        String result = Meowifier.meowify(input, MeowifyMode.MEOW, true, false, false);

        assertNotNull(result);
        assertTrue(result.contains(", "));
        assertTrue(result.contains("! "));
        assertTrue(result.endsWith("?"));
        assertEquals(input.split("\\s+").length, result.split("\\s+").length);
    }

    @Test
    public void testPunsMode() {
        String input = "I need that right now please!";
        String result = Meowifier.meowify(input, MeowifyMode.PUNS, true, false, false);

        assertTrue(result.toLowerCase().contains("right meow"));
        assertTrue(result.toLowerCase().contains("purrlease"));
    }

    @Test
    public void testSuffixMode() {
        String input = "Good morning everyone";
        String result = Meowifier.meowify(input, MeowifyMode.SUFFIX, true, false, false);

        assertTrue(result.startsWith("Good morning everyone"));
        assertTrue(result.length() > input.length());
    }

    @Test
    public void testAppendCatFace() {
        String input = "Hello";
        String result = Meowifier.meowify(input, MeowifyMode.FURRY, true, false, true);

        assertTrue(result.endsWith(":3"));
    }
}
