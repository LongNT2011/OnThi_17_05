package com.example.onthi_17_05;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class BillAdapter extends BaseAdapter {
    private List<Bill> mListBill;

    public void setData(List<Bill> list){
        mListBill = list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        if(mListBill != null){
            return mListBill.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return mListBill.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mListBill.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewStudent;
        if (convertView == null) {
            viewStudent = View.inflate(parent.getContext(), R.layout.item_bill, null);
        } else {
            viewStudent = convertView;
        }

        Bill student = (Bill) getItem(position);
        TextView txtSoXe = viewStudent.findViewById(R.id.txt_so_xe);
        TextView txtQuangDuong = viewStudent.findViewById(R.id.txt_quang_duong);
        TextView txtDonGia = viewStudent.findViewById(R.id.txt_don_gia);
        double tong = student.getPrice() * student.getDistance() * (100 - student.getPercent()) / 100;
        txtSoXe.setText(student.getNumber());
        txtQuangDuong.setText("Quãng đường: " + student.getDistance());
        txtDonGia.setText(String.valueOf(tong));
        return viewStudent;
    }
}
