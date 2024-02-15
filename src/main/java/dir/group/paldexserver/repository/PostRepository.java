package dir.group.paldexserver.repository;

import dir.group.paldexserver.entity.PostEntity;
import dir.group.paldexserver.interfaces.PostWithFilePathProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    @Query(value = "SELECT p.pk AS pk, p.title AS title, " +
            "p.html AS html, p.markdown AS markdown, p" +
            ".description AS description, p.tags AS tags," +
            " p.is_public AS isPublic, p.is_review AS isReview, p.comment AS comment, f.path AS path FROM post p LEFT JOIN file f ON p.pk = f.t_pk AND f.t_name = 'post' AND f.is_thumb = '1'", nativeQuery = true)
    List<PostWithFilePathProjection> findAllPosts();
    @Query(value = "SELECT p.pk AS pk, p.title AS title, " +
            "p.html AS html, p.markdown AS markdown, p" +
            ".description AS description, p.tags AS tags," +
            " p.is_public AS isPublic, p.is_review AS " +
            "isReview, p.comment AS comment, f.path AS path FROM post p LEFT JOIN file f ON p.pk = f.t_pk AND f.t_name = 'post' AND f.is_thumb = '1' WHERE FIND_IN_SET(:tag, REPLACE(p.tags, ', ', ',')) > 0", nativeQuery = true)
    List<PostWithFilePathProjection> findByTag(@Param("tag") String tag);

    Optional<PostEntity> findByTitle(String title);
}
