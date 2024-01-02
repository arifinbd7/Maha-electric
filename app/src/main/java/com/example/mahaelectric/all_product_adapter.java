package com.example.mahaelectric;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class all_product_adapter extends RecyclerView.Adapter<all_product_adapter.allProductVHolder>{

    public all_product_adapter(ArrayList<productModel> arrayList) {
        this.arrayList = arrayList;
    }

    ArrayList<productModel> arrayList;

    @NonNull
    @Override
    public allProductVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_product_row,parent,false);
        return new allProductVHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull allProductVHolder holder, int position) {
        holder.title.setText(arrayList.get(position).title);
        holder.stock.setText("Stock: " + arrayList.get(position).stock);
        holder.price.setText("price: " + arrayList.get(position).price);
        holder.mrp.setText("MRP: " + arrayList.get(position).mrp);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class allProductVHolder extends RecyclerView .ViewHolder{
        TextView title;
        ImageButton button;
        TextView stock;
        TextView price;
        TextView mrp;


        public allProductVHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            button = itemView.findViewById(R.id.imageButton);
            stock = itemView.findViewById(R.id.stock);
            price = itemView.findViewById(R.id.price);
            mrp = itemView.findViewById(R.id.mrp);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                    popupMenu.inflate(R.menu.product_recycler_menu);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getItemId() == R.id.delete){
                                Toast.makeText(v.getContext(),String.valueOf(getAdapterPosition())  , Toast.LENGTH_SHORT).show();
                                new myDbHelper(v.getContext()).delete_product(arrayList.get(getAdapterPosition()).title);
                                arrayList.remove(getAdapterPosition());
                                notifyItemRemoved(getAdapterPosition());
                            }
                            if (item.getItemId() == R.id.edit){
                                Toast.makeText(v.getContext(), "edit", Toast.LENGTH_SHORT).show();
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });


        }
    }
}
