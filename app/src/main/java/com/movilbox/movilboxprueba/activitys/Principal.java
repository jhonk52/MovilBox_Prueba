package com.movilbox.movilboxprueba.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import com.movilbox.movilboxprueba.ServiceHTTP;
import com.movilbox.movilboxprueba.API;
import com.movilbox.movilboxprueba.R;
import com.movilbox.movilboxprueba.adapters.AdaptadorListaPublicaciones;
import com.movilbox.movilboxprueba.database.BasedeDatos;
import com.movilbox.movilboxprueba.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Principal extends AppCompatActivity implements AdaptadorListaPublicaciones.OnItemClickListener{

    List<Post> postes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("POST APP");

        ServiceHTTP service = API.getAPI().create(ServiceHTTP.class);
        Call<List<Post>> postCall = service.getPostsList();

        if(postes != null) {

            desplegarLista(postes);

        }else{


            postCall.enqueue(new Callback<List<Post>>() {
                @Override
                public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                    postes = response.body();

                    BasedeDatos database = new BasedeDatos(Principal.this);

                    for (int i = 0; i < 20 ; i++){
                        database.savePost(postes.get(i));
                    }

                    desplegarLista(postes);
                    Toast.makeText(Principal.this, "resivido", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<List<Post>> call, Throwable t) {
                    Toast.makeText(Principal.this, "Error al obtener datos del servidor", Toast.LENGTH_SHORT).show();
                }
            });


        }

    }



    private void desplegarLista(List<Post> posts){

        RecyclerView listaPublicaciones = findViewById(R.id.rcv_listaPublicaciones);

        RecyclerView.LayoutManager manager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        AdaptadorListaPublicaciones adapter = new AdaptadorListaPublicaciones(posts, R.layout.plantilla_rcv_listapublicaciones, this);

        listaPublicaciones.setLayoutManager(manager);
        listaPublicaciones.setAdapter(adapter);

        listaPublicaciones.setHasFixedSize(true);

    }


    @Override
    public void onItemClick(Post post, int position) {
        post.setViewed("true");

        Intent intent = new Intent(Principal.this,Detalle.class);
        intent.putExtra("post",post.convertToString());

        startActivity(intent);

        desplegarLista(postes);
    }


}


/*

user 	-> album
    	-> post
	    -> todos

album	-> photos

post	-> comments


*/