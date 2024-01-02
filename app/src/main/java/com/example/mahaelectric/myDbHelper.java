package com.example.mahaelectric;

import android.content.ContentValues;
import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class myDbHelper extends SQLiteOpenHelper {
    //private static final String CREATE_TABLE =
           // "CREATE TABLE add_product  (id INTEGER PRIMARY KEY AUTOINCREMENT, product_name  TEXT, retail_price TEXT, total_quantity TEXT, mrp TEXT, company_name TEXT)";
    public myDbHelper(Context context) {
        super(context, "mydb.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE product  (id INTEGER PRIMARY KEY AUTOINCREMENT, product_name  TEXT, retail_price TEXT, total_quantity TEXT, mrp TEXT, company_name TEXT)");
        db.execSQL("CREATE TABLE sale  (id INTEGER PRIMARY KEY AUTOINCREMENT, date  TEXT, month_year TEXT, total_profit TEXT, product_desc TEXT, total_sale TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS product");
        db.execSQL("DROP TABLE IF EXISTS sale");
        onCreate(db);
    }

    public void add_product(String name, String price, String Quan, String mrp, String company){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("product_name",name);
        contentValues.put("retail_price", price);
        contentValues.put("total_quantity", Quan);
        contentValues.put("mrp", mrp);
        contentValues.put("company_name",company);
        sqLiteDatabase.insert("product", null, contentValues);

    }
    public void add_sale(String date, String month, String profit, String desc, String sale){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("month_year", month);
        contentValues.put("total_profit",profit);
        contentValues.put("product_desc", desc);
        contentValues.put("total_sale", sale);
        sqLiteDatabase.insert("sale", null, contentValues);
    }
    public Float get_profitBy_month(String month){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("sale", new String[]{"total_profit"},"month_year=?", new String[]{month},null,null,null );
        Float total = 0.0F;
        while (cursor.moveToNext()){
            Float pro = Float.valueOf(cursor.getString(0));
            total += pro;
        }
        return total;
    }
    public Float get_profitby_day(String day){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("sale", new String[]{"total_profit"},"date=?", new String[]{day},null,null,null );
        Float total = 0.0F;
        while (cursor.moveToNext()){
            Float pro = Float.valueOf(cursor.getString(0));
            total += pro;
        }
        return total;
    }

    public Float get_saleby_day (String day){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("sale", new String[]{"total_sale"},"date=?", new String[]{day},null,null,null );
        Float total = 0.0F;
        while (cursor.moveToNext()){
            Float pro = Float.valueOf(cursor.getString(0));
            total += pro;
        }
        return total;
    }
    public ArrayList<productModel> getAllProduct(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("product",null,null,null,null,null,null );
        ArrayList<productModel> arrayList = new ArrayList<productModel>();
        while (cursor.moveToNext()){
            String title = cursor.getString(1);
            String stock = cursor.getString(3);
            String price = cursor.getString(2);
            String mrp = cursor.getString(4);
            arrayList.add(new productModel(title,stock,price,mrp));
        }
        cursor.close();
        return arrayList;
    }

    public  ArrayList<sales_model> get_all_sales(String date){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("sale",null,"date=?",new String[]{date},null,null,null );
        ArrayList<sales_model> arrayList = new ArrayList<sales_model>();
        while (cursor.moveToNext()){
            try {
                String date1 = cursor.getString(1);
                String  month = cursor.getString(2);
                String profit = cursor.getString(3);
                String desc = cursor.getString(4);
                String sale = cursor.getString(5);
                arrayList.add(new sales_model(date1,month,profit,desc,sale));
            } catch( Exception e){
                e.printStackTrace();
            }

        }
        return arrayList;
    }

    public  void delete_product(String title){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete("product","product_name=?" ,new String[]{title});


    }
}
