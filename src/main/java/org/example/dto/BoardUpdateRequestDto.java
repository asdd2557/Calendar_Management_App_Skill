package org.example.dto;


import lombok.Getter;

@Getter
public class BoardUpdateRequestDto {
    private final String title;
    private final String contents;

    public BoardUpdateRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
