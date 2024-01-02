package com.example.mahaelectric;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class All_Product extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);
        getSupportActionBar().setTitle("Maha Electric: All Product");

        ArrayList<productModel> arr1 = new myDbHelper(this).getAllProduct();

        RecyclerView recyclerView1 = findViewById(R.id.allProductRecyclerView);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView1.addItemDecoration(dividerItemDecoration);
        recyclerView1.setAdapter(new all_product_adapter(arr1));


    }
}