package dir.group.paldexserver.interfaces;

public interface PostWithFilePathProjection {
    Long getPk();
    String getTitle();
    String getHtml();
    String getMarkdown();
    String getDescription();
    String getTags();
    String getIsPublic();
    String getIsReview();
    String getComment();
    String getPath();
}
