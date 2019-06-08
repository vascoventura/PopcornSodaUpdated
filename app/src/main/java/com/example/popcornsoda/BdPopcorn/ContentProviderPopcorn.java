package com.example.popcornsoda.BdPopcorn;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.popcornsoda.BdPopcorn.BdPopcornOpenHelper;
import com.example.popcornsoda.BdPopcorn.BdTableAutores;
import com.example.popcornsoda.BdPopcorn.BdTableFilmes;
import com.example.popcornsoda.BdPopcorn.BdTableSeries;


public class ContentProviderPopcorn extends ContentProvider {

    public static final String AUTHORITY = "com.example.popcornsoda.BdPopcorn";
    public static final String AUTORES = "autores";
    public static final String FILMES = "filmes";
    public static final String SERIES = "series";


    private static final Uri ENDERECO_BASE = Uri.parse("content://" + AUTHORITY);
    public static final Uri ENDERECO_AUTORES = Uri.withAppendedPath(ENDERECO_BASE, AUTORES);
    public static final Uri ENDERECO_FILMES = Uri.withAppendedPath(ENDERECO_BASE, FILMES);
    public static final Uri ENDERECO_SERIES = Uri.withAppendedPath(ENDERECO_BASE, SERIES);

    public static final int URI_AUTORES = 100;
    public static final int URI_AUTOR_ESPECÍFICO = 101;
    public static final int URI_FILMES = 200;
    public static final int URI_FILME_ESPECÍFICO = 201;
    public static final int URI_SERIES = 300;
    public static final int URI_SERIE_ESPECIFICA = 301;
    
    public static final String UNICO_ITEM = "vnd.android.cursor.item/";
    public static final String MULTIPLOS_ITEMS = "vnd.android.cursor.dir/";

    private BdPopcornOpenHelper bdPopcornOpenHelper;

    private UriMatcher getUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTHORITY, AUTORES, URI_AUTORES);
        uriMatcher.addURI(AUTHORITY, AUTORES + "/#", URI_AUTOR_ESPECÍFICO);
        uriMatcher.addURI(AUTHORITY, FILMES, URI_FILMES);
        uriMatcher.addURI(AUTHORITY, FILMES + "/#", URI_FILME_ESPECÍFICO);
        uriMatcher.addURI(AUTHORITY, SERIES, URI_SERIES);
        uriMatcher.addURI(AUTHORITY, SERIES + "/#", URI_SERIE_ESPECIFICA);


        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        bdPopcornOpenHelper = new BdPopcornOpenHelper(getContext());

        return true;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase bd = bdPopcornOpenHelper.getReadableDatabase();

        String id = uri.getLastPathSegment();

        switch (getUriMatcher().match(uri)) {
            case URI_AUTORES:
                return new BdTableAutores(bd).query(projection, selection, selectionArgs, null, null, sortOrder);

            case URI_AUTOR_ESPECÍFICO:
                return new BdTableAutores(bd).query(projection, BdTableAutores._ID + "=?", new String[] { id }, null, null, null);

            case URI_FILMES:
                return new BdTableFilmes(bd).query(projection, selection, selectionArgs, null, null, sortOrder);

            case URI_FILME_ESPECÍFICO:
                return  new BdTableFilmes(bd).query(projection, BdTableFilmes.NOME_TABELA + "." + BdTableFilmes._ID + "=?", new String[] { id }, null, null, null);

            case URI_SERIES:
                return new BdTableSeries(bd).query(projection, selection, selectionArgs, null, null, sortOrder);

            case URI_SERIE_ESPECIFICA:
                return  new BdTableSeries(bd).query(projection, BdTableSeries.NOME_TABELA + "." + BdTableSeries._ID + "=?", new String[] { id }, null, null, null);

            default:
                throw new UnsupportedOperationException("URI inválida (QUERY): " + uri.toString());
        }
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (getUriMatcher().match(uri)) {
            case URI_AUTORES:
                return MULTIPLOS_ITEMS + AUTORES;
            case URI_AUTOR_ESPECÍFICO:
                return UNICO_ITEM + AUTORES;
            case URI_FILMES:
                return MULTIPLOS_ITEMS + FILMES;
            case URI_FILME_ESPECÍFICO:
                return UNICO_ITEM + FILMES;
            case URI_SERIES:
                return MULTIPLOS_ITEMS + SERIES;
            case URI_SERIE_ESPECIFICA:
                return UNICO_ITEM + SERIES;
            default:
                return null;
        }
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase bd = bdPopcornOpenHelper.getWritableDatabase();

        long id = -1;

        switch (getUriMatcher().match(uri)) {
            case URI_AUTORES:
                id = new BdTableAutores(bd).insert(values);
                break;

            case URI_FILMES:
                id = new BdTableFilmes(bd).insert(values);
                break;

            case URI_SERIES:
                id = new BdTableSeries(bd).insert(values);
                break;

            default:
                throw new UnsupportedOperationException("URI inválida (INSERT):" + uri.toString());
        }

        if (id == -1) {
            throw new SQLException("Não foi possível inserir o registo");
        }

        return Uri.withAppendedPath(uri, String.valueOf(id));
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase bd = bdPopcornOpenHelper.getWritableDatabase();

        String id = uri.getLastPathSegment();

        switch (getUriMatcher().match(uri)) {
            case URI_AUTOR_ESPECÍFICO:
                return new BdTableAutores(bd).delete( BdTableAutores._ID + "=?", new String[] {id});
            case URI_FILME_ESPECÍFICO:
                return new BdTableFilmes(bd).delete(BdTableFilmes._ID + "=?", new String[] {id});
            case URI_SERIE_ESPECIFICA:
                return new BdTableSeries(bd).delete(BdTableSeries._ID + "=?", new String[] {id});
            default:
                throw new UnsupportedOperationException("URI inválida (DELETE): " + uri.toString());
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase bd = bdPopcornOpenHelper.getWritableDatabase();

        String id = uri.getLastPathSegment();

        switch (getUriMatcher().match(uri)) {
            case URI_AUTOR_ESPECÍFICO:
                return new BdTableAutores(bd).update(values, BdTableAutores._ID + "=?", new String[] {id});
            case URI_FILME_ESPECÍFICO:
                return new BdTableFilmes(bd).update(values, BdTableFilmes._ID + "=?", new String[] {id});
            case URI_SERIE_ESPECIFICA:
                return new BdTableSeries(bd).update(values,BdTableSeries._ID + "=?", new String[] {id});
            default:
                throw new UnsupportedOperationException("URI inválida (UPDATE): " + uri.toString());
        }
    }
}


