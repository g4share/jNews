package com.g4share.common.log;

/**
 * User: gm
 */
public interface Logger {
    void logEvent(LogLevel level, String message);
    void setLevel(LogLevel level);
}
