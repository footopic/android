package jp.ac.dendai.im.cps.footopic.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by naoya on 15/12/11.
 * Userの構造
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class User implements Serializable {
    private int id;
    private String created_at;
    private String updated_at;
    private String screen_name;
    private String name;
    private String provider;
    private String uid;
    private Image image;
    private int article_count;
    private Article[] recent_articles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at.substring(0, 9) + " " + created_at.substring(11, 16);
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at.substring(0, 9) + " " + updated_at.substring(11, 16);
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Article[] getRecent_articles() {
        return recent_articles;
    }

    public void setRecent_articles(Article[] recent_articles) {
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
