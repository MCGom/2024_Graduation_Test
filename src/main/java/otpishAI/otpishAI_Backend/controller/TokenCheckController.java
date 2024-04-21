package otpishAI.otpishAI_Backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import otpishAI.otpishAI_Backend.service.RefreshTCheckService;

@RestController
@AllArgsConstructor
public class TokenCheckController {

    private final RefreshTCheckService refreshTCheckService;

    //리프레시 토큰 확인 요청
    @PostMapping("/tokenCheck")
    public ResponseEntity<?> tokenChecker(HttpServletRequest request, HttpServletResponse response){
        //올바른 리프레시 토큰이 있다면 200을, 없다면 400을 반환
        return refreshTCheckService.RefreshTCheck(request, response);

    }
}
