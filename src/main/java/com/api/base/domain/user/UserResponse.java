package com.api.base.domain.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserResponse {
    private String username;
    private String fullName;
    private String phoneNumber;
    private String email;
}
