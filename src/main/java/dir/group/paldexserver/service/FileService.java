package dir.group.paldexserver.service;

import dir.group.paldexserver.entity.FileEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dir.group.paldexserver.repository.FileRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileService {

    private final FileRepository fileRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public FileService(FileRepository fileRepository) {
            this.fileRepository = fileRepository;
        }
        public void deleteFilesByPostId(Long postId) {
            List<FileEntity> files = fileRepository.findByTpk(postId);
            for (FileEntity file : files) {
                String filePath = file.getPath();
                deleteFileLocally(filePath);
                fileRepository.delete(file);
            }
        }
        private void deleteFileLocally(String filePath) {
        try {
            Path localFilePath = Paths.get(filePath);
            Files.delete(localFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
