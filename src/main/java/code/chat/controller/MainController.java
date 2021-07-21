package code.chat.controller;

import code.chat.domain.Message;
import code.chat.service.MesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class MainController {

    final private MesService mesService;

    @GetMapping
    public List<Message> main() {
        return mesService.test();
    }
}
