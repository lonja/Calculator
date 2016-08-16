package com.lonja.calculator.data.entity;

import android.support.annotation.NonNull;

import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

@RealmClass
public class Token extends RealmObject {

    public static final String KEY = "486256";

    @Required
    private String token;

    @Required
    private Date expires;

    private Date lastRefresh;

    @Required
    private String type;

    public Token() {
    }

    public Token(@NonNull String token,
                 @NonNull Date expires,
                 Date lastRefresh,
                 @NonNull String type) {
        this.token = token;
        this.expires = expires;
        this.lastRefresh = lastRefresh;
        this.type = type;
    }

    public static Token generateToken(Account account, String key) {
        Date currentDate = new Date();
        Date expiresDate = new Date(System.currentTimeMillis() + 15552000000L);
        return new Token(Jwts.builder()
                .setSubject(account.getUsername())
                .setSubject(account.getPassword())
                .setExpiration(expiresDate)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact(), expiresDate, currentDate, TokenType.SERVER);
    }

    @NonNull
    public String getToken() {
        return token;
    }

    public void setToken(@NonNull String token) {
        this.token = token;
    }

    @NonNull
    public Date getExpires() {
        return expires;
    }

    public void setExpires(@NonNull Date expires) {
        this.expires = expires;
    }

    public Date getLastRefresh() {
        return lastRefresh;
    }

    public void setLastRefresh(Date lastRefresh) {
        this.lastRefresh = lastRefresh;
    }

    @NonNull
    public String getType() {
        return type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }

    public interface TokenType {

        String FACEBOOK = "facebook";

        String GOOGLE = "google";

        String TWITTER = "twitter";

        String SERVER = "server";
    }
}
