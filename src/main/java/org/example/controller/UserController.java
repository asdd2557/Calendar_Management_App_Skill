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

/**
 * UserController는 로그인 및 로그아웃과 관련된 API를 제공하는 컨트롤러 클래스입니다.
 */
@RestController // REST API를 처리하는 컨트롤러임을 나타냄
@RequiredArgsConstructor // 생성자를 자동으로 생성해주는 Lombok 어노테이션
public class UserController {

    private final MemberService memberService; // 사용자 관련 비즈니스 로직을 처리하는 서비스

    /**
     * 로그인 API
     * @param request 로그인 요청 데이터를 담은 DTO (이메일, 비밀번호)
     * @param response HTTP 응답 객체 (쿠키 설정을 위해 사용)
     * @return 로그인 성공 시 사용자 정보를 담은 DTO와 HTTP 상태코드 200(OK)
     * @throws ResponseStatusException 로그인 실패 시 예외 발생
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto request, // 요청 바디에서 이메일과 비밀번호를 받음
            HttpServletResponse response // 쿠키 값을 설정하기 위한 HTTP 응답 객체
    ) {
        // 로그인 요청 처리
        LoginResponseDto responseDto = memberService.login(request.getEmail(), request.getPassword(), response);

        // 로그인 실패 처리: 사용자 정보가 없거나 ID가 null인 경우 예외 발생
        if (responseDto == null || responseDto.getId() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // 로그인 성공 처리: 인증 쿠키 생성 및 설정
        Cookie cookie = new Cookie("userId", String.valueOf(responseDto.getId()));
        cookie.setPath("/"); // 쿠키가 애플리케이션 전체에서 유효하도록 설정
        cookie.setHttpOnly(true); // JavaScript로 접근 불가 설정
        response.addCookie(cookie); // HTTP 응답에 쿠키 추가

        // 로그인 성공 결과 반환
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * 로그아웃 API
     * @param response HTTP 응답 객체 (쿠키 제거를 위해 사용)
     * @return 로그아웃 성공 메시지와 HTTP 상태코드 200(OK)
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        // 로그아웃 처리: 인증 쿠키 제거
        Cookie cookie = new Cookie("userId", null); // 쿠키 값을 null로 설정
        cookie.setPath("/"); // 쿠키가 애플리케이션 전체에서 제거되도록 설정
        cookie.setMaxAge(0); // 쿠키를 즉시 만료
        cookie.setHttpOnly(true); // JavaScript로 접근 불가 설정
        response.addCookie(cookie); // HTTP 응답에 쿠키 추가

        // 로그아웃 성공 메시지 반환
        return ResponseEntity.ok("Logged out successfully");
    }
}
