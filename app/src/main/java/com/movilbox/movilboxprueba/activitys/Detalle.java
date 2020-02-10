package com.movilbox.movilboxprueba.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.movilbox.movilboxprueba.Retrofit.RetrofitInstance;
import com.movilbox.movilboxprueba.R;
import com.movilbox.movilboxprueba.Retrofit.Requests;
import com.movilbox.movilboxprueba.adapters.CommentsListAdapter;
import com.movilbox.movilboxprueba.models.Comment;
import com.movilbox.movilboxprueba.models.Post;
import com.movilbox.movilboxprueba.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detalle extends AppCompatActivity {

    TextView txt_userName,txt_title,txt_body;
    ListView lst_commentsList;
    Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        post = new Post();

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Details");

        txt_userName = findViewById(R.id.txt_userName_detalle);
        txt_title = findViewById(R.id.txt_title_detalle);
        txt_body = findViewById(R.id.txt_body_detalle);
        lst_commentsList = findViewById(R.id.lst_commentsList_detalle);

        if (getIntent().getExtras() != null){
            post = post.convertToPost( getIntent().getExtras().getStringArray("post"));
            txt_title.setText(post.getTitle());
            txt_body.setText(post.getBody());
        }

        getDataUser();
        desplegarComments();


    }


    private void getDataUser(){

        Requests service = RetrofitInstance.getInstance(true).create(Requests.class);
        Call<User> userCall = service.getDataUser(post.getUserId());

        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, final Response<User> response) {

                final User user = response.body();

            txt_userName.setText(user.getUsername());
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
                Toast.makeText(Detalle.this, "Error con datos del servidor", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void desplegarComments(){

        Requests service = RetrofitInstance.getInstance(false).create(Requests.class);
        Call<List<Comment>> postCall = service.getPostComments(post.getId());

        postCall.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                CommentsListAdapter adapter = new CommentsListAdapter(Detalle.this,response.body(),R.layout.template_lst_commentslist);
                lst_commentsList.setAdapter(adapter);

            }
            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Toast.makeText(Detalle.this, "Error al obtener datos del servidor", Toast.LENGTH_SHORT).show();
            }
        });

    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
