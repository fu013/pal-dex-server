package dir.group.paldexserver.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ImgBean {
    // getters and setters
    @JsonProperty("url")
    private String url;

    @JsonProperty("isThumbnail")
    private String isThumbnail;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setIsThumbnail(String isThumbnail) {
        this.isThumbnail = isThumbnail;
    }
}
