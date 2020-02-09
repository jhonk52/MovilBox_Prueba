package com.movilbox.movilboxprueba;

import com.movilbox.movilboxprueba.models.Comment;
import com.movilbox.movilboxprueba.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceHTTP {
    @GET("posts")
    Call<List<Post>> getPostsList();

    @GET("comments")
    Call<List<Comment>> getPostComments(@Query("postId") String postId);

}
