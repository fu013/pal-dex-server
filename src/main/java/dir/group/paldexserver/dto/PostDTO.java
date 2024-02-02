package dir.group.paldexserver.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class PostDTO {
    private String title;
    private String content;
    private String description;
    private String tags;
    private List<String> imageArr;
}