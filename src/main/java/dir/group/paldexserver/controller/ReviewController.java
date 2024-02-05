package dir.group.paldexserver.controller;


import dir.group.paldexserver.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
}
