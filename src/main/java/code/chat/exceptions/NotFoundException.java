package code.chat.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
@ResponseBody
public class NotFoundException extends RuntimeException {
    public <T> NotFoundException(T id) {
        super(String.format("Entity with id='%s' not found!", id.toString()));
    }
}
