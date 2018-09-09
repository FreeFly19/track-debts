package com.freefly19.trackdebts.security;

import com.freefly19.trackdebts.user.User;
import com.freefly19.trackdebts.user.UserRepository;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Builder
@Getter
public class UserRequestContext {
    private final long id;
    private final long date;
    private final String email;
    private final String requestId;

    public Timestamp timestamp() {
        return new Timestamp(date);
    }

    public User toUser(UserRepository userRepository) {
        return userRepository.findById(id).orElseThrow(IllegalStateException::new);
    }
}
