package org.example.dto;


import lombok.Getter;

@Getter
public class BoardWithAgeResponseDto {
    private final String title;
    private final String contents;


    public BoardWithAgeResponseDto(String title, String contents) {
        this.title = title;
        this.contents = contents;

    }
}
