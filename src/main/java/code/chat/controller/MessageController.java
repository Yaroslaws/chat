package code.chat.controller;

import code.chat.Repo.MessageRepo;
import code.chat.Repo.UserDetailsRepo;
import code.chat.domain.Message;
import code.chat.service.MesService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(value = "message", produces = "application/json")
public class MessageController {

    private final MessageRepo messageRepo;
    private final UserDetailsRepo userDetailsRepo;
    private final MesService mesService;

    @Autowired
    public MessageController(MessageRepo messageRepo, MesService mesService, UserDetailsRepo userDetailsRepo, MesService mesService1) {
        this.messageRepo = messageRepo;
        this.userDetailsRepo = userDetailsRepo;
        this.mesService = mesService1;
    }


    @GetMapping("{id}")
    public Message getOne(@PathVariable("id") Message message) {
        return message;
    }

    @PostMapping
    public ResponseEntity<Message> saveMessage(@RequestParam String text) throws InterruptedException {
        return mesService.saveMessage(text);
    }

    @PutMapping("{id}")
    public Message update(
            @PathVariable("id") Message messagefromDb,
            @RequestBody Message message) {

        BeanUtils.copyProperties(message, messagefromDb, "id");

        return messageRepo.save(messagefromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {
        messageRepo.delete(message);

    }
}
