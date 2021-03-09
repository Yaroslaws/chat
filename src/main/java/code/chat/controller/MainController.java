package code.chat.controller;

import code.chat.Repo.MessageRepo;
import code.chat.service.MesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    private final MessageRepo messageRepo;

    public MainController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @Autowired
    MesService mesService;

    @GetMapping
    public String main() {
        mesService.nameByCourse();
        mesService.getMessage("привет");
        return "1";
    }
}
