package code.chat.Repo;

import code.chat.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsRepo extends JpaRepository<User, String> {

}
