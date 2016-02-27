package jp.ac.dendai.im.cps.footopic.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by naoya on 15/12/11.
 * Articleの構造
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Article implements Serializable {
    private int id;
    private String created_at;
    private String updated_at;
    private String title;
    private String text;
    private String[] tags;
    private Comment[] comments;
    private User user;
    private History[] histories;
    private int star_count;
    private User[] stars;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaneCreated_at() {
        return created_at;
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

    public Comment[] getComments() {
        return comments;
    }

    public void setComments(Comment[] comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public History[] getHistories() {
        return histories;
    }

    public void setHistories(History[] histories) {
        this.histories = histories;
    }

    public int getStar_count() {
        return star_count;
    }

    public void setStar_count(int star_count) {
        this.star_count = star_count;
    }

    public User[] getStars() {
        return stars;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", tags=" + Arrays.toString(tags) +
                ", comments=" + Arrays.toString(comments) +
                ", user=" + user +
                ", histories=" + Arrays.toString(histories) +
                ", star_count=" + star_count +
                ", stars=" + Arrays.toString(stars) +
                '}';
    }

    public void setStars(User[] stars) {
        this.stars = stars;
    }
}
