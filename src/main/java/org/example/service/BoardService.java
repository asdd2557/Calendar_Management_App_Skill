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
@RequiredArgsConstructor
public class BoardService {
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public BoardResponseDto save(String title, String contents, String username, LocalDateTime createTime, LocalDateTime updateTime) {
        Member findMember = memberRepository.findMemberByUsernameOrElseThrow(username);

        Board board = new Board(title, contents, createTime, updateTime);
        board.setMember(findMember);
        Board savedBoard = boardRepository.save(board);
        return new BoardResponseDto(savedBoard.getId(), savedBoard.getTitle(), savedBoard.getContent(), createTime, updateTime);
    }

    public List<BoardResponseDto> findAll() {
        return boardRepository.findAll()
                .stream()
                .map(BoardResponseDto::toDto)
                .toList();
    }

    public BoardWithAgeResponseDto findById(Long id) {
        Board findBoard = boardRepository.findByIdOrElseThrow(id);
        Member writer = findBoard.getMember();

        return new BoardWithAgeResponseDto(findBoard.getTitle(), findBoard.getContent());

    }

    public void delete(Long id) {
       Board findBoard =  boardRepository.findByIdOrElseThrow(id);

       boardRepository.delete(findBoard);
    }
    @Transactional
    public BoardUpdateRequestDto update(Long id, BoardUpdateRequestDto boardUpdateRequestDto){
        Board findBoard = boardRepository.findByIdOrElseThrow(id);
        findBoard.updateBoard(boardUpdateRequestDto.getTitle(), boardUpdateRequestDto.getContents());
        return new BoardUpdateRequestDto(findBoard.getTitle(), findBoard.getContent());
    }
}
