package org.example.dto;

import lombok.Getter;

@Getter
public class MemberResponseDto {
    private final String username;


    public MemberResponseDto(String username) {
        this.username = username;

    }
}
