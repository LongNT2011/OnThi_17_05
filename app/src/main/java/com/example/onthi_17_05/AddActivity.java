package com.example.onthi_17_05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    private EditText edtSoXe, edtQuangDuong, edtDonGia, edtKhuyenMai;
    private Button btnAdd, btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        edtSoXe = findViewById(R.id.edt_so_xe);
        edtQuangDuong = findViewById(R.id.edt_quang_duong);
        edtDonGia = findViewById(R.id.edt_don_gia);
        edtKhuyenMai = findViewById(R.id.edt_khuyen_mai);
        btnAdd = findViewById(R.id.btn_add);
        btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddActivity.super.onBackPressed();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    String soXe = edtSoXe.getText().toString().trim();
                    double quangDuong =Double.parseDouble(edtQuangDuong.getText().toString().trim());
                    double donGia = Double.parseDouble(edtDonGia.getText().toString().trim());
                    int khuyenMai = Integer.parseInt(edtKhuyenMai.getText().toString().trim());
                    Bill billAdd = new Bill(-1,soXe, quangDuong, donGia, khuyenMai);
                    intent.putExtra("billAdd", billAdd);
                    setResult(79, intent);
                    AddActivity.super.onBackPressed();
                } catch (Exception e){
                    Toast.makeText(AddActivity.this, "Add Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}