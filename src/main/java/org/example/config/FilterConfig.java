package org.example.config;

import org.example.filter.AuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FilterConfig는 애플리케이션에서 사용하는 필터를 설정하는 클래스입니다.
 * AuthenticationFilter를 등록하고 필터 동작을 정의합니다.
 */
@Configuration // Spring에서 이 클래스를 설정 파일로 인식하도록 지정
public class FilterConfig {

    /**
     * AuthenticationFilter를 등록하는 메서드입니다.
     * @return FilterRegistrationBean<AuthenticationFilter>
     */
    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilter() {
        // FilterRegistrationBean 객체 생성
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();

        // 사용자 정의 필터(AuthenticationFilter) 설정
        registrationBean.setFilter(new AuthenticationFilter());

        // 필터를 적용할 URL 패턴 설정
        registrationBean.addUrlPatterns("/*"); // 모든 요청에 필터 적용

        // 필터 이름 설정
        registrationBean.setName("AuthenticationFilter");

        // 필터 실행 순서 설정 (숫자가 낮을수록 먼저 실행)
        registrationBean.setOrder(1);

        // 설정된 FilterRegistrationBean 반환
        return registrationBean;
    }
}
