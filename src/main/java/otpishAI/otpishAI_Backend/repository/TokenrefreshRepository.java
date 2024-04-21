package otpishAI.otpishAI_Backend.repository;

import otpishAI.otpishAI_Backend.entity.Tokenrefresh;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenrefreshRepository extends JpaRepository<Tokenrefresh, Long> {

    Boolean existsByRefresh(String refresh);

    @Transactional
    void deleteByRefresh(String refresh);
}
