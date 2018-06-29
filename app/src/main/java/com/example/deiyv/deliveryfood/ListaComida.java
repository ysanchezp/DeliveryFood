package com.example.deiyv.deliveryfood;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.deiyv.deliveryfood.Interface.ItemClickListener;
import com.example.deiyv.deliveryfood.Model.Comida;
import com.example.deiyv.deliveryfood.ViewHolder.ComidaViewHolder;
import com.example.deiyv.deliveryfood.common.Common;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListaComida extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference listaComida;

    String categoryId="";

    FirebaseRecyclerAdapter<Comida,ComidaViewHolder> adapter;

    // funcionalidad del buscador

    FirebaseRecyclerAdapter<Comida,ComidaViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_comida);

        database=FirebaseDatabase.getInstance();
        listaComida=database.getReference("Foods");

        recyclerView=(RecyclerView)findViewById(R.id.recycler_comida);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //obtenemos el intent aqui

        if(getIntent()!=null){
            categoryId=getIntent().getStringExtra("CategoryId");

        }
        if(!categoryId.isEmpty() && categoryId!=null){
            if(Common.isConecctedToInternet(getBaseContext()))
                loadListaComida(categoryId);
            else
                Toast.makeText(ListaComida.this,"Por favor verifica tu conexion a internet",Toast.LENGTH_SHORT).show();
                return;
        }

        //search

        materialSearchBar=(MaterialSearchBar)findViewById(R.id.searchBar);
        materialSearchBar.setHint("Ingresa tu comida");
        //materialSearchBar.setSpeechMode(false);
        loadSuggest();
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<String> suggest = new ArrayList<>();
                for (String search:suggestList){
                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase())){
                        suggest.add(search);
                    }
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

                if (!enabled)
                    recyclerView.setAdapter(adapter);

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {


                startSearch(text);

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
    }

    private void startSearch(CharSequence text) {
        searchAdapter=new FirebaseRecyclerAdapter<Comida, ComidaViewHolder>(
                Comida.class,
                R.layout.comida_item,
                ComidaViewHolder.class,
                listaComida.orderByChild("Name").equalTo(text.toString())
        ) {
            @Override
            protected void populateViewHolder(ComidaViewHolder viewHolder, Comida model, int position) {
                viewHolder.comida_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.comida_image);

                final Comida local=model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Toast.makeText(ListaComida.this,""+local.getName(),Toast.LENGTH_SHORT).show();

                        // iniciamos el nuevo activity
                        Intent ComidaDetalle=new Intent(ListaComida.this,Comida_Detalle.class);
                        ComidaDetalle.putExtra("FoodId",adapter.getRef(position).getKey());  // envia el id de la comida al nuevo activity
                        startActivity(ComidaDetalle);
                    }
                });
            }
        };
                recyclerView.setAdapter(searchAdapter); //establecer el adaptador para recyclerview es el resultado de la búsqueda
    }

    private void loadSuggest() {
        listaComida.orderByChild("MenuId").equalTo(categoryId)
        .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Comida item=postSnapshot.getValue(Comida.class);
                    suggestList.add(item.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadListaComida(String categoryId) {

        adapter =new FirebaseRecyclerAdapter<Comida, ComidaViewHolder>(Comida.class,R.layout.comida_item,ComidaViewHolder.class,listaComida.orderByChild("MenuId").equalTo(categoryId)) {
            @Override
            protected void populateViewHolder(ComidaViewHolder viewHolder, Comida model, int position) {
                viewHolder.comida_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.comida_image);

                final Comida local=model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Toast.makeText(ListaComida.this,""+local.getName(),Toast.LENGTH_SHORT).show();

                        // iniciamos el nuevo activity
                        Intent ComidaDetalle=new Intent(ListaComida.this,Comida_Detalle.class);
                        ComidaDetalle.putExtra("FoodId",adapter.getRef(position).getKey());  // envia el id de la comida al nuevo activity
                        startActivity(ComidaDetalle);
                    }
                });
            }
        };
        //Establecemos adapatdor
        //Log.d("TAG","",+adapter.getItemCount());
        recyclerView.setAdapter(adapter);
    }
}
