package com.lonja.calculator.data.exception;

import android.support.annotation.NonNull;

public class EmailException extends RuntimeException {

    public EmailException(@NonNull String message) {
        super(message);
    }

}
