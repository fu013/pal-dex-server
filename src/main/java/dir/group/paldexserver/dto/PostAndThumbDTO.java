package dir.group.paldexserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
