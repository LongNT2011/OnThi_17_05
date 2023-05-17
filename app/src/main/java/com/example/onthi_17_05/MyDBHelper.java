package com.example.onthi_17_05;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {
    private static final String DBName = "SqliteDb_20_11_2002.db";
    private static final int VERSION = 1;
    private static final String TABLE_NAME = "Taxi_MaDe";
    private static final String MA = "ma";
    private static final String SOXE = "soxe";
    private static final String QUANGDUONG = "quangduong";
    private static final String DONGIA = "dongia";
    private static final String PHANTRAM = "phantram";
    private SQLiteDatabase myDB;

    public static String getMa() {
        return MA;
    }

    public static String getSoXe() {
        return SOXE;
    }

    public static String getQuangDuong() {
        return QUANGDUONG;
    }

    public static String getDonGia() {
        return DONGIA;
    }

    public static String getPhanTram() {
        return PHANTRAM;
    }

    public MyDBHelper(@Nullable Context context) {
        super(context, DBName, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryTable = "CREATE TABLE " + TABLE_NAME + "(" +
                MA + " INTEGER PRIMARY KEY autoincrement, " +
                SOXE + " TEXT NOT NULL, " +
                QUANGDUONG + " DOUBLE NOT NULL, " +
                DONGIA + " DOUBLE NOT NULL, " +
                PHANTRAM + " INTEGER NOT NULL " + ")";
        db.execSQL(queryTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropDB = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(dropDB);
        onCreate(db);
    }

    public void openDB() {
        myDB = getWritableDatabase();
    }

    public void closeDB() {
        if (myDB != null && myDB.isOpen()) {
            myDB.close();
        }
    }

    public long insertDB(String soXe, double quangDuong, double donGia, int phanTram) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SOXE, soXe);
        values.put(QUANGDUONG, quangDuong);
        values.put(DONGIA, donGia);
        values.put(PHANTRAM, phanTram);
        return db.insert(TABLE_NAME, null, values);
    }

    public long updateDB(int ma, String soXe, double quangDuong, double donGia, int phanTram) {
        ContentValues values = new ContentValues();
        values.put(MA, ma);
        values.put(SOXE, soXe);
        values.put(QUANGDUONG, quangDuong);
        values.put(DONGIA, donGia);
        values.put(PHANTRAM, phanTram);
        String where = MA + " = " + ma;
        return myDB.update(TABLE_NAME, values, where, null);
    }

    public long deleteDB(int ma) {
        String where = MA + " = " + ma;
        return myDB.delete(TABLE_NAME, where, null);
    }

    public Cursor displayAll() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }

    public Cursor searchByPrice(double Price) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "Select * from " + TABLE_NAME + " WHERE ";
        query += DONGIA + " * " + QUANGDUONG + " * (" + "100 - " + PHANTRAM + " ) / 100 > " + Price;
        return sqLiteDatabase.rawQuery(query, null);
    }
}
