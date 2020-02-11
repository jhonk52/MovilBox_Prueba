package com.movilbox.movilboxprueba.Retrofit;

import com.movilbox.movilboxprueba.models.Post;
import com.movilbox.movilboxprueba.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Requests {

    @GET("posts")
    Call<List<Post>> getPostsList();

    @GET("users/{id}")
    Call<User> getDataUser(@Path("id") String id);

}
