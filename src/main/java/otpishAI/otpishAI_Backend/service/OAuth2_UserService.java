package otpishAI.otpishAI_Backend.service;

import lombok.AllArgsConstructor;
import otpishAI.otpishAI_Backend.dto.GoogleResponse;
import otpishAI.otpishAI_Backend.dto.NaverResponse;
import otpishAI.otpishAI_Backend.dto.OAuth2Response;
import otpishAI.otpishAI_Backend.dto.OAuth2_User;
import otpishAI.otpishAI_Backend.dto.UserDTO;
import otpishAI.otpishAI_Backend.entity.User;
import otpishAI.otpishAI_Backend.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OAuth2_UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("naver")) {

            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        else if (registrationId.equals("google")) {

            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }
        else {

            return null;
        }
        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        User existData = userRepository.findByUsername(username);

        if (existData == null) {

            User user = new User();
            user.setUsername(username);
            user.setEmail(oAuth2Response.getEmail());
            user.setName(oAuth2Response.getName());
            user.setRole("ROLE_USER");

            userRepository.save(user);

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(username);
            userDTO.setName(oAuth2Response.getName());
            userDTO.setRole("ROLE_USER");

            return new OAuth2_User(userDTO);
        }
        else {

            existData.setEmail(oAuth2Response.getEmail());
            existData.setName(oAuth2Response.getName());

            userRepository.save(existData);

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(existData.getUsername());
            userDTO.setName(oAuth2Response.getName());
            userDTO.setRole(existData.getRole());

            return new OAuth2_User(userDTO);
        }


    }
}