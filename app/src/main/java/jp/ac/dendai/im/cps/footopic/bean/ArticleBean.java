package jp.ac.dendai.im.cps.footopic.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by naoya on 15/12/11.
 * Articleの構造
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class ArticleBean {
    private int id;
    private String created_at;
    private String updated_at;
    private String title;
    private String text;
    private String[] tags;
    private ArticleBean[] comments;
    private UserBean user;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public ArticleBean[] getComments() {
        return comments;
    }

    public void setComments(ArticleBean[] comments) {
        this.comments = comments;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    @Override
    public String toString() {
        String str = "id: " + id + "\n" +
                ", created_at: " + created_at + "\n" +
                ", updated_at: " + updated_at + "\n" +
                ", title: " + title + "\n" +
                ", text: " + text + "\n" +
                ", tags: " + tags + "\n" +
                ", user: " + user.toString() + "\n";

        if (comments == null) {
            str +=  ", comments: " + 0;
        } else {
            str +=  ", comments: " + comments.length;
        }
        return str;
    }
}
