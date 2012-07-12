package com.g4share.common;

/**
 * User: gm
 */
public final class Constants {
    public static final int WRONG_NUMBER = Integer.MIN_VALUE;
    public enum ResultCode {
        FATAL_ERROR_CODE(-1, true),
        SUCCESS_CODE(0, false),
        ERROR_CODE(1, true),
        INTERRUPTED_CODE(2, false);

        private int code;
        private boolean error;

        private ResultCode(int code, boolean error) {
            this.code = code;
            this.error = error;
        }

        public boolean isError(){
            return error;
        }
    }
}