package com.meowify;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Meowifier {
    private static final Random RANDOM = new Random();

    // Cat sounds categorized by length
    private static final String[] SHORT_SOUNDS = { "mew", "nya", "prr", "pur", "mrr" };
    private static final String[] MEDIUM_SOUNDS = { "meow", "mrow", "purr", "nyaa", "mrrow" };
    private static final String[] LONG_SOUNDS = { "meooow", "mrroow", "purrr", "nyaaan", "meeeoow", "purrfect" };

    // Common cat pun replacements for PUNS mode
    private static final Map<String, String> PUN_DICTIONARY = new LinkedHashMap<>();

    static {
        // Multi-word phrases first
        PUN_DICTIONARY.put("right now", "right meow");
        PUN_DICTIONARY.put("all right", "all purr");
        PUN_DICTIONARY.put("what are you doing", "what are you mewing");

        // Single words
        PUN_DICTIONARY.put("now", "meow");
        PUN_DICTIONARY.put("perfect", "purrfect");
        PUN_DICTIONARY.put("perfection", "purrfection");
        PUN_DICTIONARY.put("pause", "paws");
        PUN_DICTIONARY.put("pausing", "pawsing");
        PUN_DICTIONARY.put("sorry", "paw-don me");
        PUN_DICTIONARY.put("hello", "meowdy");
        PUN_DICTIONARY.put("hi", "meow");
        PUN_DICTIONARY.put("hey", "mew");
        PUN_DICTIONARY.put("bye", "b-nya");
        PUN_DICTIONARY.put("goodbye", "good-purr");
        PUN_DICTIONARY.put("friend", "furriend");
        PUN_DICTIONARY.put("friends", "furriends");
        PUN_DICTIONARY.put("please", "purrlease");
        PUN_DICTIONARY.put("cool", "pawsome");
        PUN_DICTIONARY.put("awesome", "pawsome");
        PUN_DICTIONARY.put("great", "grrreat");
        PUN_DICTIONARY.put("good", "purrty good");
        PUN_DICTIONARY.put("family", "furmily");
        PUN_DICTIONARY.put("forever", "fur-ever");
        PUN_DICTIONARY.put("definitely", "defur-nitely");
        PUN_DICTIONARY.put("feeling", "feline");
        PUN_DICTIONARY.put("for", "fur");
        PUN_DICTIONARY.put("very", "furry");
        PUN_DICTIONARY.put("together", "to-purr-ther");
        PUN_DICTIONARY.put("human", "hooman");
        PUN_DICTIONARY.put("humans", "hoomans");
        PUN_DICTIONARY.put("person", "hooman");
        PUN_DICTIONARY.put("people", "hoomans");
        PUN_DICTIONARY.put("cat", "supreme ruler (=^･ω･^=)");
        PUN_DICTIONARY.put("cats", "supreme rulers (=^･ω･^=)");
        PUN_DICTIONARY.put("dog", "barking creature");
        PUN_DICTIONARY.put("dogs", "barking creatures");
        PUN_DICTIONARY.put("food", "tasty treats");
        PUN_DICTIONARY.put("fish", "yummy fishies");
        PUN_DICTIONARY.put("sleep", "catnap");
        PUN_DICTIONARY.put("sleeping", "catnapping");
    }

    private static final String[] SUFFIX_SOUNDS = {
        " ~ meow!",
        " ~ nya~",
        " ~ purr~",
        " ~ mrrow!",
        " :3",
        " (=^･ω･^=)",
        " ~ mew!"
    };

    private static final Pattern URL_PATTERN = Pattern.compile("https?://\\S+");
    private static final Pattern WORD_PATTERN = Pattern.compile("[a-zA-Z0-9]+|[^a-zA-Z0-9]+");

    public static String meowify(String input, MeowifyMode mode, boolean preserveUrls, boolean preserveNumbers, boolean appendCatFace) {
        if (input == null || input.trim().isEmpty()) {
            return input;
        }

        String result;
        switch (mode) {
            case PUNS:
                result = meowifyPuns(input, preserveUrls);
                break;
            case SUFFIX:
                result = meowifySuffix(input);
                break;
            case FULL:
            default:
                result = meowifyFull(input, preserveUrls, preserveNumbers);
                break;
        }

        if (appendCatFace && !result.contains(":3") && !result.contains("(=^･ω･^=)")) {
            result = result + " :3";
        }

        return result;
    }

    private static String meowifyFull(String input, boolean preserveUrls, boolean preserveNumbers) {
        if (preserveUrls) {
            Matcher urlMatcher = URL_PATTERN.matcher(input);
            StringBuilder sb = new StringBuilder();
            int lastEnd = 0;
            while (urlMatcher.find()) {
                String before = input.substring(lastEnd, urlMatcher.start());
                sb.append(meowifyTextSegment(before, preserveNumbers));
                sb.append(urlMatcher.group());
                lastEnd = urlMatcher.end();
            }
            if (lastEnd < input.length()) {
                sb.append(meowifyTextSegment(input.substring(lastEnd), preserveNumbers));
            }
            return sb.toString();
        } else {
            return meowifyTextSegment(input, preserveNumbers);
        }
    }

    private static String meowifyTextSegment(String segment, boolean preserveNumbers) {
        StringBuilder sb = new StringBuilder();
        Matcher matcher = WORD_PATTERN.matcher(segment);

        while (matcher.find()) {
            String token = matcher.group();

            if (isWord(token)) {
                if (preserveNumbers && isNumeric(token)) {
                    sb.append(token);
                } else {
                    String catSound = pickCatSound(token.length());
                    sb.append(applyCasing(token, catSound));
                }
            } else {
                sb.append(token);
            }
        }

        return sb.toString();
    }

    private static String meowifyPuns(String input, boolean preserveUrls) {
        if (preserveUrls) {
            Matcher urlMatcher = URL_PATTERN.matcher(input);
            StringBuilder sb = new StringBuilder();
            int lastEnd = 0;
            while (urlMatcher.find()) {
                String before = input.substring(lastEnd, urlMatcher.start());
                sb.append(meowifyPunsSegment(before));
                sb.append(urlMatcher.group());
                lastEnd = urlMatcher.end();
            }
            if (lastEnd < input.length()) {
                sb.append(meowifyPunsSegment(input.substring(lastEnd)));
            }
            return sb.toString();
        } else {
            return meowifyPunsSegment(input);
        }
    }

    private static String meowifyPunsSegment(String segment) {
        String result = segment;
        boolean replaced = false;

        for (Map.Entry<String, String> entry : PUN_DICTIONARY.entrySet()) {
            String phrase = entry.getKey();
            String replacement = entry.getValue();

            Pattern pattern = Pattern.compile("\\b" + Pattern.quote(phrase) + "\\b", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(result);

            if (matcher.find()) {
                replaced = true;
                StringBuffer buffer = new StringBuffer();
                matcher.reset();
                while (matcher.find()) {
                    String matchedWord = matcher.group();
                    String casedReplacement = applyCasing(matchedWord, replacement);
                    matcher.appendReplacement(buffer, Matcher.quoteReplacement(casedReplacement));
                }
                matcher.appendTail(buffer);
                result = buffer.toString();
            }
        }

        if (!replaced && !segment.trim().isEmpty()) {
            result = result + " ~ meow :3";
        }

        return result;
    }

    private static String meowifySuffix(String input) {
        String trimmed = input.trim();
        String chosenSuffix = SUFFIX_SOUNDS[RANDOM.nextInt(SUFFIX_SOUNDS.length)];

        if (trimmed.endsWith(".") || trimmed.endsWith("!") || trimmed.endsWith("?")) {
            return trimmed + " " + chosenSuffix.trim();
        } else {
            return trimmed + chosenSuffix;
        }
    }

    private static boolean isWord(String token) {
        return token.chars().anyMatch(Character::isLetterOrDigit);
    }

    private static boolean isNumeric(String token) {
        return token.chars().allMatch(Character::isDigit);
    }

    private static String pickCatSound(int wordLength) {
        if (wordLength <= 3) {
            return SHORT_SOUNDS[RANDOM.nextInt(SHORT_SOUNDS.length)];
        } else if (wordLength <= 6) {
            return MEDIUM_SOUNDS[RANDOM.nextInt(MEDIUM_SOUNDS.length)];
        } else {
            return LONG_SOUNDS[RANDOM.nextInt(LONG_SOUNDS.length)];
        }
    }

    private static String applyCasing(String original, String replacement) {
        if (original.isEmpty()) return replacement;

        boolean isAllUpper = original.length() > 1 && original.chars().filter(Character::isLetter).allMatch(Character::isUpperCase);
        if (isAllUpper) {
            return replacement.toUpperCase();
        }

        boolean isFirstUpper = Character.isUpperCase(original.charAt(0));
        if (isFirstUpper) {
            return Character.toUpperCase(replacement.charAt(0)) + replacement.substring(1);
        }

        return replacement.toLowerCase();
    }
}
