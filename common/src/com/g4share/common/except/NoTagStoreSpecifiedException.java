package com.g4share.common.except;

/**
 * User: gm
 */
public class NoTagStoreSpecifiedException extends Exception {
    public NoTagStoreSpecifiedException() {
        super("Could not found tagStore object.");
    }
}