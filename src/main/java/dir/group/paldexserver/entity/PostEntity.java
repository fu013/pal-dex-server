package dir.group.paldexserver.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "post")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long pk;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "tags", columnDefinition = "TEXT")
    private String tags;

    @Column(name = "is_public", length = 1)
    private String isPublic;

    @Column(name = "is_review", length = 1)
    private String isReview;

    @Column(name = "comment")
    private String comment;

    @Column(name = "updated_date", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedDate;

    @Column(name = "created_date", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdDate;

    @Builder
    public PostEntity(String title, String content, String description, String tags) {
        this.title = title;
        this.content = content;
        this.description = description;
        this.tags = tags;
        this.isPublic = "1";
        this.isReview = "1";
        this.updatedDate = LocalDateTime.now();
        this.createdDate = LocalDateTime.now();
    }
}
