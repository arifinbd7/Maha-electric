package com.example.mahaelectric;

import android.app.Dialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;

public class saleAdapter extends RecyclerView.Adapter<saleAdapter.saleViewHolder> implements Filterable {
    ArrayList<getCountModel> getCountArray;
    ArrayList<productModel> all_sale_product;
    SaleAdapterInterface saleAdapterInterface;
    public saleAdapter(ArrayList<productModel> sale_products, SaleAdapterInterface saleAdapterInterface) {
        this.sale_products = sale_products;
        this.all_sale_product = new ArrayList<productModel>(sale_products);
        this.saleAdapterInterface = saleAdapterInterface;
        this.getCountArray = new ArrayList<getCountModel>();
    }

    ArrayList<productModel> sale_products;

    @NonNull
    @Override
    public saleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.sale_row,parent,false);
        return new saleViewHolder(view2);
    }

    @Override
    public void onBindViewHolder(@NonNull saleViewHolder holder, int position) {
        holder.sale_title.setText(sale_products.get(position).title);
        holder.sale_stock.setText("stock: " + sale_products.get(position).stock);
        holder.sale_price.setText("price: " + sale_products.get(position).price);
        holder.sale_mrp.setText("MRP: " + sale_products.get(position).mrp);
        holder.sale_items.setText("items 0");

        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d1 = new Dialog(v.getContext());
                d1.setCanceledOnTouchOutside(false);
                d1.setContentView(R.layout.get_item_count);

                EditText e1= d1.findViewById(R.id.e1);
                TextView tv2 = d1.findViewById(R.id.title_show);
                tv2.setText(holder.sale_title.getText().toString());
                Button b1 = d1.findViewById(R.id.btn_count);
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (e1.getText().toString().isEmpty()){
                            Toast.makeText(v.getContext(), "invalid number", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            holder.sale_items.setText(e1.getText().toString());
                            int index = indexFor(holder.sale_title.getText().toString());
                            if ( index == -1){
                                getCountArray.add(new getCountModel(holder.sale_title.getText().toString(),e1.getText().toString()));
                            }

                            else {
                                getCountArray.get(index).setProductCount(e1.getText().toString());
                            }
                            Toast.makeText(v.getContext(), getCountArray.get(0).getProductCount().toString(), Toast.LENGTH_SHORT).show();

                            saleAdapterInterface.getArray(getCountArray);
                            d1.dismiss();
                        }
                    }
                });
                d1.show();
            }
        });
    }

    private int indexFor(String search) {
        for (int i = 0; i < getCountArray.size(); i++)
            if (getCountArray.get(i).getTitle()== search)
                return i;
        return -1;
    }

    @Override
    public int getItemCount() {
        return sale_products.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<productModel> filteredList = new ArrayList<productModel>();
            if (constraint.toString().isEmpty()){
                filteredList.addAll(all_sale_product);
            }
            else {
                for (productModel product : all_sale_product){
                    if (product.getTitle().toString().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(product);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            sale_products.clear();
            sale_products.addAll((Collection<? extends productModel>) results.values);
            notifyDataSetChanged();
        }
    };

    public class saleViewHolder extends RecyclerView.ViewHolder {
        TextView sale_title, sale_stock, sale_price, sale_mrp, sale_items;
        RelativeLayout rl;
        ArrayList<String> getCountArray= new ArrayList<String>();
        public saleViewHolder(@NonNull View itemView) {
            super(itemView);

            sale_title = itemView.findViewById(R.id.sale_title);
            sale_stock = itemView.findViewById(R.id.sale_stock);
            sale_price = itemView.findViewById(R.id.sale_price);
            sale_mrp = itemView.findViewById(R.id.sale_mrp);
            sale_items = itemView.findViewById(R.id.sale_items);
            rl = itemView.findViewById(R.id.rl);

        }
    }
}
