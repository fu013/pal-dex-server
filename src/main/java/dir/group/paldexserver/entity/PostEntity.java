package dir.group.paldexserver.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_public")
    private Boolean isPublic;

    @Column(name = "is_review")
    private Boolean isReview;

    @Column(name = "comment")
    private String comment;

    @Column(name = "updated_date", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedDate;

    @Column(name = "created_date", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdDate;
}
