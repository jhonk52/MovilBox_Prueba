package com.movilbox.movilboxprueba.models;

public class Post {

    private String userId;
    private String id;
    private String title;
    private String body;

    private String favorite;
    private String viewed;

    public Post(String userId, String id, String title, String body, String favorite, String viewed) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
        this.favorite = favorite;
        this.viewed = viewed;
    }

    public Post() {
        this.favorite = "false";
        this.viewed = "false";
    }

    public Boolean getFavorite() {
        return favorite.equals("true");
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite? "true" : "false";
    }

    public Boolean getViewed() {
        return viewed.equals("true");
    }

    public void setViewed(Boolean viewed) {
        this.viewed = viewed? "true" : "false";
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
        return new String[]{this.userId,this.id,this.title,this.body,this.favorite,this.viewed};
    }

    public Post convertToPost(String[] post){
       return new Post(post[0],post[1],post[2],post[3],post[4],post[5]);
    }

    /*
    public String[] convertToString(){
        return new String[]{this.userId,this.id,this.title,this.body,booleanToString(this.favorite),booleanToString(this.viewed)};
    }

    public Post convertToPost(String[] post){
        return new Post(post[0],post[1],post[2],post[3],stringToBoolean(post[4]),stringToBoolean(post[5]));
    }

    private String booleanToString(boolean val){
        return val ? "true" : "false";
    }

    private boolean stringToBoolean(String val){
        return val.equalsIgnoreCase("true");
    }


     */
}
