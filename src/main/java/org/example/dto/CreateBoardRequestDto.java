package org.example.dto;

import lombok.Getter;

@Getter
public class CreateBoardRequestDto {
    private final String title;

    private final String contents;

    private final String email;


    public CreateBoardRequestDto(String title, String contents, String email) {
        this.title = title;
        this.contents = contents;
        this.email = email;
    }
}
