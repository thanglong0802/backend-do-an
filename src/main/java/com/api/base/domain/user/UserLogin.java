package com.api.base.domain.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class UserLogin {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
