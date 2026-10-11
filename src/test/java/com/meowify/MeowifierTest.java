package com.meowify;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MeowifierTest {

    @Test
    public void testNullAndEmpty() {
        assertNull(Meowifier.meowify(null, MeowifyMode.FULL, true, false, false));
        assertEquals("", Meowifier.meowify("", MeowifyMode.FULL, true, false, false));
        assertEquals("   ", Meowifier.meowify("   ", MeowifyMode.FULL, true, false, false));
    }

    @Test
    public void testFullModePreservesPunctuationAndSpacing() {
        String input = "Hello, world! How are you?";
        String result = Meowifier.meowify(input, MeowifyMode.FULL, true, false, false);
        
        assertNotNull(result);
        assertTrue(result.contains(", "));
        assertTrue(result.contains("! "));
        assertTrue(result.endsWith("?"));
        assertEquals(input.split("\\s+").length, result.split("\\s+").length);
    }

    @Test
    public void testFullModePreservesCasing() {
        String upper = "HELLO";
        String title = "Hello";
        String lower = "hello";

        String upperResult = Meowifier.meowify(upper, MeowifyMode.FULL, true, false, false);
        String titleResult = Meowifier.meowify(title, MeowifyMode.FULL, true, false, false);
        String lowerResult = Meowifier.meowify(lower, MeowifyMode.FULL, true, false, false);

        assertEquals(upperResult, upperResult.toUpperCase());
        assertTrue(Character.isUpperCase(titleResult.charAt(0)));
        assertTrue(Character.isLowerCase(titleResult.charAt(1)));
        assertEquals(lowerResult, lowerResult.toLowerCase());
    }

    @Test
    public void testPreserveUrls() {
        String input = "Check this out https://minecraft.net and see!";
        String result = Meowifier.meowify(input, MeowifyMode.FULL, true, false, false);

        assertTrue(result.contains("https://minecraft.net"));
    }

    @Test
    public void testPreserveNumbers() {
        String input = "Coords: 100 64 -200";
        String withPreserve = Meowifier.meowify(input, MeowifyMode.FULL, true, true, false);
        
        assertTrue(withPreserve.contains("100"));
        assertTrue(withPreserve.contains("64"));
        assertTrue(withPreserve.contains("200"));
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
        String result = Meowifier.meowify(input, MeowifyMode.FULL, true, false, true);

        assertTrue(result.contains(":3"));
    }
}
