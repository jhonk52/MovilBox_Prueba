package com.movilbox.movilboxprueba.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.movilbox.movilboxprueba.Retrofit.Requests;
import com.movilbox.movilboxprueba.Retrofit.RetrofitInstance;
import com.movilbox.movilboxprueba.R;
import com.movilbox.movilboxprueba.adapters.PostsListAdapter;
import com.movilbox.movilboxprueba.adapters.SwipeToDeleteCallback;
import com.movilbox.movilboxprueba.database.Database;
import com.movilbox.movilboxprueba.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//Este activity nunca se destruye por inactividad (ver manifest linea 20)

public class Principal extends AppCompatActivity implements PostsListAdapter.OnItemClickListener{


    RecyclerView rcv_postsList;
    RecyclerView.LayoutManager manager_rcvPostList;
    PostsListAdapter adapter_rcvPostList;

    boolean update_button_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("POST APP");

        rcv_postsList = findViewById(R.id.rcv_postsList_principal);
        manager_rcvPostList = new LinearLayoutManager(Principal.this);

        update_button_flag = false;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionsmenu_principal,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.itemMenu_update_principal:

                update_button_flag = true;
                getPosts();

                break;
        }

        return super.onOptionsItemSelected(item);
    }



    private void desplegarLista(List<Post> posts){

        adapter_rcvPostList = new PostsListAdapter(posts, R.layout.template_rcv_postslist, this);

        rcv_postsList.setLayoutManager(manager_rcvPostList);
        rcv_postsList.setAdapter(adapter_rcvPostList);
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(adapter_rcvPostList));
        itemTouchHelper.attachToRecyclerView(rcv_postsList);

    }



    private void getPosts(){

        Requests service = RetrofitInstance.getInstance(false).create(Requests.class);
        Call<List<Post>> postCall = service.getPostsList();


        postCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                // obtengo los posts y los guardo en database, si ya existe lo traigo de database

                Database database = new Database(Principal.this);
                List<Post> postsResponse = response.body();

                for (int i = 0; i < postsResponse.size() ; i++){

                    Post post = postsResponse.get(i);

                    if( !database.savePost(post) ){
                        postsResponse.set(i,database.readPost(post.getId()));
                    }
                }

                desplegarLista(response.body());

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

                if(!update_button_flag){
                    desplegarLista(new Database(Principal.this).listPost(""));
                }

                Toast.makeText(Principal.this, "Error al obtener datos del servidor", Toast.LENGTH_SHORT).show();

            }
        });


    }


    @Override
    public void onItemClick(Post post, int position) {

        post.setViewed(true);

        Database database = new Database(Principal.this);
        if (!database.updatePost(post)){
            database.savePost(post);
        }

        Intent intent = new Intent(Principal.this,Detalle.class);
        intent.putExtra("post",post.convertToString());

        startActivity(intent);

        adapter_rcvPostList.notifyItemChanged(position);
    }



    @Override
    protected void onResume() {

        getPosts();

        super.onResume();
    }



}

