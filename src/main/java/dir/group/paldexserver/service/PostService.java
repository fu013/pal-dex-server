package dir.group.paldexserver.service;

import dir.group.paldexserver.bean.ImgBean;
import dir.group.paldexserver.dto.PostDTO;
import dir.group.paldexserver.entity.FileEntity;
import dir.group.paldexserver.entity.PostEntity;
import dir.group.paldexserver.interfaces.PostWithFilePathProjection;
import dir.group.paldexserver.repository.FileRepository;
import dir.group.paldexserver.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.nio.file.Paths;
import java.time.Instant;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PostService {
    @Value("${server.config.uploadPath}") // 임시 이미지 경로
    private String uploadPath;

    private final PostRepository postRepository;
    private final FileRepository fileRepository;
    private final FileService fileService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Environment env;

    @Autowired
    public PostService(PostRepository postRepository,
                       FileRepository fileRepository,
                       Environment env,
                       FileService fileService) {
        this.postRepository = postRepository;
        this.fileRepository = fileRepository;
        this.env = env;
        this.fileService = fileService;
    }

    @Transactional
    public void savePostWithTransaction(PostDTO postDTO) throws IOException {
        try {
            String title = postDTO.getTitle();
            String html = postDTO.getHtml();
            String markdown = postDTO.getMarkdown();
            String description = postDTO.getDescription();
            String tags = postDTO.getTags();
            String imageArr = postDTO.getImageArr();

            Optional<PostEntity> existingPostOptional = postRepository.findByTitle(title);
            PostEntity postEntity;

            if (existingPostOptional.isPresent()) {
                postEntity = existingPostOptional.get();
                postEntity.setHtml(html);
                postEntity.setMarkdown(markdown);
                postEntity.setDescription(description);
                postEntity.setTags(tags);
            } else {
                postEntity = new PostEntity(title, html, markdown, description, tags);
            }

            postRepository.save(postEntity);
            Long generatedPK = postEntity.getPk();

            ObjectMapper objectMapper = new ObjectMapper();
            ImgBean[] imgBean = objectMapper.readValue(imageArr, ImgBean[].class);

            for (ImgBean obj : imgBean) {
                String dirImagePath = uploadPath + "/" + obj.getUrl();
                Path filePath = Path.of(dirImagePath);

                if (Files.exists(filePath) && generatedPK > 0) {
                    long size = Files.size(filePath);
                    String ext = getFileExtension(dirImagePath);
                    String isThumbnail = obj.getIsThumbnail();
                    Optional<FileEntity> existingFileOptional = fileRepository.findByTpkAndIsThumb(generatedPK, isThumbnail);
                    FileEntity fileEntity;

                    if (existingFileOptional.isPresent()) {
                        fileEntity = existingFileOptional.get();
                        fileEntity.setSize(size);
                    } else {
                        fileEntity = new FileEntity("post", generatedPK, dirImagePath, ext, size, isThumbnail);
                    }
                    fileRepository.save(fileEntity);
                } else {
                    throw new IOException("File not found: " + dirImagePath);
                }
            }
        } catch (IOException e) {
            logger.error("Error processing file: {}", e.getMessage(), e);
            throw new IOException("Error processing file: " + e.getMessage());
        }
    }


    private static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex >= 0) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
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
    public ResponseEntity<List<PostWithFilePathProjection>> getPostsByTag(String tag) {
        List<PostWithFilePathProjection> postList = postRepository.findByTag(tag);
        if (!postList.isEmpty()) {
            return ResponseEntity.ok(postList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Transactional
    public ResponseEntity<List<PostWithFilePathProjection>> getAllPosts() {
        List<PostWithFilePathProjection> posts = postRepository.findAllPosts();
        if (!posts.isEmpty()) {
            return ResponseEntity.ok(posts);
        } else {
            return ResponseEntity.notFound().build();
        }
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

    @Transactional
    public void deletePostWithFiles(Long postId) {
        Optional<PostEntity> postOptional = postRepository.findById(postId);

        if (postOptional.isPresent()) {
            PostEntity postEntity = postOptional.get();

            fileService.deleteFilesByPostId(postId);

            postRepository.delete(postEntity);
        } else {
            throw new RuntimeException("Post not found with ID: " + postId);
        }
    }
}
