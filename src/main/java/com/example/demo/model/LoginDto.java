package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class LoginDto {
    @NotEmpty
    private String userId;
    @NotEmpty
    private String password;
}
