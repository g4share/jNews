package com.g4share.common.log;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.g4share.common.except.WrongPropertyException;

/**
 * User: gm
 */
public class FileLogger implements Logger {
    private LogLevel currentLevel = LogLevel.getDefaultLevel();

    private String fileName;
    private Logger innerLogger;

    public FileLogger(LoggerProperties fileProperties, Logger innerLogger) throws WrongPropertyException {
        fileName = fileProperties.GetPropertyValue(FileLoggerProperties.FILE_NAME);
        this.innerLogger = innerLogger;

        if (innerLogger != null) innerLogger.logEvent(LogLevel.TRACE, fileName);
    }

    @Override
    public void setLevel(LogLevel level) {
        this.currentLevel = level;
    }

    @Override
    public void logEvent(LogLevel level, String message) {
        if (currentLevel == null || level == null) return;

        if (level.isHighest(currentLevel)){
            printMessage(level.getDescription()
                    + " "
                    + new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss.SSS ").format(new Date())
                    + " "
                    + message);
        }
    }

    private void printMessage(String message){
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println(message);

        } catch (IOException e){
            if (innerLogger != null) innerLogger.logEvent(LogLevel.FATAL, "error logging message.");
        }
    }
}