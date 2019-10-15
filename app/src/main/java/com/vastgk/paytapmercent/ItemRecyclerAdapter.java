package com.vastgk.paytapmercent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ViewHolder>{
  ArrayList< ItemDetails> items;
  private Context mcontext;

    public ItemRecyclerAdapter(Context context,ArrayList<ItemDetails> items) {
        this.items=items;
        mcontext=context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_itemdetails,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       final ItemDetails i=items.get(position);
        holder.itemName.setText("Item:"+i.getItemName());
        holder.itemPrice.setText("Price:"+i.getItemPrice()+"x"+i.getItemQuantity()+"="+String.format("%.2f",i.getItemQuantity()*Float.valueOf(i.getItemPrice())));
        holder.itmCode.setText("itemCode:"+i.getItemCode());
        holder.itemQuantity.setText("QTY:"+items.get(position).getItemQuantity());
        holder.CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(mcontext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialogue_cardview);
                dialog.setCancelable(true);
                final EditText editQty=dialog.findViewById(R.id.dialog_qty_editText);
                final TextView del=dialog.findViewById(R.id.dialog_delete);
                del.setText("Delete "+i.getItemCode());
                //editQty.setText(i.getItemQuantity());
                del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mcontext, "Deleted"+i.getItemCode(), Toast.LENGTH_SHORT).show();
                        ((MainActivity)mcontext).DeleteItem(i.getItemCode());
dialog.dismiss();

                    }
                });
                editQty.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
/// get the QTY here
                        String qty=editQty.getText().toString();
                        ((MainActivity)mcontext).ChangeQty(i.getItemCode(),Integer.parseInt(qty));
                        dialog.dismiss();
                    }
                });
                dialog.show();
                //dialogue end


            }
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName,itemPrice,itemQuantity,itmCode;
        private CardView CardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName=itemView.findViewById(R.id.cardView_ItemName);
            itemQuantity=itemView.findViewById(R.id.cardView_itemQuantity);
            itmCode=itemView.findViewById(R.id.cardview_itmcode);
            itemPrice=itemView.findViewById(R.id.cardView_itemPrice);
            CardView=itemView.findViewById(R.id.cardView_root);


        }
    }
}
