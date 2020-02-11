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
import com.movilbox.movilboxprueba.database.DatabaseFields;
import com.movilbox.movilboxprueba.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//Este activity nunca se destruye por inactividad (ver manifest linea 20)

public class Principal extends AppCompatActivity implements PostsListAdapter.OnItemClickListener{

    RecyclerView postsList;
    RecyclerView.LayoutManager manager_PostList;
    PostsListAdapter adapter_PostList;

    boolean isFirstPostsLoad;
    Post clickedPost;
    int positionclickedPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("POST APP");

        postsList = findViewById(R.id.rcv_postsList_principal);
        manager_PostList = new LinearLayoutManager(Principal.this);

        isFirstPostsLoad = true;

        getPosts();

    }



    @Override
    protected void onResume() {

        if(clickedPost != null){ // si no es la primera vez que se ejecuta onResume

            //En activity detalle se cambio la propiedad viewed o favorite y se guardo en database
            // aqui se recupera ese post y se actualiza en el recyclerview para mostrar los cambios

            Database database = new Database(Principal.this);
            clickedPost = database.readPost( clickedPost.getId() );
            database.close();
            adapter_PostList.editItem(positionclickedPost, clickedPost);
        }

        super.onResume();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionsmenu_principal,menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.itemMenu_update_principal ||
                itemId == R.id.itemMenu_allPost_principal){

            isFirstPostsLoad = false;
            getPosts(); // obtengo los post desde el servicio en internet y los listo en el recyclerview

        }else if (itemId == R.id.itemMenu_favorites_principal){

            //Obtengo los post desde la base de datos que tengan campo "favorite" como true (1 en sqlite)
            // y actualizo el reciclerview con los nuevos post favoritos
            Database database = new Database(Principal.this);
                adapter_PostList.setPosts( database.listPost(DatabaseFields.FAVORITE,"1") );
                adapter_PostList.notifyDataSetChanged();
            database.close();

        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onItemClick(Post post, int position) {
        clickedPost = post;
        positionclickedPost = position;

        clickedPost.setViewed(true);

        Intent intent = new Intent(Principal.this,Detalle.class);

        // envio el post clickeado al activity detalle en forma de []String
        intent.putExtra("post",post.convertToString());

        startActivity(intent);

        adapter_PostList.notifyItemChanged(position);
    }




    private void listingPosts(List<Post> posts){
        //Este metodo lista los posts en el recyclerview, si es la primera vez carga las instancias necesarias
        // sino y como ya existen las instancias solo se le cambia la lista de posts y se actualiza
        if(isFirstPostsLoad) {

            adapter_PostList = new PostsListAdapter(posts, R.layout.template_rcv_postslist, R.layout.footer_rcv_postslist,this);
            postsList.setAdapter(adapter_PostList);
            postsList.setLayoutManager(manager_PostList);
            // swipe efect
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter_PostList));
            itemTouchHelper.attachToRecyclerView(postsList);

        }else{

            adapter_PostList.setPosts(posts);
            adapter_PostList.notifyDataSetChanged();
            postsList.scrollToPosition(0);

        }

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

                    if( !database.savePost(post) ){// sino logro guardar significa que ya existe
                        postsResponse.set(i,database.readPost(post.getId()));
                    }
                }

                listingPosts(postsResponse);

                database.close();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

                if(!isFirstPostsLoad){
                    listingPosts(new Database(Principal.this).listPost("",""));
                }

                Toast.makeText(Principal.this, "Error al obtener datos del servidor", Toast.LENGTH_SHORT).show();

            }
        });


    }




}

