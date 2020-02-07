package com.movilbox.movilboxprueba.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.movilbox.ServiceHTTP;
import com.movilbox.movilboxprueba.API;
import com.movilbox.movilboxprueba.R;
import com.movilbox.movilboxprueba.adapters.AdaptadorListaPublicaciones;
import com.movilbox.movilboxprueba.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Principal extends AppCompatActivity {

    RecyclerView listaPublicaciones;
    RecyclerView.LayoutManager manager;
    AdaptadorListaPublicaciones adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        listaPublicaciones = findViewById(R.id.rcv_listaPublicaciones);
        manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        ServiceHTTP service = API.getAPI().create(ServiceHTTP.class);
        Call<List<Post>> postCall = service.getPostsList();

        postCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                AdaptadorListaPublicaciones adapter = new AdaptadorListaPublicaciones(response.body(), R.layout.plantilla_rcv_listapublicaciones, new AdaptadorListaPublicaciones.OnItemClickListener() {
                    @Override
                    public void onItemClick(Post post, int position) {

                    }
                });


                listaPublicaciones.setLayoutManager(manager);
                listaPublicaciones.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(Principal.this, "Error al traer datos del servidor", Toast.LENGTH_SHORT).show();

            }
        });


    }





}
