package dir.group.paldexserver.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class PostDTO {
    private String title;
    private String content;
}