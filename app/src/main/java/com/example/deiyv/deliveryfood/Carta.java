package com.example.deiyv.deliveryfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deiyv.deliveryfood.Interface.ItemClickListener;
import com.example.deiyv.deliveryfood.Model.Category;
import com.example.deiyv.deliveryfood.ViewHolder.MenuViewHolder;
import com.example.deiyv.deliveryfood.common.Common;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Carta extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

        FirebaseDatabase database;
        DatabaseReference category;

        TextView txtFullName;

        RecyclerView recycler_carta;
        RecyclerView.LayoutManager layoutManager;
        FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Carta");
        setSupportActionBar(toolbar);

        //inicializamos firebase
        database=FirebaseDatabase.getInstance();
        category=database.getReference("Category");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent=new Intent(Carta.this,Cart.class);
                startActivity(cartIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //modificamos el usuario con el que ingreso

        View headerView=navigationView.getHeaderView(0);
        txtFullName = (TextView)headerView.findViewById(R.id.txtFullName);
        txtFullName.setText(Common.currentUser.getName());

        // cargamos el menu

        recycler_carta=(RecyclerView)findViewById(R.id.recycler_carta);
        recycler_carta.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_carta.setLayoutManager(layoutManager);

        if (Common.isConecctedToInternet(this))
            loadCarta();
        else
            Toast.makeText(this,"Por favor verifica tu conexion a internet",Toast.LENGTH_SHORT).show();
            return;
    }

    private void loadCarta() {
            adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.carta_item,MenuViewHolder.class,category) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {
                viewHolder.txtMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageView);

                final Category clickItem=model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                       Intent listaComida=new Intent(Carta.this,ListaComida.class);
                       listaComida.putExtra("CategoryId",adapter.getRef(position).getKey());
                       startActivity(listaComida);

                    }
                });

            }
        };
        recycler_carta.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.carta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.actualizar)
            loadCarta();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            // Handle the camera action
        } else if (id == R.id.nav_Carrito) {
            Intent cartIntent = new Intent(Carta.this,Cart.class);
            startActivity(cartIntent);

        } else if (id == R.id.nav_pedido) {
            Intent pedidoIntent = new Intent(Carta.this,EstadoPedido.class);
            startActivity(pedidoIntent);
        } else if (id == R.id.nav_salir) {
            Intent signInIntent=new Intent(Carta.this,SignIn.class);
            signInIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signInIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
