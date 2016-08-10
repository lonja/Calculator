package com.lonja.calculator.common.authentication;


public interface GoogleCallback<T> {

    void onSuccess(T result);

    void onError(Throwable error);

}