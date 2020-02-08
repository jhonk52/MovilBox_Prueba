package com.movilbox.movilboxprueba.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.TextViewCompat;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.movilbox.movilboxprueba.API;
import com.movilbox.movilboxprueba.R;
import com.movilbox.movilboxprueba.ServiceHTTP;
import com.movilbox.movilboxprueba.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detalle extends AppCompatActivity {

    TextView txt_title,txt_body;
    Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        post = new Post("","","","");

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Details");

        txt_title = findViewById(R.id.txt_title_detalle);
        txt_body = findViewById(R.id.txt_body_detalle);

        if (getIntent().getExtras() != null){
            post = post.convertToPost( getIntent().getExtras().getStringArray("post"));
        }

        txt_title.setText(post.getTitle());
        txt_body.setText(post.getBody());

    }




}
