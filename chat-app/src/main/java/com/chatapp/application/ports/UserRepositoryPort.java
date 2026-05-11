package com.chatapp.application.ports;

import com.chatapp.domain.model.User;
import com.chatapp.domain.valueobject.UserId;

import java.util.Optional;

public interface UserRepositoryPort {

    void save(User user);

    Optional<User> findById(UserId id);
}