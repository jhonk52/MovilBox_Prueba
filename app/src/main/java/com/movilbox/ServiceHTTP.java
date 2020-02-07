package com.movilbox;

import com.movilbox.movilboxprueba.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceHTTP {
    @GET("posts")
    Call<List<Post>> getPostsList();
}
