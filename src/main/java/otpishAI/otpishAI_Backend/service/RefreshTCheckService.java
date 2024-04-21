package otpishAI.otpishAI_Backend.service;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import otpishAI.otpishAI_Backend.jwt.JWTUtil;
import otpishAI.otpishAI_Backend.repository.TokenrefreshRepository;

//리프레시 토큰 확인 서비스
@Service
@AllArgsConstructor
public class RefreshTCheckService {
    private final JWTUtil jwtUtil;

    private final TokenrefreshRepository tokenrefreshRepository;

    //리프레시 토큰 확인 함수
    //올바른 리프레시 토큰이 존재한다면 200을, 없다면 400을 반환
    public ResponseEntity<?> RefreshTCheck(HttpServletRequest request, HttpServletResponse response){
        String refresh = getRefreshT(request, response);

        //리프레시 토큰 null 검증
        if (refresh == null) {
            return new ResponseEntity<>("리프레시 토큰 비어있음", HttpStatus.BAD_REQUEST);
        }

        //리프레시 토큰 만료 확인
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            //만료시 예외 처리
            return new ResponseEntity<>("리프레시 토큰 만료됨", HttpStatus.BAD_REQUEST);
        }

        // 토큰이 리프레시인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            return new ResponseEntity<>("적절하지 않은 토큰", HttpStatus.BAD_REQUEST);
        }

        //DB에 저장되어 있는지 확인
        Boolean isExist = tokenrefreshRepository.existsByRefresh(refresh);
        if (!isExist) {
            return new ResponseEntity<>("적절하지 않은 토큰", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    //리프레시 토큰 가져오는 함수
    public String getRefreshT(HttpServletRequest request, HttpServletResponse response){
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("refresh")) {

                refresh = cookie.getValue();
            }
        }
        return refresh;
    }
}
