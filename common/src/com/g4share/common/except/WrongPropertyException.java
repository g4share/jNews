package com.g4share.common.except;

/**
 * User: gm
 */
public class WrongPropertyException  extends Exception {
    public WrongPropertyException(String propertyName) {
        super("Could not found property: " + propertyName);
    }
}