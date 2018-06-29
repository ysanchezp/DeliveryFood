package com.example.deiyv.deliveryfood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.deiyv.deliveryfood.Model.Request;
import com.example.deiyv.deliveryfood.ViewHolder.OrderViewHolder;
import com.example.deiyv.deliveryfood.common.Common;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EstadoPedido extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request,OrderViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference requests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_pedido);

        // Firebase

        database=FirebaseDatabase.getInstance();
        requests=database.getReference("Request");

        recyclerView =(RecyclerView)findViewById(R.id.listPedidos);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadPedidos(Common.currentUser.getTelefono());
    }

    private void loadPedidos(String telefono) {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.pedido_layout,
                OrderViewHolder.class,
                requests.orderByChild("telefono").equalTo(telefono)
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {
                viewHolder.txtPedidoId.setText(adapter.getRef(position).getKey());
                viewHolder.txtPedidoEstado.setText(convertCodeToStatus(model.getStatus()));
                viewHolder.txtPedidoDireccion.setText(model.getDireccion());
                viewHolder.txtPedidoPhone.setText(model.getTelefono());
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private String convertCodeToStatus(String status) {

        if(status.equals("0"))
            return  "Entregado";

        else if (status.equals("1"))
            return "En camino";

        else
            return  "Enviado";

    }
}
