package com.zsq.service;

import com.zsq.pojo.User;

public interface UserService {
    boolean login(User user);

    boolean changePassword(User user);

    boolean sendByEmail(String email, String code);

    User selectUserByEmail(String email);

    void addUser(User newUser);
}
