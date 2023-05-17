package com.example.onthi_17_05;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView lvBill;
    private List<Bill> listBill;
    private MyDBHelper myDB;
    private BillAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new MyDBHelper(this);
        init();
    }
    private void init() {
        lvBill = findViewById(R.id.lv_bill);
        myDB.onUpgrade(myDB.getWritableDatabase(), 1,1);
        insertData();
        listBill = getDataFromDB();
        adapter = new BillAdapter();
        adapter.setData(listBill);
        lvBill.setAdapter(adapter);
    }

    private void insertData() {
        myDB.insertDB("08-763A", 9.2, 1000, 13);
        myDB.insertDB("08-763A", 9.2, 1000, 13);
        myDB.insertDB("08-763A", 9.2, 1000, 13);
        myDB.insertDB("08-763A", 9.2, 1000, 13);
        myDB.insertDB("201200205", 9.2, 1000, 13);
        myDB.insertDB("08-763A", 9.2, 1000, 13);
    }
    @SuppressLint("Range")
    private List<Bill> getDataFromDB(){
        List<Bill> list = new ArrayList<>();
        Cursor cursor = myDB.displayAll();
        String getMa = MyDBHelper.getMa();
        String getSoXe = MyDBHelper.getSoXe();
        String getQuangDuong = MyDBHelper.getQuangDuong();
        String getDonGia = MyDBHelper.getDonGia();
        String getPhanTram = MyDBHelper.getPhanTram();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int ma = cursor.getInt((cursor.getColumnIndex(getMa)));
            String soXe = cursor.getString((cursor.getColumnIndex(getSoXe)));
            double quangDuong = cursor.getDouble((cursor.getColumnIndex(getQuangDuong)));
            double donGia = cursor.getDouble((cursor.getColumnIndex(getDonGia)));
            int phanTram = cursor.getInt((cursor.getColumnIndex(getPhanTram)));
            list.add(new Bill(ma, soXe, quangDuong, donGia, phanTram));
        }
        return list;
    }
    @Override
    protected void onStart() {
        super.onStart();
        myDB.openDB();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myDB.closeDB();
    }
}