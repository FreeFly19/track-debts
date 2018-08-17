package com.freefly19.trackdebts.user;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User registerUser(RegisterUserCommand command) {
        return new User(1L, command.getEmail(), command.getPassword());
    }
}
