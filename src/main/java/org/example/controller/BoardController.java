package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.example.dto.BoardResponseDto;
import org.example.dto.BoardUpdateRequestDto;
import org.example.dto.BoardWithAgeResponseDto;
import org.example.dto.CreateBoardRequestDto;
import org.example.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardResponseDto> save(@RequestBody CreateBoardRequestDto requestDto) {

        BoardResponseDto boardResponseDto = boardService.save(requestDto.getTitle(), requestDto.getContents(), requestDto.getEmail(), LocalDateTime.now(), LocalDateTime.now());
        return new ResponseEntity<>(boardResponseDto, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> findAll() {
        List<BoardResponseDto> boardResponseDtoList = boardService.findAll();
        return new ResponseEntity<>(boardResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardWithAgeResponseDto> findByid(@PathVariable Long id) {


        BoardWithAgeResponseDto boardWithAgeResponseDto = boardService.findById(id);

        return new ResponseEntity<>(boardWithAgeResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boardService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BoardUpdateRequestDto> updateById(@PathVariable Long id, @RequestBody BoardUpdateRequestDto boardUpdateRequestDto) {
        BoardUpdateRequestDto boardUpdateRequestDto1 = boardService.update(id, boardUpdateRequestDto);

        return new ResponseEntity<>(boardUpdateRequestDto1, HttpStatus.OK);
    }
}
