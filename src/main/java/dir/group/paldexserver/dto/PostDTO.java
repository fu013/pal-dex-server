package dir.group.paldexserver.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class PostDTO {
    private String title;
    private String html;
    private String markdown;
    private String description;
    private String tags;
    private List<String> imageArr;
}