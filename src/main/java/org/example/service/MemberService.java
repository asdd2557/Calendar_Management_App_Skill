package org.example.service;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.LoginResponseDto;
import org.example.dto.MemberResponseDto;
import org.example.dto.SignUpResponseDto;
import org.example.entity.Member;
import org.example.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public SignUpResponseDto signUp(String username, String password) {

        Member member = new Member(username, password);

        Member savedMember = memberRepository.save(member);
        return new SignUpResponseDto(savedMember.getId(), savedMember.getEmail());
    }

    public MemberResponseDto findById(Long id) {
        Optional<Member> optionalMember = memberRepository.findById(id);

        if (optionalMember.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Member findMember = optionalMember.get();

        return new MemberResponseDto(findMember.getEmail());

    }

    @Transactional
    public void updatePassword(Long id, String oldPassword, String newPassword) {
        Member findMember = memberRepository.findByIdOrElseThrow(id);
        if (!findMember.getPassword().equals(oldPassword)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }
        findMember.updatePassword(newPassword);
    }

    public LoginResponseDto login(String email, String password, HttpServletResponse response) {
        Member member = memberRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        // 인증 쿠키 생성
        Cookie authCookie = new Cookie("userId", String.valueOf(member.getId()));
        authCookie.setHttpOnly(true); // JavaScript 접근 불가
        authCookie.setPath("/"); // 애플리케이션 전체에서 사용 가능
        response.addCookie(authCookie); // HTTP 응답에 쿠키 추가

        return new LoginResponseDto(member.getId());
    }


}
