package org.example.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.BoardResponseDto;
import org.example.dto.BoardUpdateRequestDto;
import org.example.dto.BoardWithAgeResponseDto;
import org.example.entity.Board;
import org.example.entity.Member;
import org.example.repository.BoardRepository;
import org.example.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor // 생성자 자동 생성 (DI를 위한 Lombok 어노테이션)
public class BoardService {

    private final MemberRepository memberRepository; // Member 관련 데이터 접근 객체
    private final BoardRepository boardRepository; // Board 관련 데이터 접근 객체

    /**
     * 게시글 저장
     * @param title 게시글 제목
     * @param contents 게시글 내용
     * @param username 작성자 사용자 이름
     * @param createTime 생성 시간
     * @param updateTime 수정 시간
     * @return 저장된 게시글 정보를 포함하는 DTO
     */
    public BoardResponseDto save(String title, String contents, String username, LocalDateTime createTime, LocalDateTime updateTime) {
        // 작성자 조회
        Member findMember = memberRepository.findMemberByUsernameOrElseThrow(username);

        // 게시글 객체 생성 및 작성자 설정
        Board board = new Board(title, contents, createTime, updateTime);
        board.setMember(findMember);

        // 게시글 저장
        Board savedBoard = boardRepository.save(board);

        // 저장된 게시글 정보를 DTO로 반환
        return new BoardResponseDto(savedBoard.getId(), savedBoard.getTitle(), savedBoard.getContent(), createTime, updateTime);
    }

    /**
     * 모든 게시글 조회
     * @return 모든 게시글 정보를 포함하는 DTO 리스트
     */
    public List<BoardResponseDto> findAll() {
        // 모든 게시글을 조회하여 DTO로 변환
        return boardRepository.findAll()
                .stream()
                .map(BoardResponseDto::toDto) // Entity -> DTO 변환
                .toList();
    }

    /**
     * ID를 기준으로 게시글 조회
     * @param id 조회할 게시글 ID
     * @return 게시글 정보와 작성자 정보를 포함하는 DTO
     */
    public BoardWithAgeResponseDto findById(Long id) {
        // 게시글 조회. 존재하지 않으면 예외 발생
        Board findBoard = boardRepository.findByIdOrElseThrow(id);

        // 작성자 정보 가져오기
        Member writer = findBoard.getMember();

        // 게시글 정보를 DTO로 반환
        return new BoardWithAgeResponseDto(findBoard.getTitle(), findBoard.getContent());
    }

    /**
     * 게시글 삭제
     * @param id 삭제할 게시글 ID
     */
    public void delete(Long id) {
        // 게시글 조회. 존재하지 않으면 예외 발생
        Board findBoard = boardRepository.findByIdOrElseThrow(id);

        // 게시글 삭제
        boardRepository.delete(findBoard);
    }

    /**
     * 게시글 수정
     * @param id 수정할 게시글 ID
     * @param boardUpdateRequestDto 수정 내용이 담긴 DTO
     * @return 수정된 게시글 정보를 포함하는 DTO
     */
    @Transactional // 데이터베이스 트랜잭션 관리
    public BoardUpdateRequestDto update(Long id, BoardUpdateRequestDto boardUpdateRequestDto) {
        // 게시글 조회. 존재하지 않으면 예외 발생
        Board findBoard = boardRepository.findByIdOrElseThrow(id);

        // 게시글 정보 업데이트
        findBoard.updateBoard(boardUpdateRequestDto.getTitle(), boardUpdateRequestDto.getContents());

        // 수정된 게시글 정보를 DTO로 반환
        return new BoardUpdateRequestDto(findBoard.getTitle(), findBoard.getContent());
    }
}
