package com.wposs.buc.restpapp.BD.Controler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClsConexion extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "productos_rest.db";


    private final String TABLE_PROD_NEW = "productos_new";
    private final String COLUMN_PROD_ID= "prod_id";
    private final String COLUMN_PROD_TITULO = "prod_titulo";
    private final String COLUMN_PROD_VALOR = "prod_valor";
    private final String COLUMN_PROD_CATEGORIA = "prod_categoriao";
    private final String COLUMN_PROD_DESCRIPCION = "prod_descripcion";

    private final String CREATE_PRODUCTOS_TABLE_NEW = "create table "+ TABLE_PROD_NEW + "(" +
            COLUMN_PROD_ID +" integer primary key AUTOINCREMENT, " +
            COLUMN_PROD_TITULO + " text not null, " +
            COLUMN_PROD_VALOR + " integer not null, " +
            COLUMN_PROD_CATEGORIA + " text not null, " +
            COLUMN_PROD_DESCRIPCION + " text);";

    private final String TABLE_CATEGORIAS_NEW = "categorias_new";
    private final String COLUMN_CATEG_ID= "categ_id";
    private final String COLUMN_CATEG_NAME = "categ_name";

    private final String CREATE_CATEGORIAS_TABLE_NEW = "create table " + TABLE_CATEGORIAS_NEW + "(" +
            COLUMN_CATEG_ID + " integer primary key AUTOINCREMENT, " +
            COLUMN_CATEG_NAME + " text not null);";



    public ClsConexion(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PRODUCTOS_TABLE_NEW);
        db.execSQL(CREATE_CATEGORIAS_TABLE_NEW);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean crearProducto(String titulo, int valor, String categoria, String descripcion){
        boolean ret = false;
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PROD_TITULO, titulo);
        values.put(COLUMN_PROD_VALOR, valor);
        values.put(COLUMN_PROD_CATEGORIA, categoria);
        values.put(COLUMN_PROD_DESCRIPCION, descripcion);
        try {
            db.insert(TABLE_PROD_NEW,null,values);
            db.close();
            ret = true;
        }catch (SQLException e){
            e.getCause();
        }
        return ret;
    }

    public boolean updateProducto(){
        boolean ret = false;



        return ret;
    }

    public void deleteProducto(String producto) {
        db = this.getWritableDatabase();
        db.delete(TABLE_PROD_NEW, COLUMN_PROD_TITULO + "='"
                + producto + "'", null);
        db.close();
    }

    public ArrayList<HashMap<String, String>> getAllProductos() {
        db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT prod_titulo, prod_descripcion, prod_valor FROM "+ TABLE_PROD_NEW;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("titulo",cursor.getString(cursor.getColumnIndex(COLUMN_PROD_TITULO)));
            user.put("descripcion",cursor.getString(cursor.getColumnIndex(COLUMN_PROD_DESCRIPCION)));
            user.put("valor",cursor.getString(cursor.getColumnIndex(COLUMN_PROD_VALOR)));
            userList.add(user);
        }
        cursor.close();
        db.close();
        return  userList;
    }

    public void crearCategoria(String categoria){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEG_NAME, categoria);
        try {
            db.insert(TABLE_CATEGORIAS_NEW, null, values);
            db.close();
        }catch (SQLException e){
            e.getCause();
        }
    }

    public boolean updateCategorias(String categoria){
        boolean ret = false;

        // Debe actualizar todos los productos

        return ret;
    }

    public void deleteCategoria(String categoria) {
        db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORIAS_NEW, COLUMN_CATEG_NAME + "='"
                + categoria + "'", null);
        db.close();
    }

    public ArrayList<String> getAllCategorias(){
        db = this.getWritableDatabase();
        ArrayList<String> categorias = new ArrayList<String>();
        String query = "SELECT * " + "FROM " + TABLE_CATEGORIAS_NEW;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                categorias.add(cursor.getString(cursor.getColumnIndex(COLUMN_CATEG_NAME)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categorias;
    }
}
