package com.cacao.server.service;

import com.cacao.server.model.User;

import java.util.Set;

public interface UserService {
    void AddUser(User user);

    boolean exists(String to);

    void addFriend(User user, String email);
}
