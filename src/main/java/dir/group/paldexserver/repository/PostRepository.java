package dir.group.paldexserver.repository;

import dir.group.paldexserver.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @Query(value = "SELECT * FROM post WHERE FIND_IN_SET(:tag, REPLACE(tags, ', ', ',')) > 0", nativeQuery = true)
    List<PostEntity> findByTag(@Param("tag") String tag);
}
