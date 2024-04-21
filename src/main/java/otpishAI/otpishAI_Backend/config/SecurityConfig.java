package otpishAI.otpishAI_Backend.config;

import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import otpishAI.otpishAI_Backend.jwt.JWTFilter;
import otpishAI.otpishAI_Backend.jwt.JWTUtil;
import otpishAI.otpishAI_Backend.jwt.Logout_Filter;
import otpishAI.otpishAI_Backend.oAuth2.SuccessHandler;
import otpishAI.otpishAI_Backend.repository.TokenrefreshRepository;
import otpishAI.otpishAI_Backend.service.CookieService;
import otpishAI.otpishAI_Backend.service.OAuth2_UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {


    private final OAuth2_UserService customOAuth2UserService;

    private final SuccessHandler customSuccessHandler;

    private final TokenrefreshRepository tokenrefreshRepository;

    private final JWTUtil jwtUtil;

    private final CookieService cookieService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //cors 보안 설정
        http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Arrays.asList("JSESSIONID", "access", "refresh", "Set-Cookie", "Content-Type"));
                        configuration.setMaxAge(3600L);

                        configuration.setExposedHeaders(Arrays.asList("JSESSIONID", "access", "refresh", "Set-Cookie", "Content-Type"));

                        return configuration;
                    }
                }));
        //csrf 설정(비확성화)
        http
                .csrf((auth) -> auth.disable());

        //From 로그인 방식 설정(비확성화)
        http
                .formLogin((auth) -> auth.disable());

        //HTTP Basic 인증 방식 설정(비확성화)
        http
                .httpBasic((auth) -> auth.disable());

        //oauth2 설정
        http
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(customOAuth2UserService))
                        .successHandler(customSuccessHandler)
                );

        //경로별 인가 작업
        http
                .authorizeRequests((auth) -> auth
                        .requestMatchers("/refresh").permitAll()
                        .anyRequest().authenticated());
        //JWTFilter 및 Logout_Filter 추가
        http
                .addFilterAfter(new JWTFilter(jwtUtil), OAuth2LoginAuthenticationFilter.class)
                .addFilterBefore(new Logout_Filter(jwtUtil, tokenrefreshRepository, cookieService), LogoutFilter.class);
        //세션 설정(세션 유지 x)
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        return http.build();
    }
}