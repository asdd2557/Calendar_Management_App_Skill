package org.example.dto;

import lombok.Getter;
import org.example.entity.Board;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {
    private final Long id;
    private final String title;
    private final String contents;
    private final LocalDateTime createTime;
    private final LocalDateTime updateTime;
    public BoardResponseDto(Long id, String title, String contents, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public static BoardResponseDto toDto(Board board){
        return new BoardResponseDto(board.getId(), board.getTitle(), board.getContent(), board.getCreateTime(), board.getLastUpdated());
    }
}
