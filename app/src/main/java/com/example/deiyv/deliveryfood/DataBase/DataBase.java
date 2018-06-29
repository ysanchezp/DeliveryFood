package com.example.deiyv.deliveryfood.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.deiyv.deliveryfood.Model.Pedido;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteAssetHelper {
    private static final   String DB_Name="pedidoDB.db";
    private static final int DB_VER=1;
    public DataBase(Context context) {
        super(context, DB_Name, null, DB_VER);
    }

    public List<Pedido> getPedidos(){
        SQLiteDatabase db=getReadableDatabase();
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();

        String[]sqlSelect={"ProductoNombre","ProductoId","Cantidad","Precio"};
        String sqlTable="DetallePedido";

        qb.setTables(sqlTable);
        Cursor c= qb.query(db,sqlSelect,null,null,null,null,null);

        final List<Pedido> result= new ArrayList<>();

        if (c.moveToFirst()){
            do{
                result.add(new Pedido(c.getString(c.getColumnIndex("ProductoId")),
                        c.getString(c.getColumnIndex("ProductoNombre")),
                        c.getString(c.getColumnIndex("Cantidad")),
                        c.getString(c.getColumnIndex("Precio"))

                        ));
            }while (c.moveToNext());
        }
        return result;
    }

    public void addToCart (Pedido pedido){
        SQLiteDatabase db= getReadableDatabase();
        String query=String.format("INSERT INTO DetallePedido(ProductoId,ProductoNombre,Cantidad,Precio) VALUES ('%s','%s','%s','%s');",
                pedido.getProductoID(),
                pedido.getProductoNombre(),
                pedido.getCantidad(),
                pedido.getPrecio());

        db.execSQL(query);
    }

    public void cleanCart (){
        SQLiteDatabase db= getReadableDatabase();
        String query=String.format("DELETE FROM DetallePedido ");

        db.execSQL(query);
    }

}
