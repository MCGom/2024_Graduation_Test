package otpishAI.otpishAI_Backend.repository;

import otpishAI.otpishAI_Backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    //이메일로
    User findByEmail(String email);

    User findByUsername(String username);
}