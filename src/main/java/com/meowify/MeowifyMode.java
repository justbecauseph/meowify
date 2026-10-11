package com.meowify;

public enum MeowifyMode {
    FULL("Full Meow"),
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
        return FULL;
    }
}
