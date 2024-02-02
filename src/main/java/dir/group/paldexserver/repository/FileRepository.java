package dir.group.paldexserver.repository;

import dir.group.paldexserver.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> { }
