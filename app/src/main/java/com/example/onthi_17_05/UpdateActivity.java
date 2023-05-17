package com.example.onthi_17_05;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    private EditText edtSoXe, edtQuangDuong, edtDonGia, edtKhuyenMai;
    private Button btnEdit, btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        edtSoXe = findViewById(R.id.edt_so_xe);
        edtQuangDuong = findViewById(R.id.edt_quang_duong);
        edtDonGia = findViewById(R.id.edt_don_gia);
        edtKhuyenMai = findViewById(R.id.edt_khuyen_mai);
        btnEdit = findViewById(R.id.btn_edit);
        btnBack = findViewById(R.id.btn_back);
        Bill bill =(Bill) getIntent().getSerializableExtra("bill");
        if(bill != null){
            edtSoXe.setText(bill.getNumber());
            edtQuangDuong.setText(String.valueOf(bill.getDistance()));
            edtDonGia.setText(String.valueOf(bill.getPrice()));
            edtKhuyenMai.setText(String.valueOf(bill.getPercent()));
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateActivity.super.onBackPressed();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    String soXe = edtSoXe.getText().toString().trim();
                    double quangDuong =Double.parseDouble(edtQuangDuong.getText().toString().trim());
                    double donGia = Double.parseDouble(edtDonGia.getText().toString().trim());
                    int khuyenMai = Integer.parseInt(edtKhuyenMai.getText().toString().trim());
                    Bill billUpdate = new Bill(bill.getId(), soXe, quangDuong, donGia, khuyenMai);
                    intent.putExtra("billUpdate", billUpdate);
                    setResult(78, intent);
                    UpdateActivity.super.onBackPressed();
                } catch (Exception e){
                    Toast.makeText(UpdateActivity.this, "Update Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}