package com.wposs.buc.restpapp.BD.Controler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wposs.buc.restpapp.BD.Model.Usuarios;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClsConexion extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    public static Usuarios usuario;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "resto_app.db";

    /**
     * TABLE productos_new
     * Colums:
     *  id
     *  titulo
     *  valor
     *  categoria
     *  descripcion
     */
    private final String TABLE_PROD_NEW = "productos_new";
    private final String COLUMN_PROD_ID= "prod_id";
    private final String COLUMN_PROD_TITULO = "prod_titulo";
    private final String COLUMN_PROD_VALOR = "prod_valor";
    private final String COLUMN_PROD_CATEGORIA = "prod_categoria";
    private final String COLUMN_PROD_DESCRIPCION = "prod_descripcion";

    private final String CREATE_PRODUCTOS_TABLE_NEW = "create table "+ TABLE_PROD_NEW + "(" +
            COLUMN_PROD_ID +" integer primary key AUTOINCREMENT, " +
            COLUMN_PROD_TITULO + " text not null, " +
            COLUMN_PROD_VALOR + " integer not null, " +
            COLUMN_PROD_CATEGORIA + " text not null, " +
            COLUMN_PROD_DESCRIPCION + " text);";

    /**
     * TABLE categorias_new
     * Columns:
     *  id
     *  name
     */
    private final String TABLE_CATEGORIAS_NEW = "categorias_new";
    private final String COLUMN_CATEG_ID= "categ_id";
    private final String COLUMN_CATEG_NAME = "categ_name";

    private final String CREATE_CATEGORIAS_TABLE_NEW = "create table " + TABLE_CATEGORIAS_NEW + "(" +
            COLUMN_CATEG_ID + " integer primary key AUTOINCREMENT, " +
            COLUMN_CATEG_NAME + " text not null);";

    /**
     * TABLE usuarios_new
     * Colums:
     *  id
     *  user
     *  pass
     *  name
     *  status
     *  role
     */
    private final String TABLE_USUARIOS_NEW = "usuarios_new";
    private final String COLUMN_USER_ID = "user_id";
    private final String COLUMN_USER_USER = "user_user";
    private final String COLUMN_USER_PASS = "user_pass";
    private final String COLUMN_USER_NAME = "user_name";
    private final String COLUMN_USER_ROLE = "user_role";
    private final String COLUMN_USER_STATUS = "user_status";

    private final String CREATE_USUARIOS_TABLE_NEW = "create table " + TABLE_USUARIOS_NEW + "(" +
            COLUMN_USER_ID + " integer primary key not null, " +
            COLUMN_USER_USER + " text not null, " +
            COLUMN_USER_PASS + " text not null, " +
            COLUMN_USER_NAME + " text not null, " +
            COLUMN_USER_ROLE + " text not null, " +
            COLUMN_USER_STATUS + " text not null);";

    private final int USUARIO_ADMIN_ID = 1234567890;
    private final String USUARIO_ADMIN_USER = "admin";
    private final String USUARIO_ADMIN_PASS = "123456";
    private final String USUARIO_ADMIN_NAME = "Administrador";
    private final String USUARIO_ADMIN_ROLE = "Admin";
    private final String USUARIO_ADMIN_STATUS = "activado";

    private final String INSERT_USUARIO_ADMIN = ("insert into " + TABLE_USUARIOS_NEW +" values('"+USUARIO_ADMIN_ID+"'," +
            "'"+USUARIO_ADMIN_USER+"','"+USUARIO_ADMIN_PASS+"','"+USUARIO_ADMIN_NAME+"','"+USUARIO_ADMIN_ROLE+"','"+USUARIO_ADMIN_STATUS+"');");

    public ClsConexion(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PRODUCTOS_TABLE_NEW);
        db.execSQL(CREATE_CATEGORIAS_TABLE_NEW);
        db.execSQL(CREATE_USUARIOS_TABLE_NEW);
        db.execSQL(INSERT_USUARIO_ADMIN);

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
        String query = "SELECT * FROM " + TABLE_CATEGORIAS_NEW;
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

    public boolean crearUsuario (int id, String user, String pass, String name, String role, String status){
        boolean ret = false;
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, id);
        values.put(COLUMN_USER_USER, user);
        values.put(COLUMN_USER_PASS, pass);
        values.put(COLUMN_USER_NAME, name);
        values.put(COLUMN_USER_ROLE, role);
        values.put(COLUMN_USER_STATUS, status);
        try {
            db.insert(TABLE_USUARIOS_NEW,null,values);
            db.close();
            ret = true;
        }catch (SQLException e){
            e.getCause();
        }
        return ret;
    }

    public ArrayList<String> getAllUsuariosUser() {
        ArrayList<String> users = new ArrayList<String>();
        db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_USER_USER + " FROM " + TABLE_USUARIOS_NEW;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                users.add(cursor.getString(cursor.getColumnIndex(COLUMN_USER_USER)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return users;
    }

    public ArrayList<Integer> getAllUsuariosId() {
        ArrayList<Integer> users_id = new ArrayList<Integer>();
        db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_USER_ID + " FROM " + TABLE_USUARIOS_NEW;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                users_id.add(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return users_id;
    }

    public Usuarios readUser(Usuarios user) {
        db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_USUARIOS_NEW,
                new String[]{COLUMN_USER_ID, COLUMN_USER_USER, COLUMN_USER_PASS,COLUMN_USER_NAME,COLUMN_USER_ROLE,COLUMN_USER_STATUS},
                COLUMN_USER_USER + "=?",
                new String[] {user.getUser()},
                null,null,null
        );

        if (cursor !=null && cursor.moveToFirst() && cursor.getCount()>0){
            Usuarios userData = new Usuarios(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
                    cursor.getString(3),cursor.getString(4),cursor.getString(5));
            usuario = userData;
            usuario.setId(cursor.getInt(0));
            usuario.setUser(cursor.getString(1));
            usuario.setPass(cursor.getString(2));
            usuario.setName(cursor.getString(3));
            usuario.setRole(cursor.getString(4));
            usuario.setStatus(cursor.getString(5));
            db.close();
            return userData;
        }
        db.close();
        return null;
    }
}
