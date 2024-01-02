package com.example.mahaelectric;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
    int currentYear = calendar.get(Calendar.YEAR);
    int currentMonth = calendar.get(Calendar.MONTH) + 1;
    int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
    String date = String.valueOf(currentDay) + "." + String.valueOf(currentMonth) +"." + String.valueOf(currentYear);
    TextView dashboard_profit;
    Float pro1;
    TextView todayDate,total_sale,monthly_profit,monthly_sale ;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Maha Electric");


        dashboard_profit = findViewById(R.id.dashboard_profit);
        pro1 = new myDbHelper(this).get_profitby_day(date);
        dashboard_profit.setText("Profit today \n৳" + String.valueOf(pro1));

        todayDate = findViewById(R.id.dateTextview);
        todayDate.setText(date);

        monthly_profit = findViewById(R.id.monthly_profit);

        monthly_profit.setText("This month profit: ৳" + String.valueOf(new myDbHelper(this).get_profitBy_month(String.valueOf(currentMonth+"."+currentYear))));
        monthly_sale = findViewById(R.id.monthly_sale);
        monthly_sale.setText("");

        total_sale  = findViewById(R.id.total_sale_textview);
        String sale_today =String.valueOf(new myDbHelper(this).get_saleby_day(date)) ;
        total_sale.setText("Today's Total sale: ৳"+ sale_today);


        FloatingActionButton fav = findViewById(R.id.fav);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,sale_Activity.class));
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigationview);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    //Toast.makeText(MainActivity.this, "home", Toast.LENGTH_SHORT).show();
                }
                if (item.getItemId() == R.id.sale) {
                    //Toast.makeText(MainActivity.this, "sale", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, Activity_sales.class));
                }
                if (item.getItemId() == R.id.product) {
                   // Toast.makeText(MainActivity.this, "Product", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, All_Product.class));
                }
                if(item.getItemId() == R.id.month){
                    //Toast.makeText(MainActivity.this, "month", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, monthly_view.class));
                }

                return true;
            }
        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        pro1 = new myDbHelper(this).get_profitby_day(date);
        dashboard_profit.setText("Profit today \n" + String.valueOf(pro1));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.exit) {
            finish();
        }
        if (item.getItemId() == R.id.add) {
            add_action(MainActivity.this);
        }
        return super.onOptionsItemSelected(item);
    }

    private void add_action(Context context) {

        Dialog add_item_dialog = new Dialog(context);
        add_item_dialog.setContentView(R.layout.add_product_dialog);
        add_item_dialog.setTitle("title");
        EditText product_name = add_item_dialog.findViewById(R.id.product_name);
        EditText total_price = add_item_dialog.findViewById(R.id.price);
        EditText quantity = add_item_dialog.findViewById(R.id.quantity);
        EditText mrp = add_item_dialog.findViewById(R.id.mrp);
        Spinner spinner = add_item_dialog.findViewById(R.id.spinner);
        Button cancel_button = add_item_dialog.findViewById(R.id.cancel_button1);
        Button save_button = add_item_dialog.findViewById(R.id.save_product_button);

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_item_dialog.dismiss();
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p_name = product_name.getText().toString();
                String t_price = total_price.getText().toString();
                String quan = quantity.getText().toString();
                String MRP = mrp.getText().toString();
                if (p_name.trim().isEmpty() || t_price.trim().isEmpty() || quan.trim().isEmpty() || MRP.trim().isEmpty()){
                    Toast.makeText(MainActivity.this, "Please fill", Toast.LENGTH_SHORT).show();
                }
                else {
                    String s = p_name + " " + t_price + " " + quan + " " + MRP;
                    Float  buying_price = Float.parseFloat(t_price) / Float.parseFloat(quan);
                    myDbHelper myDbHelper1 = new myDbHelper(v.getContext());
                    myDbHelper1.add_product(p_name,String.valueOf(buying_price),quan,MRP,"");
                    add_item_dialog.dismiss();
                    Toast.makeText(MainActivity.this, "Procuct saved!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        add_item_dialog.setCanceledOnTouchOutside(false);
        add_item_dialog.show();
    }
}
