package com.example.mahaelectric;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class sale_Activity extends AppCompatActivity implements SaleAdapterInterface{
    saleAdapter adapter;
    ArrayList<getCountModel> newGetcountArray = new ArrayList<getCountModel>();
    ArrayList<productModel> arr2;
    Float sumPrice = 0.0F;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);
        getSupportActionBar().setTitle("Maha Electric: select sale");
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) RecyclerView rv1 = findViewById(R.id.rv2);
        rv1.setLayoutManager(new LinearLayoutManager(this));
        arr2 = new myDbHelper(this).getAllProduct();
        adapter = new saleAdapter(arr2, this);
        rv1.setAdapter(adapter);
        rv1.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        FloatingActionButton floatingActionButton = findViewById(R.id.sale_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newGetcountArray.size()> 0){

                    Float sumMrp = 0.0F;
                    for (int j = 0; j < newGetcountArray.size(); j++){
                        int index = indexFor(newGetcountArray.get(j).getTitle());
                        Float price = Float.valueOf(arr2.get(index).getPrice()) * Float.valueOf(newGetcountArray.get(j).getProductCount());
                        Float mrp = Float.valueOf(arr2.get(index).getMrp()) * Float.valueOf(newGetcountArray.get(j).getProductCount());
                        sumMrp += mrp;
                        sumPrice += price;
                    }
                    Dialog dialog1 = new Dialog(v.getContext());
                    dialog1.setContentView(R.layout.after_sale_money_recev_layout);
                    dialog1.setCanceledOnTouchOutside(false);
                    TextView priceTextView = dialog1.findViewById(R.id.priceTextView);
                    TextView mrpTextView = dialog1.findViewById(R.id.mrpTextView);
                    TextView item_desc = dialog1.findViewById(R.id.item_desc);
                    item_desc.setText(get_desc(newGetcountArray));
                    EditText total_received_money = dialog1.findViewById(R.id.total_received_money);
                    Button save_and_close = dialog1.findViewById(R.id.saveAndClose);
                    Button save_and_print = dialog1.findViewById(R.id.saveAndPrint);
                    Float finalSumPrice = sumPrice;
                    save_and_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (total_received_money.getText().toString().isEmpty()){
                                Toast.makeText(sale_Activity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                                int currentYear = calendar.get(Calendar.YEAR);
                                int currentMonth = calendar.get(Calendar.MONTH) + 1;
                                int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
                                String date = String.valueOf(currentDay) + "." + String.valueOf(currentMonth) +"." + String.valueOf(currentYear);
                                String month_year = String.valueOf(currentMonth) + "." + String.valueOf(currentYear);
                                Float profit =  Float.valueOf(total_received_money.getText().toString()) - Float.valueOf(sumPrice);
                                //Toast.makeText(sale_Activity.this, String.valueOf(profit), Toast.LENGTH_SHORT).show();
                               new myDbHelper(v.getContext()).add_sale(date,month_year,String.valueOf(profit),get_desc(newGetcountArray), String.valueOf(sumPrice));
                                adapter.getCountArray.clear();
                                Toast.makeText(sale_Activity.this, "profit added", Toast.LENGTH_SHORT).show();
                                dialog1.dismiss();
                            }

                        }
                    });
                    save_and_print.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog1.dismiss();
                        }
                    });
                    priceTextView.setText("Total Buying Price: " + String.valueOf(sumPrice));
                    mrpTextView.setText("Total MRP: " + String.valueOf(sumMrp));
                    dialog1.setTitle("Title");
                    dialog1.show();

                }
                else {
                    Toast.makeText(v.getContext(), "Please select atleast one item to continue :-(", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private int indexFor(String search) {
        for (int i = 0; i < arr2.size(); i++)
            if (arr2.get(i).getTitle()== search)
                return i;
        return -1;
    }

    private String get_desc(ArrayList<getCountModel> arr){
        String str = "";
        for (int i = 0 ; i < arr.size(); i++){
            String str1 = arr.get(i).getTitle() + " x" + arr.get(i).getProductCount() + ", \n";
            str += str1;
        }
        return str;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.sale_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void getArray(ArrayList<getCountModel> arrayList) {
        newGetcountArray = arrayList;
    }
}