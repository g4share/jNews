package com.g4share.common.log;

import java.util.HashMap;
import java.util.Map;
import com.g4share.common.except.WrongPropertyException;

/**
 * User: gm
 */
public class FileLoggerProperties implements LoggerProperties {
    private Map<String, String> properties = new HashMap<>();
    public static final String FILE_NAME = "fileName";

    public FileLoggerProperties(String fileName) {
        properties.put(FILE_NAME, fileName);
    }

    @Override
    public String GetPropertyValue(String propertyName) throws WrongPropertyException {
        if (!properties.containsKey(propertyName)) throw new WrongPropertyException(propertyName);

        return properties.get(propertyName);
    }
}
