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
@RequiredArgsConstructor // 생성자를 자동으로 만들어주는 Lombok 어노테이션
public class MemberService {

    private final MemberRepository memberRepository; // 의존성 주입된 MemberRepository

    /**
     * 회원가입 로직을 처리
     * @param username 회원 이메일
     * @param password 회원 비밀번호
     * @return 회원가입 결과를 포함하는 DTO
     */
    public SignUpResponseDto signUp(String username, String password) {
        // 새로운 Member 객체 생성
        Member member = new Member(username, password);

        // Member 저장
        Member savedMember = memberRepository.save(member);

        // 저장된 정보를 DTO로 변환하여 반환
        return new SignUpResponseDto(savedMember.getId(), savedMember.getEmail());
    }

    /**
     * ID를 통해 회원 정보를 조회
     * @param id 조회하려는 회원 ID
     * @return 조회된 회원 정보를 담은 DTO
     * @throws ResponseStatusException 회원을 찾을 수 없을 때
     */
    public MemberResponseDto findById(Long id) {
        // ID를 기반으로 회원 조회
        Optional<Member> optionalMember = memberRepository.findById(id);

        // 회원이 존재하지 않을 경우 예외 발생
        if (optionalMember.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // Optional에서 Member 객체 가져오기
        Member findMember = optionalMember.get();

        // 조회 결과를 DTO로 반환
        return new MemberResponseDto(findMember.getEmail());
    }

    /**
     * 비밀번호를 변경하는 로직
     * @param id 회원 ID
     * @param oldPassword 기존 비밀번호
     * @param newPassword 새로운 비밀번호
     * @throws ResponseStatusException 비밀번호가 일치하지 않을 때
     */
    @Transactional // 데이터베이스 트랜잭션을 관리
    public void updatePassword(Long id, String oldPassword, String newPassword) {
        // ID로 회원 조회. 존재하지 않으면 예외 발생
        Member findMember = memberRepository.findByIdOrElseThrow(id);

        // 기존 비밀번호 검증
        if (!findMember.getPassword().equals(oldPassword)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        // 비밀번호 업데이트
        findMember.updatePassword(newPassword);
    }

    /**
     * 로그인 로직 처리 및 인증 쿠키 생성
     * @param email 회원 이메일
     * @param password 회원 비밀번호
     * @param response HTTP 응답 객체 (쿠키 설정을 위해 사용)
     * @return 로그인 성공 시 회원 정보를 담은 DTO
     * @throws ResponseStatusException 잘못된 로그인 정보일 때
     */
    public LoginResponseDto login(String email, String password, HttpServletResponse response) {
        // 이메일과 비밀번호로 회원 조회
        Member member = memberRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        // 인증 쿠키 생성
        Cookie authCookie = new Cookie("userId", String.valueOf(member.getId()));
        authCookie.setHttpOnly(true); // JavaScript로 접근 불가
        authCookie.setPath("/"); // 애플리케이션 전체에서 사용 가능
        response.addCookie(authCookie); // HTTP 응답에 쿠키 추가

        // 로그인 결과 반환
        return new LoginResponseDto(member.getId());
    }
}
