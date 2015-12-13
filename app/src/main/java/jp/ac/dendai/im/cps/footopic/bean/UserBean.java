package jp.ac.dendai.im.cps.footopic.bean;

/**
 * Created by naoya on 15/12/11.
 * Userの構造
 */
public class UserBean {
    private int id;
    private String created_at;
    private String updated_at;
    private String screen_name;
    private String name;
    private String provider;
    private String uid;
    private ImageBean image;
    private int article_count;
    private ArticleBean[] recent_articles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getArticle_count() {
        return article_count;
    }

    public void setArticle_count(int article_count) {
        this.article_count = article_count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageBean getImage() {
        return image;
    }

    public void setImage(ImageBean image) {
        this.image = image;
    }

    public ArticleBean[] getRecent_articles() {
        return recent_articles;
    }

    public void setRecent_articles(ArticleBean[] recent_articles) {
        this.recent_articles = recent_articles;
    }

    @Override
    public String toString() {
        String str = "id: " + id + "\n" +
                "created_at: " + created_at + "\n" +
                "updated_at: " + updated_at + "\n" +
                "screen_name: " + screen_name + "\n" +
                "name:" + name + "\n" +
                "provider: " + provider + "\n" +
                "uid: " + uid + "\n" +
                "image: " + image.toString() + "\n" +
                "article_count: " + article_count + "\n";

        if (recent_articles == null) {
            str += "recent_articles: " + 0;
        } else {
            str += "recent_articles: " + recent_articles.length;
        }

        return str;
    }
}
