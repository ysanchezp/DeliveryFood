package com.example.deiyv.deliveryfood;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deiyv.deliveryfood.DataBase.DataBase;
import com.example.deiyv.deliveryfood.Model.Pedido;

import com.example.deiyv.deliveryfood.Model.Request;
import com.example.deiyv.deliveryfood.ViewHolder.CartAdapter;
import com.example.deiyv.deliveryfood.common.Common;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotalPrecio;
    Button btnRealizarPedido;

    List<Pedido> cart = new ArrayList<>();

    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Request");

        // incializamos

        recyclerView = (RecyclerView) findViewById(R.id.listaCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrecio = (TextView) findViewById(R.id.total);
        btnRealizarPedido = (Button) findViewById(R.id.realizar_pedido);

        btnRealizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cart.size()>0)
                    showAlertDialog();
                else
                    Toast.makeText(Cart.this,"Tu lista esta Vacia!!",Toast.LENGTH_SHORT).show();
            }
        });

        loadListComida();

    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("¡Un Paso Mas!");
        alertDialog.setMessage("Introduzca su Direccion");

        final EditText edtDireccion = new EditText(Cart.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtDireccion.setLayoutParams(lp);
        alertDialog.setView(edtDireccion); // agrega un editext en la alerta
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        AlertDialog.Builder builder = alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // crea nueva solicitud
                Request request= new Request(
                        Common.currentUser.getTelefono(),
                        Common.currentUser.getName(),
                        edtDireccion.getText().toString(),
                        txtTotalPrecio.getText().toString(),
                        cart

                );

                // para enviar a firebase usaremos Systemm.currentMilli to key
                requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);

                // eliminar del carrito

                new DataBase(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this, "Gracias", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

    private void loadListComida() {
        cart = new DataBase(this).getPedidos();
        adapter = new CartAdapter(cart, this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        //calcula el precio total


        try{
            int total = 0;
            for (Pedido pedido : cart)

                total+= (Integer.parseInt(pedido.getPrecio()))*(Integer.parseInt(pedido.getCantidad()));
            Locale locale = new Locale("en", "US");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
            txtTotalPrecio.setText(fmt.format(total));

        }catch (NumberFormatException ex){

        }

    }
    public  boolean onContextItemSelected(MenuItem item){
        if(item.getTitle().equals(Common.DELETE))
            deleteCart(item.getOrder());
        return true;
        
    }

    private void deleteCart(int position) {
        cart.remove(position);


        new DataBase(this).cleanCart();

        for(Pedido item:cart){
            new DataBase(this).addToCart(item);
        }
        loadListComida();
    }
}
