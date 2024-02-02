package dir.group.paldexserver.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AppController {

    @GetMapping("/")
    public String app() throws IOException {
        return "shop";
    }
}