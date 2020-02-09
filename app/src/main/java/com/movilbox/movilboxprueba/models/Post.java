package com.movilbox.movilboxprueba.models;

import com.google.gson.annotations.Expose;

public class Post {

    private String id;
    private String title;
    private String body;
    private String userId;

    private Boolean favorite = false;
    private Boolean isViewed = false;

    public Post(String userId, String id, String title, String body ) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.userId = userId;
    }

    public Post() {
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public Boolean getViewed() {
        return isViewed;
    }

    public void setViewed(Boolean viewed) {
        isViewed = viewed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String[] convertToString(){
        return new String[]{this.userId,this.id,this.title,this.body};
    }

    public Post convertToPost(String[] post){
        return new Post(post[0],post[1],post[2],post[3]);
    }

}
