package dir.group.paldexserver.controller;

import dir.group.paldexserver.dto.PostDTO;
import dir.group.paldexserver.entity.PostEntity;
import dir.group.paldexserver.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("")
    public ResponseEntity<List<PostEntity>> getPost(@RequestParam("id") long id) {
        return postService.getPostById(id);
    }
    @GetMapping("/all")
    public ResponseEntity<List<PostEntity>> getAllPost() {
        return postService.getAllPosts();
    }

    @PostMapping("/set")
    public ResponseEntity<String> createPost(@RequestBody PostDTO postDTO) {
        try {
            postService.savePostWithTransaction(postDTO);
            return new ResponseEntity<>("Post created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating post: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
