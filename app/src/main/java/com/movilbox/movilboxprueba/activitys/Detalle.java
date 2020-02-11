package com.movilbox.movilboxprueba.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.movilbox.movilboxprueba.Retrofit.RetrofitInstance;
import com.movilbox.movilboxprueba.R;
import com.movilbox.movilboxprueba.Retrofit.Requests;
import com.movilbox.movilboxprueba.database.Database;
import com.movilbox.movilboxprueba.models.Post;
import com.movilbox.movilboxprueba.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detalle extends AppCompatActivity {

    TextView txt_userName,txt_title,txt_body;
    Post resivedPost;
    ImageButton btn_favorite;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        resivedPost = new Post();

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Details");

        txt_userName = findViewById(R.id.txt_userName_detalle);
        txt_title = findViewById(R.id.txt_title_detalle);
        txt_body = findViewById(R.id.txt_body_detalle);
        btn_favorite = findViewById(R.id.btn_favorite_detalle);


        if (getIntent().getExtras() != null){
            // Obtengo la nota seleccionada en la pantalla principal
            resivedPost = resivedPost.convertToPost(getIntent().getExtras().getStringArray("post"));

            txt_title.setText(resivedPost.getTitle());
            txt_body.setText(resivedPost.getBody());

            // guardo en basededatos el post con la propiedad viewed en true, sginifica que al llegar aqui ha sido leido
            resivedPost.setViewed(true);
            Database database = new Database(Detalle.this);
                    database.updatePost(resivedPost);
            database.close();



            btn_favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resivedPost.setFavorite(true);
                    // guardo en basededatos el post con la propiedad favorite en true
                    Database database = new Database(Detalle.this);
                        database.updatePost(resivedPost);
                    database.close();

                    Toast.makeText(Detalle.this, "favorite", Toast.LENGTH_SHORT).show();
                    btn_favorite.setVisibility(View.GONE);
                }
            });

            getDataUser(); //obtengo la informacion del usuario que asociado al post

        }



    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }




    private void getDataUser(){

        Requests service = RetrofitInstance.getInstance(true).create(Requests.class);
        Call<User> userCall = service.getDataUser(resivedPost.getUserId());

        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, final Response<User> response) {

                final User user = response.body();

                txt_userName.setText(user.getUsername());

                // para ver la info completa del usuario hay q dar click en el nombre (se ve en negrilla en la interfaz)
                txt_userName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AlertDialog.Builder(Detalle.this).setTitle(user.getUsername())
                                .setMessage("Name: " + user.getName()+"\n"+
                                        "Email: " + user.getEmail()+"\n"+
                                        "Phone: " + user.getPhone()+"\n"+
                                        "WebSite: " + user.getWebsite()+"\n"+
                                        "Address:\n"+
                                        user.getAddress_suite() + " of " + user.getAddress_street() + " street"+"\n"+
                                        "City: " + user.getAddress_city()+"\n"+
                                        "ZipCode: " + user.getAddress_zipcode()+"\n"+
                                        "Company: " + user.getCompany_name()+"\n"+
                                        user.getCompany_catchPhrase()+"\n"+
                                        user.getCompany_bs()
                                )
                                .setPositiveButton("Aceptar",null)
                                .show();
                    }
                });

            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(Detalle.this, "Error al obtener datos del servidor", Toast.LENGTH_SHORT).show();
            }
        });

    }



}
