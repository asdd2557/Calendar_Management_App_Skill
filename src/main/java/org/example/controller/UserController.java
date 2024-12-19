package org.example.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.LoginRequestDto;
import org.example.dto.LoginResponseDto;
import org.example.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto request,
            HttpServletResponse response // 쿠키값 세팅에 필요
    ) {
        // 로그인 유저 조회
        LoginResponseDto responseDto = memberService.login(request.getEmail(), request.getPassword(), response);

        if (responseDto == null || responseDto.getId() == null) {
            // 로그인 실패 처리
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // 로그인 성공 처리
        Cookie cookie = new Cookie("userId", String.valueOf(responseDto.getId()));
        cookie.setPath("/"); // 쿠키의 경로 설정 (전체 경로에서 유효)
        cookie.setHttpOnly(true); // JavaScript 접근 방지
        response.addCookie(cookie);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        // 쿠키 삭제
        Cookie cookie = new Cookie("userId", null);
        cookie.setPath("/");
        cookie.setMaxAge(0); // 쿠키를 즉시 만료
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return ResponseEntity.ok("Logged out successfully");
    }

}

