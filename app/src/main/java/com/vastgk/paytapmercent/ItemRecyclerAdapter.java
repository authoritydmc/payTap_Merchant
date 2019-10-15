package com.vastgk.paytapmercent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
       final ItemDetails i=items.get(position);
        holder.itemName.setText(String.format("Item:%25s",i.getItemName()));
        holder.itemPrice.setText("Price:\u20B9"+i.getItemPrice()+"x"+i.getItemQuantity()+"=\u20B9"+String.format("%.2f",i.getItemQuantity()*Float.valueOf(i.getItemPrice())));
        holder.itmCode.setText(String.format("ItemCode:%16s",i.getItemCode()));
        holder.itemQuantity.setText(" QTY:"+items.get(position).getItemQuantity());
       holder.ContextBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               PopupMenu menu=new PopupMenu(mcontext,holder.ContextBtn);
               menu.inflate(R.menu.context_menu_cardview);
               menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                   @Override
                   public boolean onMenuItemClick(MenuItem item) {
                       switch (item.getItemId())
                       {
                           case R.id.cardview_menu_delete:
                               Toast.makeText(mcontext, "Deleted "+i.getItemCode(), Toast.LENGTH_SHORT).show();
                               ((MainActivity)mcontext).DeleteItem(i.getItemCode());
                               break;
                           case R.id.cardview_menu_change_qty:
                               final Dialog dialog=new Dialog(mcontext);
                               dialog.setTitle("Item "+i.getItemCode());
                               dialog.setContentView(R.layout.dialogue_cardview);
                               final EditText editTextQty=dialog.findViewById(R.id.dialog_qty_editText);
                               Button btn=dialog.findViewById(R.id.dialog_btn);
                               btn.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       int qty=Integer.parseInt(editTextQty.getText().toString());
                                       if (qty<0)
                                       {editTextQty.setError("Quantity Can't be Negative");
                                           Toast.makeText(mcontext, "Qty<0 error", Toast.LENGTH_SHORT).show();
                                           return;
                                       }
                                       ((MainActivity)mcontext).ChangeQty(i.getItemCode(),qty);
                                       dialog.dismiss();
                                   }
                               });
                               dialog.setCancelable(true);
                               dialog.setCanceledOnTouchOutside(true);
                               dialog.show();
                               dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);



                               break;

                       }
                       return  true;
                   }
               });
               menu.show();
           }
       });









    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName, itemPrice, itemQuantity, itmCode;
        private CardView CardView;
        private Button ContextBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.cardView_ItemName);
            itemQuantity = itemView.findViewById(R.id.cardView_itemQuantity);
            itmCode = itemView.findViewById(R.id.cardview_itmcode);
            itemPrice = itemView.findViewById(R.id.cardView_itemPrice);
            CardView = itemView.findViewById(R.id.cardView_root);
            ContextBtn=itemView.findViewById(R.id.cardView_buttonOptions);


        }


    }


}


