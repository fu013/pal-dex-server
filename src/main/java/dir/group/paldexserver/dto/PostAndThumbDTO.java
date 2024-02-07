package dir.group.paldexserver.dto;

import java.time.LocalDateTime;

public class PostAndThumbDTO {
    private Long pk;
    private String title;
    private String html;
    private String markdown;
    private String description;
    private String tags;
    private String isPublic;
    private String isReview;
    private String comment;
    private String path;
    private LocalDateTime updatedDate;
    private LocalDateTime createdDate;
}
