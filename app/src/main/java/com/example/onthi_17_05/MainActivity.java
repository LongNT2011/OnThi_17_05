package com.example.onthi_17_05;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView lvBill;
    private List<Bill> listBill;
    private MyDBHelper myDB;
    private BillAdapter adapter;
    private EditText edtSearch;
    private FloatingActionButton btnAdd;

    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 78) {
                        Intent data = result.getData();
                        if (data != null) {
                            Bill bill = (Bill) data.getSerializableExtra("billUpdate");
                            long upDate = myDB.updateDB(bill.getId(), bill.getNumber(), bill.getDistance(), bill.getPrice(), bill.getPercent());
                            if (upDate == -1) {
                                Toast.makeText(MainActivity.this, "Update error!", Toast.LENGTH_SHORT).show();
                            } else {
                                getDataFromDB();
                                Toast.makeText(MainActivity.this, "Update success!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else if(result.getResultCode() == 79){
                        Intent data = result.getData();
                        if (data != null) {
                            Bill bill = (Bill) data.getSerializableExtra("billAdd");
                            long insert = myDB.insertDB(bill.getNumber(), bill.getDistance(), bill.getPrice(), bill.getPercent());
                            if (insert == -1) {
                                Toast.makeText(MainActivity.this, "Add error!", Toast.LENGTH_SHORT).show();
                            } else {
                                getDataFromDB();
                                Toast.makeText(MainActivity.this, "Add success!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new MyDBHelper(this);
        init();
        registerForContextMenu(lvBill);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("Range")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    double price = Double.parseDouble(edtSearch.getText().toString().trim());
                    Cursor cursor = myDB.searchByPrice(price);
                    if (cursor != null) {
                        List<Bill> bills = new ArrayList<>();
                        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                            String getMa = MyDBHelper.getMa();
                            String getSoXe = MyDBHelper.getSoXe();
                            String getQuangDuong = MyDBHelper.getQuangDuong();
                            String getDonGia = MyDBHelper.getDonGia();
                            String getPhanTram = MyDBHelper.getPhanTram();
                            int ma = cursor.getInt((cursor.getColumnIndex(getMa)));
                            String soXe = cursor.getString((cursor.getColumnIndex(getSoXe)));
                            double quangDuong = cursor.getDouble((cursor.getColumnIndex(getQuangDuong)));
                            double donGia = cursor.getDouble((cursor.getColumnIndex(getDonGia)));
                            int phanTram = cursor.getInt((cursor.getColumnIndex(getPhanTram)));
                            bills.add(new Bill(ma, soXe, quangDuong, donGia, phanTram));
                            listBill = bills;
                            adapter.setData(listBill);
                        }
                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                resultLauncher.launch(intent);
            }
        });
    }

    private void init() {
        edtSearch = findViewById(R.id.edt_search);
        btnAdd = findViewById(R.id.btn_add);
        lvBill = findViewById(R.id.lv_bill);
        myDB.onUpgrade(myDB.getWritableDatabase(), 1, 1);
        insertData();
        adapter = new BillAdapter();
        getDataFromDB();
        lvBill.setAdapter(adapter);
    }

    private void insertData() {
        myDB.insertDB("A8-763A", 9.2, 2000, 3);
        myDB.insertDB("G8-763A", 9.2, 1000, 33);
        myDB.insertDB("H8-763A", 9.2, 3000, 13);
        myDB.insertDB("E8-763A", 9.2, 6000, 23);
        myDB.insertDB("201200205", 9.2, 61000, 43);
        myDB.insertDB("C8-763A", 9.2, 1040, 34);
    }

    @SuppressLint("Range")
    private void getDataFromDB() {
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
        listBill = list;
        adapter.setData(listBill);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.lv_bill) {
            menu.add(Menu.NONE, 1, Menu.NONE, "Sửa");
            menu.add(Menu.NONE, 2, Menu.NONE, "Xóa");
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;
        switch (item.getItemId()) {
            case 1:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                updateItem(info.position);
                return true;
            case 2:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                deleteItem(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void updateItem(int position) {
        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        Bill bill = listBill.get(position);
        intent.putExtra("bill", bill);
        resultLauncher.launch(intent);
    }

    private void deleteItem(int position) {
        int id = listBill.get(position).getId();
        long upDate = myDB.deleteDB(id);
        if (upDate == -1) {
            Toast.makeText(MainActivity.this, "Delete error!", Toast.LENGTH_SHORT).show();
        } else {
            getDataFromDB();
            Toast.makeText(MainActivity.this, "Delete success!", Toast.LENGTH_SHORT).show();
        }
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