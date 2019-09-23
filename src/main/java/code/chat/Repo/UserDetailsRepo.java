package code.chat.Repo;

import code.chat.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserDetailsRepo extends JpaRepository<User, String> {

}
