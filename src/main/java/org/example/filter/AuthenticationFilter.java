package org.example.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AuthenticationFilter implements Filter {

    // 화이트리스트 경로를 관리하는 리스트
    private static final List<String> WHITELIST = List.of(
            "/login",
            "/members/signup"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 현재 요청 경로 확인
        String path = httpRequest.getRequestURI();

        // 화이트리스트 경로인지 확인
        if (isWhitelisted(path)) {
            chain.doFilter(request, response); // 인증 없이 필터 통과
            return;
        }

        // 인증 체크 로직
        String userId = null;
        if (httpRequest.getCookies() != null) {
            userId = java.util.Arrays.stream(httpRequest.getCookies())
                    .filter(cookie -> "userId".equals(cookie.getName()))
                    .map(cookie -> cookie.getValue())
                    .findFirst()
                    .orElse(null);
        }

        if (userId == null) {
            // 인증 실패 시 Unauthorized 응답 반환
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Unauthorized: Please log in.");
            return;
        }

        // 인증 성공 시 필터 체인 계속 실행
        chain.doFilter(request, response);
    }

    // 화이트리스트 확인 메서드
    private boolean isWhitelisted(String path) {
        return WHITELIST.stream().anyMatch(path::startsWith);
    }
}
