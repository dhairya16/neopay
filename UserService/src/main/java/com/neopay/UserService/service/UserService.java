package com.neopay.UserService.service;


import com.neopay.UserService.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);

    Optional<User> getUser(Long id);

    List<User> getUsers();
}