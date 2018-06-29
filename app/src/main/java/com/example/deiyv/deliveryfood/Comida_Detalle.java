package com.example.deiyv.deliveryfood;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.deiyv.deliveryfood.DataBase.DataBase;
import com.example.deiyv.deliveryfood.Model.Comida;
import com.example.deiyv.deliveryfood.Model.Pedido;
import com.example.deiyv.deliveryfood.common.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Comida_Detalle extends AppCompatActivity {

    TextView nombre_comida,precio_comida,descripcion_comida;
    ImageView image_comida;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    String foodId="";

    Comida currentComida;

    FirebaseDatabase database;
    DatabaseReference comidas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comida__detalle);

        // firebase

        database= FirebaseDatabase.getInstance();
        comidas=database.getReference("Foods");

        //iniciando vista
        numberButton=(ElegantNumberButton)findViewById(R.id.number_button);
        btnCart=(FloatingActionButton)findViewById(R.id.btnCart);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DataBase(getBaseContext()).addToCart(new Pedido(
                        foodId,
                        currentComida.getName(),
                        numberButton.getNumber(),
                        currentComida.getPrecio()


                ));

                Toast.makeText(Comida_Detalle.this,"Agregado la Carrito ",Toast.LENGTH_SHORT).show();
            }
        });

        //descripcion_comida=(TextView)findViewById(R.id.comida_descripcion);
        nombre_comida=(TextView)findViewById(R.id.comida_name);
        precio_comida=(TextView)findViewById(R.id.comida_precio);
        image_comida=(ImageView)findViewById(R.id.img_food);

        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        // obtiene el id de la comida del intent anterior

        if(getIntent()!=null){
            foodId=getIntent().getStringExtra("FoodId");

        }
        if(!foodId.isEmpty()){
            if(Common.isConecctedToInternet(getBaseContext()))
                getDetailFood(foodId);
            else
                Toast.makeText(Comida_Detalle.this,"Por favor verifica tu conexion a internet",Toast.LENGTH_SHORT).show();
                return;
        }
    }

    private void getDetailFood(String foodId) {

        comidas.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentComida=dataSnapshot.getValue(Comida.class);

                Picasso.with(getApplicationContext()).load(currentComida.getImage()).into(image_comida);

                collapsingToolbarLayout.setTitle(currentComida.getName());
                precio_comida.setText(currentComida.getPrecio());
                nombre_comida.setText(currentComida.getName());
                //descripcion_comida.setText(currentComida.getDescripcion());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
