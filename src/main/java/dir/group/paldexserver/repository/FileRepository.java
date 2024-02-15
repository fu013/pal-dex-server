package dir.group.paldexserver.repository;

import dir.group.paldexserver.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    Optional<FileEntity> findByPath(String dirImagePath);

    List<FileEntity> findByTpk(Long t_pk);
}
