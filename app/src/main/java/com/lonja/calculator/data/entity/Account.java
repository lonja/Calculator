package com.lonja.calculator.data.entity;

import android.support.annotation.NonNull;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

@RealmClass
public class Account extends RealmObject {

    public static final String ID = "id";

    public static final String USERNAME = "username";

    public static final String EMAIL = "email";

    public static final String FIRST_NAME = "firstName";

    public static final String LAST_NAME = "lastName";

    public static final String PHONE_NUMBER = "phoneNumber";

    public static final String TOKEN = "token";

    public static final String TOKEN_EXPIRES_DATE = "expires";

    public static final String TOKEN_REFRESH_DATE = "lastRefresh";

    public static final String TOKEN_TYPE = "type";


    @PrimaryKey
    private String id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Token token;

    public Account() {
        id = UUID.randomUUID().toString();
    }

    public Account(String username, String password, String firstName, String lastName, String email,
                   String phoneNumber, Token token) {
        this();
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.token = token;
    }

    public Account(String phoneNumber, String email, String lastName, String firstName,
                   String password, String username) {
        this();
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.password = password;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(@NonNull Token token) {
        this.token = token;
    }
}
