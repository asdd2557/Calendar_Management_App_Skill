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

/**
 * BoardController는 게시판과 관련된 API를 제공하는 컨트롤러 클래스입니다.
 * 게시글 생성, 조회, 수정, 삭제 등의 기능을 제공합니다.
 */
@RestController // REST API를 처리하는 컨트롤러
@RequestMapping("/boards") // 모든 메서드의 기본 URL 경로를 "/boards"로 설정
@RequiredArgsConstructor // 생성자를 자동으로 생성해주는 Lombok 어노테이션
public class BoardController {

    private final BoardService boardService; // 게시판 관련 비즈니스 로직을 처리하는 서비스

    /**
     * 게시글 생성 API
     * @param requestDto 게시글 생성 요청 데이터를 담은 DTO
     * @return 생성된 게시글 정보를 담은 DTO와 HTTP 상태코드 201(CREATED)
     */
    @PostMapping
    public ResponseEntity<BoardResponseDto> save(@RequestBody CreateBoardRequestDto requestDto) {
        // 게시글 생성 요청 처리
        BoardResponseDto boardResponseDto = boardService.save(
                requestDto.getTitle(),
                requestDto.getContents(),
                requestDto.getEmail(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        // 처리 결과 반환
        return new ResponseEntity<>(boardResponseDto, HttpStatus.CREATED);
    }

    /**
     * 모든 게시글 조회 API
     * @return 모든 게시글 정보를 담은 DTO 리스트와 HTTP 상태코드 200(OK)
     */
    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> findAll() {
        // 모든 게시글 조회
        List<BoardResponseDto> boardResponseDtoList = boardService.findAll();
        // 처리 결과 반환
        return new ResponseEntity<>(boardResponseDtoList, HttpStatus.OK);
    }

    /**
     * 특정 게시글 조회 API
     * @param id 조회할 게시글의 ID
     * @return 게시글 정보를 담은 DTO와 HTTP 상태코드 200(OK)
     */
    @GetMapping("/{id}")
    public ResponseEntity<BoardWithAgeResponseDto> findByid(@PathVariable Long id) {
        // 특정 게시글 조회
        BoardWithAgeResponseDto boardWithAgeResponseDto = boardService.findById(id);
        // 처리 결과 반환
        return new ResponseEntity<>(boardWithAgeResponseDto, HttpStatus.OK);
    }

    /**
     * 게시글 삭제 API
     * @param id 삭제할 게시글의 ID
     * @return HTTP 상태코드 200(OK)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // 게시글 삭제 요청 처리
        boardService.delete(id);
        // 처리 결과 반환
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 게시글 수정 API
     * @param id 수정할 게시글의 ID
     * @param boardUpdateRequestDto 수정 내용을 담은 DTO
     * @return 수정된 게시글 정보를 담은 DTO와 HTTP 상태코드 200(OK)
     */
    @PatchMapping("/{id}")
    public ResponseEntity<BoardUpdateRequestDto> updateById(
            @PathVariable Long id,
            @RequestBody BoardUpdateRequestDto boardUpdateRequestDto
    ) {
        // 게시글 수정 요청 처리
        BoardUpdateRequestDto boardUpdateRequestDto1 = boardService.update(id, boardUpdateRequestDto);
        // 처리 결과 반환
        return new ResponseEntity<>(boardUpdateRequestDto1, HttpStatus.OK);
    }
}
