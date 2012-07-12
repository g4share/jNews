package com.g4share.common.log;

import com.g4share.common.except.WrongPropertyException;

/**
 * User: gm
 */
public interface LoggerProperties {
    public String GetPropertyValue(String propertyName) throws WrongPropertyException;
}
