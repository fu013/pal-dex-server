package dir.group.paldexserver.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "pw")
    private String password;

    @Column(name = "is_public")
    private Boolean isPublic;

    @Column(name = "comment")
    private String comment;

    @Column(name = "updated_date", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedDate;

    @Column(name = "created_date", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdDate;
}
