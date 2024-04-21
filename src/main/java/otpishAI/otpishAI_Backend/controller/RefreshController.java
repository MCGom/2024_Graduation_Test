package otpishAI.otpishAI_Backend.controller;

import lombok.AllArgsConstructor;
import otpishAI.otpishAI_Backend.entity.Tokenrefresh;
import otpishAI.otpishAI_Backend.jwt.JWTUtil;
import otpishAI.otpishAI_Backend.repository.TokenrefreshRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import otpishAI.otpishAI_Backend.service.CookieService;
import otpishAI.otpishAI_Backend.service.RefreshTCheckService;

import java.util.Date;

@RestController
@AllArgsConstructor
public class RefreshController {

    private final JWTUtil jwtUtil;

    private final TokenrefreshRepository tokenrefreshRepository;

    private final RefreshTCheckService refreshTCheckService;

    private final CookieService cookieService;

    //토큰 재발급 요청(엑세스 토큰은 만료되거나 없지만, 올바른 리프레시 토큰이 존재한다면 요청)
    //로그인이 필요한 페이지로 이동할때 서버에 access 토큰이 만료되었다면 401 에러롤 반환, 이때 401 에러가 발생했다면,
    //post로 refresh 요청을 수행하여, 리프레시 토큰이 확인되면 access 토큰과 리프레시 토큰을 재발급 하고 이동하려는 페이지로 정상 이동시킴(200 반환)
    //refresh 요청을 수행하였을 때 리프레시 토큰 또한 만료되거나 유효하지 않다면 400에러 반환
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) {

        //리프레시 토큰 얻음
        if(refreshTCheckService.RefreshTCheck(request, response).getStatusCode() == HttpStatus.OK)
        {
            String refresh = refreshTCheckService.getRefreshT(request, response);

            String username = jwtUtil.getUsername(refresh);
            String role = jwtUtil.getRole(refresh);

            //새로운 JWT 토큰 발급
            String newAccess = jwtUtil.createJwt("access", username, role, 600000L);
            String newRefresh = jwtUtil.createJwt("refresh", username, role, 86400000L);

            //리프레시 토큰 저장 DB에 기존의 리프레시 토큰 삭제 후 새 리프레시 토큰 저장
            tokenrefreshRepository.deleteByRefresh(refresh);
            cookieService.addRefreshEntity(username, newRefresh, 86400000L);

            response.setHeader("access", newAccess);
            response.addCookie(cookieService.createCookie("refresh", newRefresh));

            return new ResponseEntity<>(HttpStatus.OK);

        }
        //리프레시 토큰이 올바르지 않거나 없다면 400 반환
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
