package code.chat.Repo;

import code.chat.domain.Food;
import code.chat.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public interface FoodRepo extends JpaRepository<Food, Long> {

}
