package dir.group.paldexserver.repository;

import dir.group.paldexserver.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> { }