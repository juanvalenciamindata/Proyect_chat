package com.chatapp.domain.model;

import com.chatapp.domain.valueobject.UserId;

public class User {

    private final UserId id;
    private final String passwordHash;

    private User(UserId id, String passwordHash) {
        this.id = id;
        this.passwordHash = passwordHash;
    }

    public static User create(String passwordHash) {
        return new User(UserId.generate(), passwordHash);
    }

    public static User rehydrate(UserId id, String passwordHash) {
        return new User(id, passwordHash);
    }

    public UserId getId() {
        return id;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}