package jp.ac.dendai.im.cps.footopic.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by naoya on 16/02/28.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Comment implements Serializable {
    private int id;
    private String created_at;
    private String updated_at;
    private String text;
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaneCreated_at() {
        return created_at.substring(0, 9) + " " + created_at.substring(11, 16);
    }

    public String getCreated_at() {
        return created_at.substring(0, 9) + " " + created_at.substring(11, 16);
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", text='" + text + '\'' +
                ", user=" + user +
                '}';
    }
}
