package otpishAI.otpishAI_Backend.controller;


import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;
import otpishAI.otpishAI_Backend.entity.User;
import otpishAI.otpishAI_Backend.jwt.JWTUtil;
import otpishAI.otpishAI_Backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import otpishAI.otpishAI_Backend.service.RefreshTCheckService;

@RestController
@AllArgsConstructor
public class RegisterController {


    private final JWTUtil jwtUtil;

    private final UserRepository userRepository;

    private final RefreshTCheckService refreshTCheckService;

    @GetMapping("/register")
    public ResponseEntity<?> register(HttpServletRequest request, HttpServletResponse response){
        User user;
        if(refreshTCheckService.RefreshTCheck(request, response).getStatusCode() == HttpStatus.OK)
        {
            String refresh = refreshTCheckService.getRefreshT(request, response);
            //유저 정보 받아옴
            user = userRepository.findByUsername(jwtUtil.getUsername(refresh));

            //유저 정보 반환
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/register")
    public void register(@RequestBody User user, HttpServletResponse response) {
        //DB에 유저 정보 저장
        userRepository.save(user);

    }
}
