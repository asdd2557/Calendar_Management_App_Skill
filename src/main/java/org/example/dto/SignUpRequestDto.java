package org.example.dto;

import lombok.Getter;

@Getter
public class SignUpRequestDto {
    private final String email;

    private final String password;


    public SignUpRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
