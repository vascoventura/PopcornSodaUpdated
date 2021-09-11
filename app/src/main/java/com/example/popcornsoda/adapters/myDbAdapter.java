package com.example.popcornsoda.adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.popcornsoda.BdPopcorn.BdPopcornOpenHelper;
import com.example.popcornsoda.BdPopcorn.BdTableCategorias;
import com.example.popcornsoda.models.Categoria;

import java.util.ArrayList;
import java.util.List;

public class myDbAdapter {
    private BdPopcornOpenHelper myhelper;

    public myDbAdapter(Context context) {
        myhelper = new BdPopcornOpenHelper(context);
    }

    /*public long insertData(String name, String pass) {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BdPopcornOpenHelper.NAME, name);
        contentValues.put(BdPopcornOpenHelper.MyPASSWORD, pass);
        long id = dbb.insert(BdPopcornOpenHelper.TABLE_NAME, null, contentValues);
        return id;
    }*/

    public String getData() {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {BdTableCategorias._ID, BdTableCategorias.CAMPO_NOME};
        Cursor cursor = db.query(BdTableCategorias.NOME_TABELA, columns, null, null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            int cid = cursor.getInt(cursor.getColumnIndex(BdTableCategorias._ID));
            String cname = cursor.getString(cursor.getColumnIndex(BdTableCategorias.CAMPO_NOME));
            buffer.append(cid + " " + cname  + " \n");
        }
        return buffer.toString();
    }

    public String[] getCategorias(){
        Cursor cursor = myhelper.getReadableDatabase().rawQuery("SELECT nome_categoria FROM categorias", null);
        String [] names = {""};
        for(int i = 0; i < cursor.getCount(); i ++){
            names[i] = cursor.getString(i);
        }
        cursor.close();
        return names;
    }

    public List<Categoria> getAll(){

        SQLiteDatabase db = myhelper.getReadableDatabase();

        List<Categoria> categoriaList = new ArrayList<>();

        String query = "Select nome_categoria from categorias";


        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            //loop throught the cursor
            do{
                String nome_categoria = cursor.getString(1);

                Categoria categoria = new Categoria(nome_categoria);
                categoriaList.add(categoria);
            }while(cursor.moveToNext());
        }else{

        }
        cursor.close();
        db.close();
        return categoriaList;
    }

    /*public int delete(String uname) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs = {uname};
        int count = db.delete(BdPopcornOpenHelper.TABLE_NAME, BdPopcornOpenHelper.NAME + " = ?", whereArgs);
        return count;

    }*/

    /*public int updateName(String oldName, String newName) {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BdPopcornOpenHelper.NAME, newName);
        String[] whereArgs = {oldName};
        int count = db.update(BdPopcornOpenHelper.TABLE_NAME, contentValues, BdPopcornOpenHelper.NAME + " = ?", whereArgs);
        return count;
    }*/
}
