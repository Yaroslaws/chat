package code.chat.Repo;

import code.chat.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.text.MessageFormat;

public interface MessageRepo extends JpaRepository<Message, Long>, JpaSpecificationExecutor<Message> {

}
