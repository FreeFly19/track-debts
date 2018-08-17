package com.freefly19.trackdebts.user;

import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    public Long id;
    public String email;
    public String password;
}
