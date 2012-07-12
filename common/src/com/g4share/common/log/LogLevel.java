package com.g4share.common.log;

/**
 * User: gm
 */
public enum LogLevel {
    NONE (-10, ""),
    CRITICAL (0, "$"),
    FATAL(1, "!"),
    ERROR(2, "*"),
    INFO (3, " "),
    TRACE(4, "?"),
    ALL(10, "~");

    private int code;
    private String description;

    private LogLevel(int code, String description) {
        this.code = code;
        this.description = description;
    }

    static public LogLevel getDefaultLevel() {
        return LogLevel.INFO;
    }

    public String getDescription() {
        return description;
    }

    public static LogLevel fromString(String text) {
        if (text != null) {
            for (LogLevel level : LogLevel.values()) {
                if (level.name().equalsIgnoreCase(text)){
                    return level;
                }
            }
        }
        return getDefaultLevel();
    }

    public boolean isHighest(LogLevel currentLevel) {
        return code <= currentLevel.code;
    }
}

