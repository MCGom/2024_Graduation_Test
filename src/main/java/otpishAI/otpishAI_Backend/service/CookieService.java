package otpishAI.otpishAI_Backend.service;

import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import otpishAI.otpishAI_Backend.entity.Tokenrefresh;
import otpishAI.otpishAI_Backend.repository.TokenrefreshRepository;

import java.util.Date;

//쿠키를 생성하거나 저장하기 위한 서비스
@Service
@AllArgsConstructor
public class CookieService {

    private final TokenrefreshRepository tokenrefreshRepository;

    //쿠키 생성 함수
    public Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
    //쿠키 삭제 함수
    public Cookie deleteCookie(String key)
    {
        Cookie deleteCookie = new Cookie(key, null);
        deleteCookie.setMaxAge(0);
        deleteCookie.setPath("/");

        return  deleteCookie;
    }
    //리프레시 토큰 서버 저장용 함수
    public void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        Tokenrefresh tokenrefresh = new Tokenrefresh();
        tokenrefresh.setUsername(username);
        tokenrefresh.setRefresh(refresh);
        tokenrefresh.setExpiration(date.toString());

        tokenrefreshRepository.save(tokenrefresh);
    }

}
