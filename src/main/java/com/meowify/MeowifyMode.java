package com.meowify;

public enum MeowifyMode {
    FURRY("Full Furry (UwU)"),
    MEOW("Pure Meow"),
    PUNS("Cat Puns"),
    SUFFIX("Cat Suffixes");

    private final String displayName;

    MeowifyMode(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static MeowifyMode fromString(String name) {
        for (MeowifyMode mode : values()) {
            if (mode.name().equalsIgnoreCase(name) || mode.displayName.equalsIgnoreCase(name)) {
                return mode;
            }
        }
        if ("uwu".equalsIgnoreCase(name) || "full".equalsIgnoreCase(name)) {
            return FURRY;
        }
        return FURRY;
    }
}
