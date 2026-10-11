package com.meowify;

import net.minecraftforge.common.ForgeConfigSpec;

public class MeowifyConfig {
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.BooleanValue ENABLED;
    public static final ForgeConfigSpec.EnumValue<MeowifyMode> MODE;
    public static final ForgeConfigSpec.BooleanValue PLAY_CAT_SOUND;
    public static final ForgeConfigSpec.DoubleValue CAT_SOUND_VOLUME;
    public static final ForgeConfigSpec.BooleanValue CAT_SOUND_PITCH_VARIATION;
    public static final ForgeConfigSpec.BooleanValue PRESERVE_URLS;
    public static final ForgeConfigSpec.BooleanValue PRESERVE_NUMBERS;
    public static final ForgeConfigSpec.BooleanValue APPEND_CAT_FACE;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("Meowify Mod Configuration - I'm a cat!").push("general");

        ENABLED = builder
                .comment("Whether chat meowification is currently active.")
                .define("enabled", true);

        MODE = builder
                .comment("Meowification mode: FULL (all words meowed), PUNS (cat puns), SUFFIX (appends meows to messages).")
                .defineEnum("mode", MeowifyMode.FULL);

        PLAY_CAT_SOUND = builder
                .comment("Play a cat sound on client when sending chat messages.")
                .define("playCatSound", true);

        CAT_SOUND_VOLUME = builder
                .comment("Volume of the cat sound (0.0 to 1.0).")
                .defineInRange("catSoundVolume", 1.0, 0.0, 1.0);

        CAT_SOUND_PITCH_VARIATION = builder
                .comment("Whether to randomize the pitch of the cat meow slightly for variety.")
                .define("catSoundPitchVariation", true);

        PRESERVE_URLS = builder
                .comment("Preserve web URLs in chat messages without meowifying them.")
                .define("preserveUrls", true);

        PRESERVE_NUMBERS = builder
                .comment("Preserve pure numbers (such as coordinates) without meowifying them.")
                .define("preserveNumbers", false);

        APPEND_CAT_FACE = builder
                .comment("Append a cat emoticon (:3) to messages.")
                .define("appendCatFace", false);

        builder.pop();
        SPEC = builder.build();
    }
}
