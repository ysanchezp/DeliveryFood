package com.example.deiyv.deliveryfood.ViewHolder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.deiyv.deliveryfood.Interface.ItemClickListener;
import com.example.deiyv.deliveryfood.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtPedidoId,txtPedidoEstado,txtPedidoPhone,txtPedidoDireccion;

    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView) {
        super(itemView);

        txtPedidoDireccion=(TextView)itemView.findViewById(R.id.pedido_direccion);
        txtPedidoEstado=(TextView)itemView.findViewById(R.id.pedido_estado);
        txtPedidoId=(TextView)itemView.findViewById(R.id.pedido_id);
        txtPedidoPhone=(TextView)itemView.findViewById(R.id.pedido_phone);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v, getAdapterPosition(),false);


    }
}
