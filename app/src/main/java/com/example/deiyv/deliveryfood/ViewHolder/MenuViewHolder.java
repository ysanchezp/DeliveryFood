package com.example.deiyv.deliveryfood.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deiyv.deliveryfood.Interface.ItemClickListener;
import com.example.deiyv.deliveryfood.R;

public class MenuViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtMenuName;
    public ImageView imageView;

    private ItemClickListener itemClickListener;

    public MenuViewHolder(View itemView) {
        super(itemView);

        txtMenuName=(TextView)itemView.findViewById(R.id.carta_name);
        imageView=(ImageView)itemView.findViewById(R.id.carta_image);

        itemView.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    public void  onClick(View view){
        itemClickListener.onClick(view,getAdapterPosition(),false);

    }
}
