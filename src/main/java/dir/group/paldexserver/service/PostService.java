package dir.group.paldexserver.service;

import dir.group.paldexserver.dto.PostDTO;
import dir.group.paldexserver.entity.PostEntity;
import dir.group.paldexserver.entity.FileEntity;
import dir.group.paldexserver.repository.FileRepository;
import dir.group.paldexserver.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Environment env;

    @Autowired
    public PostService(PostRepository postRepository, Environment env) {
        this.postRepository = postRepository;
        this.env = env;
    }

    @Transactional
    public void savePostWithTransaction(PostDTO postDTO) {
        String title = postDTO.getTitle();
        String content = postDTO.getContent();
        String description = postDTO.getDescription();
        String tags = postDTO.getTags();
        List<String> imageArr = postDTO.getImageArr();
        logger.info("Received Post Image(s): {}", imageArr);
        PostEntity postEntity = new PostEntity(title,content,description,tags);
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

    public List<String> storeFiles(List<MultipartFile> files) throws Exception {
        List<String> fileNames = new ArrayList<>();
        for (MultipartFile file : files) {
            String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String fileName = generateUniqueFileName(originalFilename);
            String uploadDir = env.getProperty("server.uploadPath");

            assert uploadDir != null;
            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) {
                boolean directoriesCreated = uploadPath.mkdirs();
                if (!directoriesCreated) {
                    throw new IOException("Failed to create directories: " + uploadPath.getAbsolutePath());
                }
            }
            Path filePath = Paths.get(uploadDir, fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            fileNames.add(fileName);
        }
        return fileNames;
    }

    private String generateUniqueFileName(String originalFilename) {
            if (originalFilename == null || originalFilename.isEmpty()) {
                originalFilename = "images";
            }
            String timestamp = String.valueOf(Instant.now().toEpochMilli());
            String randomPart = UUID.randomUUID().toString().substring(0, 8);
            return timestamp + "_" + randomPart + "_" + originalFilename;
    }
}
