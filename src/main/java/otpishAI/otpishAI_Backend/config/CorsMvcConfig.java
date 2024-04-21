package otpishAI.otpishAI_Backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    //cors 설정
    //Origins로 부터 오는 모든 경로 허용
    //쿠키를 받을 수 있도록 Set-Cookie 헤더 노출
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .allowedHeaders("access", "refresh", "Set-Cookie")
                .exposedHeaders("access", "refresh", "Set-Cookie")
                .allowedOrigins("http://localhost:3000")
                .maxAge(3600L);
    }
}