package com.movilbox.movilboxprueba.Retrofit;

import com.movilbox.movilboxprueba.models.Comment;
import com.movilbox.movilboxprueba.models.Post;
import com.movilbox.movilboxprueba.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Requests {

    @GET("posts")
    Call<List<Post>> getPostsList();

    @GET("comments")
    Call<List<Comment>> getPostComments(@Query("postId") String postId);

    @GET("users/{id}")
    Call<User> getDataUser(@Path("id") String id);

}
