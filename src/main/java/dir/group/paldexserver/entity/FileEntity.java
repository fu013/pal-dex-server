package dir.group.paldexserver.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "file", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"tpk", "is_thumb"})
})
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;

    @Column(name = "t_name")
    private String t_name;

    @Column(name = "tpk")
    private Long tpk;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "ext", nullable = false)
    private String ext;

    @Column(name = "size", nullable = false)
    private long size;

    @Column(name = "isThumb", length = 1)
    private String isThumb;

    @Column(name = "comment")
    private String comment;

    @Column(name = "updated_date", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedDate;

    @Column(name = "created_date", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdDate;

    public FileEntity() {}

    @Builder
    public FileEntity(String t_name, Long tpk, String path, String ext, long size, String isThumb) {
        this.t_name = t_name;
        this.tpk = tpk;
        this.path = path;
        this.ext = ext;
        this.size = size;
        this.isThumb = isThumb;
        this.updatedDate = LocalDateTime.now();
        this.createdDate = LocalDateTime.now();
    }
}
