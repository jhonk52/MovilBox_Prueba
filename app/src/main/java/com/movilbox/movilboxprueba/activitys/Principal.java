package com.movilbox.movilboxprueba.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.movilbox.movilboxprueba.ServiceHTTP;
import com.movilbox.movilboxprueba.API;
import com.movilbox.movilboxprueba.R;
import com.movilbox.movilboxprueba.adapters.AdaptadorListaPublicaciones;
import com.movilbox.movilboxprueba.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("POST APP");

        ServiceHTTP service = API.getAPI().create(ServiceHTTP.class);
        Call<List<Post>> postCall = service.getPostsList();

        postCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                desplegarLista(response.body());
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(Principal.this, "Error al obtener datos del servidor", Toast.LENGTH_SHORT).show();
            }
        });


    }



    private void desplegarLista(List<Post> posts){

        RecyclerView listaPublicaciones = findViewById(R.id.rcv_listaPublicaciones);

        RecyclerView.LayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        AdaptadorListaPublicaciones adapter = new AdaptadorListaPublicaciones(posts, R.layout.plantilla_rcv_listapublicaciones, new AdaptadorListaPublicaciones.OnItemClickListener() {
            @Override
            public void onItemClick(Post post, int position) {

                Intent intent = new Intent(Principal.this,Detalle.class);
                intent.putExtra("post",post.convertToString());
                startActivity(intent);

            }
        });

        listaPublicaciones.setLayoutManager(manager);

        listaPublicaciones.setAdapter(adapter);

        listaPublicaciones.setHasFixedSize(true);

    }




}


/*

user 	-> album
    	-> post
	    -> todos

album	-> photos

post	->comments


*/