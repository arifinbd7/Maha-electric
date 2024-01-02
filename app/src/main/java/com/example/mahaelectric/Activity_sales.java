package com.example.mahaelectric;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class Activity_sales extends AppCompatActivity {
        TextView todays_sale, todays_profit;
        Button date_picker;
    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
    int currentYear = calendar.get(Calendar.YEAR);
    int currentMonth = calendar.get(Calendar.MONTH) + 1;
    int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
    String date = String.valueOf(currentDay) + "." + String.valueOf(currentMonth) +"." + String.valueOf(currentYear);
    String month_year = String.valueOf(currentMonth) + "." + String.valueOf(currentYear);
    ArrayList<sales_model> arrayList5;
    RecyclerView datewise_wise_profit_recyclerview;
    sales_adapter adapter_sales;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        date_picker = findViewById(R.id.date_picker_button);
        todays_sale = findViewById(R.id.todays_sale);
        todays_profit= findViewById(R.id.todays_profit);
        arrayList5= new myDbHelper(this).get_all_sales(date);
        if (arrayList5.size() > 0){
            todays_profit.setText("Total Profit: " + getProfit());
        }
        date_picker.setText(date);
        date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog(v.getContext());
            }
        });

        datewise_wise_profit_recyclerview = findViewById(R.id.date_wise_profit_sale);
        datewise_wise_profit_recyclerview.setLayoutManager(new LinearLayoutManager(this));

        adapter_sales = new sales_adapter(arrayList5);
        datewise_wise_profit_recyclerview.setAdapter(adapter_sales);
        datewise_wise_profit_recyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    private void openDatePickerDialog(Context context) {

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String selected_date = String.valueOf(dayOfMonth) + "."+ String.valueOf(month + 1)+ "." + String.valueOf(year);
                date_picker.setText(selected_date);
                arrayList5.clear();
                arrayList5= new myDbHelper(context).get_all_sales(selected_date);
                datewise_wise_profit_recyclerview.setLayoutManager(new LinearLayoutManager(context));
                adapter_sales = new sales_adapter(arrayList5);
                //adapter_sales.notifyDataSetChanged();
                datewise_wise_profit_recyclerview.setAdapter(adapter_sales);
                todays_profit.setText("Total Profit: " + getProfit());
            }
        }, currentYear, currentMonth -1, currentDay);
        datePickerDialog.show();
    }

    private String getProfit(){
        Float today_profit = (float) 0;
        for (int i = 0; i < arrayList5.size(); i++){
            Float pr = Float.valueOf(arrayList5.get(i).getProfit());
            today_profit +=pr;

        }
        return String.valueOf(today_profit);
    }
}