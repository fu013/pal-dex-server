package dir.group.paldexserver.entity;
import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Table(name = "file")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @Column(name = "t_name")
    private String t_name;

    @Column(name = "t_pk")
    private Long t_pk;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "ext", nullable = false)
    private String ext;

    @Column(name = "size", nullable = false)
    private long size;

    @Column(name = "is_thumb", length = 1)
    private String is_thumb;

    @Column(name = "comment")
    private String comment;

    @Column(name = "updated_date", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedDate;

    @Column(name = "created_date", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdDate;

    @Builder
    public FileEntity(String t_name,Long t_pk,String path,String ext,long size,String is_thumb) {
        this.t_name = t_name;
        this.t_pk = t_pk;
        this.path = path;
        this.ext = ext;
        this.size = size;
        this.is_thumb = is_thumb;
        this.updatedDate = LocalDateTime.now();
        this.createdDate = LocalDateTime.now();
    }
}