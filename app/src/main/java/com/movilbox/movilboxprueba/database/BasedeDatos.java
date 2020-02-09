package com.movilbox.movilboxprueba.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.movilbox.movilboxprueba.models.Post;

import java.util.ArrayList;
import java.util.List;

public class BasedeDatos extends SQLiteOpenHelper {



    public BasedeDatos(@Nullable Context context) {
        super(context, "app.db", null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + CamposBasedeDatos.TABLE + "(" +
                CamposBasedeDatos.USER_ID + " TEXT , " +
                CamposBasedeDatos.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                CamposBasedeDatos.TITLE + " TEXT ,"+
                CamposBasedeDatos.BODY + " TEXT ,"+
                CamposBasedeDatos.FAVORITE + " TEXT ,"+
                CamposBasedeDatos.VIEWED + " TEXT "+
                ")");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + CamposBasedeDatos.TABLE);

        onCreate(db);
    }




    public boolean savePost(Post post){

        ContentValues valores = new ContentValues();

        valores.put(CamposBasedeDatos.USER_ID, post.getUserId());
        valores.put(CamposBasedeDatos.ID, post.getId());
        valores.put(CamposBasedeDatos.TITLE, post.getTitle());
        valores.put(CamposBasedeDatos.BODY, post.getBody());
        valores.put(CamposBasedeDatos.FAVORITE, post.getFavorite());
        valores.put(CamposBasedeDatos.VIEWED,post.getViewed());

        SQLiteDatabase bd = getWritableDatabase();

        long num = bd.insert(CamposBasedeDatos.TABLE, null, valores);

        if(num>0){
            return true;
        }
        else {
            return false;
        }
    }




    public Post readPost(String id_post) {

        String[] columns = {CamposBasedeDatos.USER_ID, CamposBasedeDatos.ID,CamposBasedeDatos.TITLE,
                            CamposBasedeDatos.BODY, CamposBasedeDatos.FAVORITE,CamposBasedeDatos.VIEWED};
        String selection = CamposBasedeDatos.ID + " = ? ";
        String[] selectionArgs = {id_post};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;

        SQLiteDatabase bd = getReadableDatabase();

        Cursor data = bd.query(CamposBasedeDatos.TABLE, columns, selection, selectionArgs, groupBy, having, orderBy, limit);


        if (data.moveToFirst()) {
            Post post = new Post();

            post.setUserId(data.getString(data.getColumnIndex(CamposBasedeDatos.USER_ID)));
            post.setId(data.getString(data.getColumnIndex(CamposBasedeDatos.ID)));
            post.setTitle(data.getString(data.getColumnIndex(CamposBasedeDatos.TITLE)));
            post.setBody(data.getString(data.getColumnIndex(CamposBasedeDatos.BODY)));
            post.setFavorite(data.getString(data.getColumnIndex(CamposBasedeDatos.FAVORITE)));
            post.setViewed(data.getString(data.getColumnIndex(CamposBasedeDatos.VIEWED)));

            data.close();

            return post;
        } else {
            return null;
        }
    }




    public void deletePost (Post post) {

        SQLiteDatabase bd = getWritableDatabase();

        String[] argumento = {post.getId()};

        bd.delete(CamposBasedeDatos.TABLE,CamposBasedeDatos.ID+"=?",argumento);

    }




    public List<Post> listPost(String queBuscar){

        SQLiteDatabase bd = getReadableDatabase();
        Cursor data;
        List<Post> postList = new ArrayList<>();

        if (queBuscar.isEmpty()) {
            data = bd.rawQuery("SELECT * FROM " + CamposBasedeDatos.TABLE, null);

        }else {
            data = bd.rawQuery("SELECT * FROM " + CamposBasedeDatos.TABLE+" where "+CamposBasedeDatos.ID+ " like '%" + queBuscar + "%'", null);
        }

        if (data.moveToFirst()) {
            for (int i = 0; i < data.getCount() ; i++) {
                postList.add(new Post (data.getString(data.getColumnIndex(CamposBasedeDatos.USER_ID)),
                                        data.getString(data.getColumnIndex(CamposBasedeDatos.ID)),
                                        data.getString(data.getColumnIndex(CamposBasedeDatos.TITLE)),
                                        data.getString(data.getColumnIndex(CamposBasedeDatos.BODY)),
                                        data.getString(data.getColumnIndex(CamposBasedeDatos.VIEWED)),
                                        data.getString(data.getColumnIndex(CamposBasedeDatos.FAVORITE))));
                data.moveToNext();
            }
        }
        data.close();

        return postList;
    }




    public boolean updatePost (Post post){

        ContentValues valores = new ContentValues();

        valores.put(CamposBasedeDatos.USER_ID, post.getUserId());
        valores.put(CamposBasedeDatos.ID, post.getId());
        valores.put(CamposBasedeDatos.TITLE, post.getTitle());
        valores.put(CamposBasedeDatos.BODY, post.getBody());
        valores.put(CamposBasedeDatos.FAVORITE, post.getFavorite());
        valores.put(CamposBasedeDatos.VIEWED,post.getViewed());

        String[] selectionArgs = {post.getId()};

        SQLiteDatabase bd = getWritableDatabase();

        int var = bd.update(CamposBasedeDatos.TABLE,valores,CamposBasedeDatos.ID+"=?", selectionArgs);

        if(var > 0){
            return true;
        }else {
            return false;
        }

    }




}

