package otpishAI.otpishAI_Backend.oAuth2;

import lombok.AllArgsConstructor;
import otpishAI.otpishAI_Backend.dto.OAuth2_User;
import otpishAI.otpishAI_Backend.entity.User;
import otpishAI.otpishAI_Backend.jwt.JWTUtil;
import otpishAI.otpishAI_Backend.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import otpishAI.otpishAI_Backend.service.CookieService;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Component
@AllArgsConstructor
public class SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    private final CookieService cookieService;

    //로그인 성공시 실행
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //OAuth2User
        OAuth2_User customUserDetails = (OAuth2_User) authentication.getPrincipal();

        String username = customUserDetails.getUsername();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        //JWT access 토큰과 리프레시 토큰 생성
        String access = jwtUtil.createJwt("access",  username, role, 600000L); //access 토큰의 지속 시간은 10분
        String refresh = jwtUtil.createJwt("refresh", username, role, 86400000L); //리프레시 토큰의 지속 시간은 24시간

        cookieService.addRefreshEntity(username, refresh, 86400000L);

        User existData = userRepository.findByUsername(username);

        response.addCookie(cookieService.createCookie("access", access));
        response.addCookie(cookieService.createCookie("refresh", refresh));

        //이미 추가 회원 정보 등록을 한 유저만 메인으로 이동
        if(existData.getAddr() == null)
        {
            response.sendRedirect("http://localhost:3000/register");
        }
        else {
            response.sendRedirect("http://localhost:3000/main");
        }
    }

}