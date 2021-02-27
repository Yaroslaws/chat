package code.chat.controller;

import code.chat.Repo.MessageRepo;
import code.chat.domain.Message;
import code.chat.domain.Views;
import code.chat.service.MesService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "message", produces = "application/json")
public class MessageController {

    private final MessageRepo messageRepo;
    private final MesService mesService;

    @Autowired
    public MessageController(MessageRepo messageRepo, MesService mesService) {
        this.messageRepo = messageRepo;
        this.mesService = mesService;
    }

    @GetMapping
    public List<Message> list() {

        mesService.getAllText();
        return messageRepo.findAll();
    }

    @GetMapping("{id}")
    public Message getOne(@PathVariable("id") Message message) {
        return message;
    }

    @PostMapping
    public Message create(@RequestBody Message message) {
        message.setCreationDate(LocalDateTime.now());
        return messageRepo.save(message);
    }

    @PutMapping("{id}")
    public Message update(
            @PathVariable("id") Message messageFromDb,
            @RequestBody Message message) {

        BeanUtils.copyProperties(message, messageFromDb, "id");

        return messageRepo.save(messageFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {
        messageRepo.delete(message);

    }
}
