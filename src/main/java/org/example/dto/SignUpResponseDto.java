package org.example.dto;

import lombok.Getter;

@Getter
public class SignUpResponseDto {

    private final Long id;

    private final String email;




    public SignUpResponseDto(Long id, String email) {
        this.id = id;
        this.email = email;

    }
}
