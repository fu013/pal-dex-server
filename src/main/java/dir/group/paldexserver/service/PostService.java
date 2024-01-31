package dir.group.paldexserver.service;

import dir.group.paldexserver.dto.PostDTO;
import dir.group.paldexserver.entity.PostEntity;
import dir.group.paldexserver.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Optional;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public void savePostWithTransaction(PostDTO postDTO) {
        String title = postDTO.getTitle();
        String content = postDTO.getContent();
        PostEntity postEntity = new PostEntity(title,content);
        postRepository.save(postEntity);
    }

    @Transactional
    public ResponseEntity<List<PostEntity>> getPostById(Long pk) {
        Optional<PostEntity> postOptional = postRepository.findById(pk);
        if (postOptional.isPresent()) {
            List<PostEntity> postList = Collections.singletonList(postOptional.get());
            return ResponseEntity.ok(postList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Transactional
    public ResponseEntity<List<PostEntity>> getAllPosts() {
        List<PostEntity> posts = postRepository.findAll();
        return ResponseEntity.ok(posts);
    }
}
