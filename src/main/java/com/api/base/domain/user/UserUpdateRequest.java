package com.api.base.domain.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class UserUpdateRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String fullName;
    @NotNull
    private Integer phoneNumber;
    @NotBlank
    private String email;
}
