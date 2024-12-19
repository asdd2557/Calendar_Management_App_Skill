package org.example.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.*;
import org.example.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * MemberController는 회원 관련 API를 제공하는 컨트롤러 클래스입니다.
 * 회원가입, 회원 조회, 비밀번호 변경 등의 기능을 제공합니다.
 */
@RestController // REST API를 처리하는 컨트롤러임을 나타냄
@RequiredArgsConstructor // 생성자를 자동으로 생성해주는 Lombok 어노테이션
public class MemberController {

    private final MemberService memberService; // 회원 관련 로직을 처리하는 서비스

    /**
     * 회원가입 API
     * @param requestDto 회원가입 요청 데이터를 담은 DTO
     * @return 회원가입 결과를 담은 DTO와 HTTP 상태코드 201(Created)
     */
    @PostMapping("/members/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto requestDto) {
        // 회원가입 요청 처리
        SignUpResponseDto signUpResponseDto = memberService.signUp(
                requestDto.getEmail(),
                requestDto.getPassword()
        );

        // 처리 결과 반환
        return new ResponseEntity<>(signUpResponseDto, HttpStatus.CREATED);
    }

    /**
     * 회원 정보 조회 API
     * @param id 조회할 회원의 ID
     * @return 회원 정보를 담은 DTO와 HTTP 상태코드 200(OK)
     */
    @GetMapping("/members/{id}")
    public ResponseEntity<MemberResponseDto> findById(@PathVariable Long id) {
        // 회원 정보 조회
        MemberResponseDto memberResponseDto = memberService.findById(id);

        // 처리 결과 반환
        return new ResponseEntity<>(memberResponseDto, HttpStatus.OK);
    }

    /**
     * 비밀번호 변경 API
     * @param id 비밀번호를 변경할 회원의 ID
     * @param requestDto 기존 비밀번호와 새로운 비밀번호를 담은 DTO
     * @return HTTP 상태코드 200(OK)
     */
    @PatchMapping("/members/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id,
                                               @RequestBody UpdatePasswordRequestDto requestDto) {
        // 비밀번호 변경 요청 처리
        memberService.updatePassword(id, requestDto.getOldPassword(), requestDto.getNewPassword());

        // 처리 결과 반환
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
