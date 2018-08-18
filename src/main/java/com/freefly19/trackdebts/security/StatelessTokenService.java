package com.freefly19.trackdebts.security;

import com.freefly19.trackdebts.user.User;

public interface StatelessTokenService {
    String createToken(User user);
}
