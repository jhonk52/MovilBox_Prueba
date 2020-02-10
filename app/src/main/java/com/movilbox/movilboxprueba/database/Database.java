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

public class Database extends SQLiteOpenHelper {



    public Database(@Nullable Context context) {
        super(context, "app.db", null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DatabaseFields.TABLE + "(" +
                DatabaseFields.USER_ID + " TEXT , " +
                DatabaseFields.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                DatabaseFields.TITLE + " TEXT ,"+
                DatabaseFields.BODY + " TEXT ,"+
                DatabaseFields.FAVORITE + " TEXT ,"+
                DatabaseFields.VIEWED + " TEXT "+
                ")");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + DatabaseFields.TABLE);

        onCreate(db);
    }




    public boolean savePost(Post post){

        ContentValues valores = new ContentValues();

        valores.put(DatabaseFields.USER_ID, post.getUserId());
        valores.put(DatabaseFields.ID, post.getId());
        valores.put(DatabaseFields.TITLE, post.getTitle());
        valores.put(DatabaseFields.BODY, post.getBody());
        valores.put(DatabaseFields.FAVORITE, post.getFavorite());
        valores.put(DatabaseFields.VIEWED,post.getViewed());

        SQLiteDatabase bd = getWritableDatabase();

        long num = bd.insert(DatabaseFields.TABLE, null, valores);

        return num > 0;
    }




    public Post readPost(String id_post) {

        String[] columns = {DatabaseFields.USER_ID, DatabaseFields.ID, DatabaseFields.TITLE,
                            DatabaseFields.BODY, DatabaseFields.FAVORITE, DatabaseFields.VIEWED};
        String selection = DatabaseFields.ID + " = ? ";
        String[] selectionArgs = {id_post};
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;

        SQLiteDatabase bd = getReadableDatabase();

        Cursor data = bd.query(DatabaseFields.TABLE, columns, selection, selectionArgs, groupBy, having, orderBy, limit);


        if (data.moveToFirst()) {
            Post post = new Post(data.getString(data.getColumnIndex(DatabaseFields.USER_ID)),
                    data.getString(data.getColumnIndex(DatabaseFields.ID)),
                    data.getString(data.getColumnIndex(DatabaseFields.TITLE)),
                    data.getString(data.getColumnIndex(DatabaseFields.BODY)),
                    data.getString(data.getColumnIndex(DatabaseFields.FAVORITE)),
                    data.getString(data.getColumnIndex(DatabaseFields.VIEWED)));

            data.close();

            return post;
        } else {
            return null;
        }
    }




    public void deletePost (Post post) {

        SQLiteDatabase bd = getWritableDatabase();

        String[] pathfinder = {post.getId()};

        bd.delete(DatabaseFields.TABLE, DatabaseFields.ID+"=?",pathfinder);

    }




    public List<Post> listPost(String find){

        SQLiteDatabase bd = getReadableDatabase();
        Cursor data;
        List<Post> postList = new ArrayList<>();

        if (find.isEmpty()) {
            data = bd.rawQuery("SELECT * FROM " + DatabaseFields.TABLE, null);

        }else {
            data = bd.rawQuery("SELECT * FROM " + DatabaseFields.TABLE+" where "+ DatabaseFields.ID+ " like '%" + find + "%'", null);
        }

        if (data.moveToFirst()) {
            for (int i = 0; i < data.getCount() ; i++) {
                postList.add(new Post (data.getString(data.getColumnIndex(DatabaseFields.USER_ID)),
                                        data.getString(data.getColumnIndex(DatabaseFields.ID)),
                                        data.getString(data.getColumnIndex(DatabaseFields.TITLE)),
                                        data.getString(data.getColumnIndex(DatabaseFields.BODY)),
                                        data.getString(data.getColumnIndex(DatabaseFields.FAVORITE)).equals("1")?"true":"false",
                                        data.getString(data.getColumnIndex(DatabaseFields.VIEWED)).equals("1")?"true":"false"));
                data.moveToNext();
            }
        }
        data.close();

        return postList;
    }




    public boolean updatePost (Post post){

        ContentValues valores = new ContentValues();

        valores.put(DatabaseFields.USER_ID, post.getUserId());
        valores.put(DatabaseFields.ID, post.getId());
        valores.put(DatabaseFields.TITLE, post.getTitle());
        valores.put(DatabaseFields.BODY, post.getBody());
        valores.put(DatabaseFields.FAVORITE, post.getFavorite());
        valores.put(DatabaseFields.VIEWED,post.getViewed());

        String[] selectionArgs = {post.getId()};

        SQLiteDatabase bd = getWritableDatabase();

        int var = bd.update(DatabaseFields.TABLE,valores, DatabaseFields.ID+"=?", selectionArgs);

        return var > 0;

    }




}

