package dir.group.paldexserver.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AppController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("file:${user.dir}/uploads")
    private String uploadPath;

    @GetMapping("/")
    public String app() throws IOException {
        logger.info("Upload path: " + uploadPath);
        return "shop";
    }
}