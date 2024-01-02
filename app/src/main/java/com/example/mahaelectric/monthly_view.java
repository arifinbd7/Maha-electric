package com.example.mahaelectric;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class monthly_view extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_view);
        getSupportActionBar().setTitle("Monthly_view");
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Spinner spinner = findViewById(R.id.monthly_view_spinner);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView monthly_textview = findViewById(R.id.monthly_view);
        ArrayList<String> arraylist7 = new ArrayList<String>();
        arraylist7.add("January");
        arraylist7.add("February");
        arraylist7.add("March");
        arraylist7.add("April");
        arraylist7.add("May");
        arraylist7.add("June");
        arraylist7.add("July");
        arraylist7.add("August");
        arraylist7.add("September");
        arraylist7.add("October");
        arraylist7.add("November");
        arraylist7.add("December");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arraylist7);
        adapter1.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String s = parent.getItemAtPosition(position).toString();
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                int currentYear = calendar.get(Calendar.YEAR);
                String month_year = String.valueOf(position+1) + "." + String.valueOf(currentYear);
             Float profit = new myDbHelper(parent.getContext()).get_profitBy_month(month_year);

             monthly_textview.setText("Total Profit: " +String.valueOf(profit));
                //Toast.makeText(monthly_view.this, String.valueOf(profit), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                monthly_textview.setText("Select month first");
            }
        });
    }
}