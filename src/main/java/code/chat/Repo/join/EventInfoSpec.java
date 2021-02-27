package code.chat.Repo.join;

import code.chat.domain.Message;

import code.chat.domain.Message_;
import code.chat.utils.SpecUtils;
import org.springframework.data.jpa.domain.Specification;

public class EventInfoSpec {

    private EventInfoSpec() {}

    public static Specification<Message> hasText(String text) {return SpecUtils.equals(Message_.title, text);
    }
}
