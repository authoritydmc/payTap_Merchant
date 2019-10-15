package com.vastgk.paytapmercent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ViewHolder>{
  ArrayList< ItemDetails> items;

    public ItemRecyclerAdapter(ArrayList<ItemDetails> items) {
        this.items=items;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_itemdetails,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemName.setText("Item:"+items.get(position).getItemName());
        holder.itemPrice.setText("Price:"+items.get(position).getItemPrice());
        holder.itemCode.setText("itemCode:"+items.get(position).getItemCode());
        holder.itemQuantity.setText("QTY:"+items.get(position).getItemQuantity());



    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName,itemCode,itemPrice,itemQuantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName=itemView.findViewById(R.id.cardView_ItemName);
            itemQuantity=itemView.findViewById(R.id.cardView_itemQuantity);
            itemCode=itemView.findViewById(R.id.cardView_itemCode);

            itemPrice=itemView.findViewById(R.id.cardView_itemPrice);



        }
    }
}
