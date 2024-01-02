package com.example.mahaelectric;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class sales_adapter extends RecyclerView.Adapter<sales_adapter.my_sales_view_holder>{
    ArrayList<sales_model> arrayList;
    public sales_adapter(ArrayList<sales_model> arrayList) {
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public my_sales_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_wise_sales_row,parent,false);
        return new my_sales_view_holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull my_sales_view_holder holder, int position) {
        holder.date.setText(arrayList.get(position).getDate());
        holder.desc.setText(arrayList.get(position).getDesc());
        holder.profit.setText("Profit: " + arrayList.get(position).getProfit());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class my_sales_view_holder extends RecyclerView.ViewHolder{
        TextView date , desc, profit;
        public my_sales_view_holder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            desc = itemView.findViewById(R.id.desc);
            profit = itemView.findViewById(R.id.profit1);
        }
    }
}
