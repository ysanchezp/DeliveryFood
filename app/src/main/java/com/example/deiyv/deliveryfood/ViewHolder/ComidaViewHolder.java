package com.example.deiyv.deliveryfood.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deiyv.deliveryfood.Interface.ItemClickListener;
import com.example.deiyv.deliveryfood.R;

public class ComidaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView comida_name;
    public ImageView comida_image;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ComidaViewHolder(View itemView) {
        super(itemView);

        comida_name=(TextView)itemView.findViewById(R.id.comida_name);
        comida_image=(ImageView)itemView.findViewById(R.id.comida_image);

        itemView.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
