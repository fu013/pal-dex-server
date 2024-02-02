package dir.group.paldexserver.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "images")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @Column(name = "t_name")
    private String tableName;

    @Column(name = "t_pk")
    private Long tablePk;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "ext", nullable = false)
    private String extension;

    @Column(name = "size", nullable = false)
    private int size;

    @Column(name = "is_thumb")
    private Character isThumbnail;

    @Column(name = "comment")
    private String comment;

    @Column(name = "updated_date", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedDate;

    @Column(name = "created_date", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdDate;
}