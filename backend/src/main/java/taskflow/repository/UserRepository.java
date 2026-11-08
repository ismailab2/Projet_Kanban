package taskflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import taskflow.domain.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query methods can be defined here if needed
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    
}
