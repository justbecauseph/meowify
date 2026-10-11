package com.meowify;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Meowifier {
    private static final Random RANDOM = new Random();

    // Cat sounds for pure meow mode
    private static final String[] SHORT_SOUNDS = { "mew", "nya", "prr", "pur", "mrr" };
    private static final String[] MEDIUM_SOUNDS = { "meow", "mrow", "purr", "nyaa", "mrrow" };
    private static final String[] LONG_SOUNDS = { "meooow", "mrroow", "purrr", "nyaaan", "meeeoow", "purrfect" };

    // Furry / Cat vocabulary
    private static final Map<String, String> FURRY_DICTIONARY = new LinkedHashMap<>();

    static {
        // Multi-word phrases first
        FURRY_DICTIONARY.put("right now", "right meow");
        FURRY_DICTIONARY.put("all right", "all purr");
        FURRY_DICTIONARY.put("what are you doing", "wat awe u mewing");
        FURRY_DICTIONARY.put("thank you", "thankies");

        // Single words
        FURRY_DICTIONARY.put("hello", "hewwo");
        FURRY_DICTIONARY.put("hi", "haii");
        FURRY_DICTIONARY.put("hey", "heyy");
        FURRY_DICTIONARY.put("now", "meow");
        FURRY_DICTIONARY.put("perfect", "purrfect");
        FURRY_DICTIONARY.put("perfection", "purrfection");
        FURRY_DICTIONARY.put("pause", "paws");
        FURRY_DICTIONARY.put("pausing", "pawsing");
        FURRY_DICTIONARY.put("sorry", "paw-don me");
        FURRY_DICTIONARY.put("please", "purrlease");
        FURRY_DICTIONARY.put("thanks", "fankies");
        FURRY_DICTIONARY.put("awesome", "pawsome");
        FURRY_DICTIONARY.put("great", "grrreat");
        FURRY_DICTIONARY.put("good", "purrty good");
        FURRY_DICTIONARY.put("cool", "claw-some");
        FURRY_DICTIONARY.put("friend", "furriend");
        FURRY_DICTIONARY.put("friends", "furriends");
        FURRY_DICTIONARY.put("forever", "fur-ever");
        FURRY_DICTIONARY.put("definitely", "defur-nitely");
        FURRY_DICTIONARY.put("feeling", "feline");
        FURRY_DICTIONARY.put("family", "furmily");
        FURRY_DICTIONARY.put("small", "smol");
        FURRY_DICTIONARY.put("cute", "c-cute :3");
        FURRY_DICTIONARY.put("human", "hooman");
        FURRY_DICTIONARY.put("humans", "hoomans");
        FURRY_DICTIONARY.put("person", "hooman");
        FURRY_DICTIONARY.put("people", "hoomans");
        FURRY_DICTIONARY.put("cat", "kitty");
        FURRY_DICTIONARY.put("cats", "kitties");
        FURRY_DICTIONARY.put("dog", "barking doggo");
        FURRY_DICTIONARY.put("dogs", "barking doggos");
        FURRY_DICTIONARY.put("food", "tasty treats");
        FURRY_DICTIONARY.put("fish", "yummy fishies");
        FURRY_DICTIONARY.put("sleep", "catnap");
        FURRY_DICTIONARY.put("sleeping", "catnapping");
        FURRY_DICTIONARY.put("stop", "stawp");
        FURRY_DICTIONARY.put("god", "gosh");
        FURRY_DICTIONARY.put("boy", "good boy :3");
        FURRY_DICTIONARY.put("girl", "good girl :3");
        FURRY_DICTIONARY.put("have", "haz");
        FURRY_DICTIONARY.put("has", "haz");
        FURRY_DICTIONARY.put("the", "da");
        FURRY_DICTIONARY.put("this", "dis");
        FURRY_DICTIONARY.put("that", "dat");
        FURRY_DICTIONARY.put("these", "dese");
        FURRY_DICTIONARY.put("those", "dose");
        FURRY_DICTIONARY.put("with", "wif");
        FURRY_DICTIONARY.put("you", "chu");
        FURRY_DICTIONARY.put("your", "ur");
        FURRY_DICTIONARY.put("what", "wat");
        FURRY_DICTIONARY.put("love", "wuv");
        FURRY_DICTIONARY.put("loved", "wuvved");
    }

    private static final String[] FURRY_ACTIONS = {
        " *purrs*",
        " *meows*",
        " *swishes tail*",
        " *nuzzles*",
        " *paw pats*",
        " *stretches*",
        " *tilts head*",
        " *blushes*",
        " *pounces*"
    };

    private static final String[] FURRY_KAOMOJI = {
        " :3",
        " >w<",
        " owo",
        " uwu",
        " ^w^",
        " rawr x3",
        " (=^･ω･^=)",
        " nya~",
        " ;w;",
        " TwT",
        " x3"
    };

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
            case FURRY:
                result = meowifyFurry(input, preserveUrls, preserveNumbers);
                break;
            case MEOW:
                result = meowifyPureMeow(input, preserveUrls, preserveNumbers);
                break;
            case PUNS:
                result = meowifyPuns(input, preserveUrls);
                break;
            case SUFFIX:
            default:
                result = meowifySuffix(input);
                break;
        }

        if (appendCatFace && !result.endsWith(":3")) {
            result = result + " :3";
        }

        return result;
    }

    /**
     * Create: Estrogen-style Full Furry / UwUfication!
     * Combines vocabulary, stuttering, nya-fication, phonetic r/l -> w, actions, and kaomoji.
     */
    private static String meowifyFurry(String input, boolean preserveUrls, boolean preserveNumbers) {
        if (preserveUrls) {
            Matcher urlMatcher = URL_PATTERN.matcher(input);
            StringBuilder sb = new StringBuilder();
            int lastEnd = 0;
            while (urlMatcher.find()) {
                String before = input.substring(lastEnd, urlMatcher.start());
                sb.append(meowifyFurrySegment(before, preserveNumbers));
                sb.append(urlMatcher.group());
                lastEnd = urlMatcher.end();
            }
            if (lastEnd < input.length()) {
                sb.append(meowifyFurrySegment(input.substring(lastEnd), preserveNumbers));
            }
            return sb.toString();
        } else {
            return meowifyFurrySegment(input, preserveNumbers);
        }
    }

    private static String meowifyFurrySegment(String segment, boolean preserveNumbers) {
        if (segment.trim().isEmpty()) {
            return segment;
        }

        // 1. Dictionary replacements (multi-word & word-level)
        String transformed = segment;
        for (Map.Entry<String, String> entry : FURRY_DICTIONARY.entrySet()) {
            String phrase = entry.getKey();
            String replacement = entry.getValue();

            Pattern pattern = Pattern.compile("\\b" + Pattern.quote(phrase) + "\\b", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(transformed);

            if (matcher.find()) {
                StringBuffer buffer = new StringBuffer();
                matcher.reset();
                while (matcher.find()) {
                    String matchedWord = matcher.group();
                    String casedReplacement = applyCasing(matchedWord, replacement);
                    matcher.appendReplacement(buffer, Matcher.quoteReplacement(casedReplacement));
                }
                matcher.appendTail(buffer);
                transformed = buffer.toString();
            }
        }

        // 2. Tokenize and apply phonetics (r/l -> w, nya, stutter)
        StringBuilder sb = new StringBuilder();
        Matcher matcher = WORD_PATTERN.matcher(transformed);

        while (matcher.find()) {
            String token = matcher.group();

            if (isWord(token)) {
                if (preserveNumbers && isNumeric(token)) {
                    sb.append(token);
                } else {
                    String processed = token;

                    // Stuttering (20% chance on words with length >= 3)
                    if (processed.length() >= 3 && Character.isLetter(processed.charAt(0)) && RANDOM.nextDouble() < 0.20) {
                        char first = processed.charAt(0);
                        processed = first + "-" + processed;
                    }

                    // Nya-fication (na/ne/ni/no/nu -> nya/nye/nyi/nyo/nyu)
                    processed = applyNya(processed);

                    // Phonetic UwU (r/l -> w, R/L -> W)
                    processed = applyPhonetics(processed);

                    sb.append(processed);
                }
            } else {
                // Punctuation transformation
                String punct = token;
                if (punct.contains("!")) {
                    punct = punct.replace("!", RANDOM.nextDouble() < 0.5 ? "~!! >w<" : "! :3");
                } else if (punct.contains("?")) {
                    punct = punct.replace("?", RANDOM.nextDouble() < 0.5 ? "? owo?" : "? mew?");
                }
                sb.append(punct);
            }
        }

        String result = sb.toString().trim();

        // 3. Add furry action / kaomoji at end if not already expressive
        if (!hasEmote(result)) {
            if (RANDOM.nextDouble() < 0.45) {
                result = result + FURRY_ACTIONS[RANDOM.nextInt(FURRY_ACTIONS.length)];
            }
            result = result + FURRY_KAOMOJI[RANDOM.nextInt(FURRY_KAOMOJI.length)];
        }

        return result;
    }

    private static String applyNya(String word) {
        Matcher m = Pattern.compile("(?i)(?<![yY])n([aeiou])").matcher(word);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            String match = m.group();
            char first = match.charAt(0);
            char second = match.charAt(1);
            String rep;
            if (Character.isUpperCase(first)) {
                rep = Character.isUpperCase(second) ? ("NY" + second) : ("Ny" + second);
            } else {
                rep = "ny" + second;
            }
            m.appendReplacement(sb, Matcher.quoteReplacement(rep));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    private static String applyPhonetics(String word) {
        // r and l -> w
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (c == 'r' || c == 'l') {
                sb.append('w');
            } else if (c == 'R' || c == 'L') {
                sb.append('W');
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static boolean hasEmote(String text) {
        return text.contains(":3") || text.contains("owo") || text.contains("uwu")
                || text.contains(">w<") || text.contains("^w^") || text.contains("(=^･ω･^=)")
                || text.contains("rawr") || text.contains("*");
    }

    /**
     * Pure meow mode: all words replaced with literal cat sounds.
     */
    private static String meowifyPureMeow(String input, boolean preserveUrls, boolean preserveNumbers) {
        if (preserveUrls) {
            Matcher urlMatcher = URL_PATTERN.matcher(input);
            StringBuilder sb = new StringBuilder();
            int lastEnd = 0;
            while (urlMatcher.find()) {
                String before = input.substring(lastEnd, urlMatcher.start());
                sb.append(meowifyPureMeowSegment(before, preserveNumbers));
                sb.append(urlMatcher.group());
                lastEnd = urlMatcher.end();
            }
            if (lastEnd < input.length()) {
                sb.append(meowifyPureMeowSegment(input.substring(lastEnd), preserveNumbers));
            }
            return sb.toString();
        } else {
            return meowifyPureMeowSegment(input, preserveNumbers);
        }
    }

    private static String meowifyPureMeowSegment(String segment, boolean preserveNumbers) {
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

        for (Map.Entry<String, String> entry : FURRY_DICTIONARY.entrySet()) {
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
