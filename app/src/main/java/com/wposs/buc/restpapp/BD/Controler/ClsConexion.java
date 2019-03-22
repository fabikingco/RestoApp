package com.wposs.buc.restpapp.BD.Controler;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    final String CREATE_PRODUCTOS_TABLE_NEW = "create table "+ TABLE_PROD_NEW + "(" +
            COLUMN_PROD_ID +" integer primary key AUTOINCREMENT, " +
            COLUMN_PROD_TITULO + " text not null, " +
            COLUMN_PROD_VALOR + " integer not null, " +
            COLUMN_PROD_CATEGORIA + " text not null, " +
            COLUMN_PROD_DESCRIPCION + " text);";


    public ClsConexion(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PRODUCTOS_TABLE_NEW);

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
}
