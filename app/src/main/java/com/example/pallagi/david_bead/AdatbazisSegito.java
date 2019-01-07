package com.example.pallagi.david_bead;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AdatbazisSegito extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "Felhasznalok.db";    //adatbázis file név
    public static final String TABLE_NAME = "Felhasznalok_tabla";    //tábla neve

    public static final String COL_1 = "ID";            //első oszlop neve
    public static final String COL_2 = "FELHASZNALONEV";    //második oszlop neve
    public static final String COL_3 = "JELSZO";   //harmadik oszlop neve
    public static final String COL_4 = "TELJESNEV";          //negyedik oszlop neve
    public static final String COL_5 = "TELEFONSZAM";

    public AdatbazisSegito(Context context)
    {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FELHASZNALONEV TEXT, JELSZO TEXT,TELJESNEV TEXT,TELEFONSZAM INTAGER)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }


    public boolean adatRogzites(String felhasznalonev, String jelszo, String teljesnev, String telefonszam) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, felhasznalonev);
        contentValues.put(COL_3, md5(jelszo));
        contentValues.put(COL_4, teljesnev);
        contentValues.put(COL_5, telefonszam);

        long eredmeny = db.insert(TABLE_NAME, null, contentValues);

        if (eredmeny == -1)
        {
            return false;           //sikertelen adatfelvétel
        }else
            return true;            //sikeres adatfelvétel
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        // Create MD5 Hash
        MessageDigest digest = null;
        try {
            digest = MessageDigest
                    .getInstance(MD5);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        digest.update(s.getBytes());
        byte messageDigest[] = digest.digest();

        // Create Hex String
        StringBuilder hexString = new StringBuilder();
        for (byte aMessageDigest : messageDigest) {
            String h = Integer.toHexString(0xFF & aMessageDigest);
            while (h.length() < 2)
                h = "0" + h;
            hexString.append(h);
        }
        return hexString.toString();
    }

    public Cursor adatLekerdezes()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor eredmeny = db.rawQuery("Select * from " + TABLE_NAME,null);
        return eredmeny;
    }
    public Long login(String felhasznalonev, String jelszo){
        SQLiteDatabase db = this.getReadableDatabase();
        Long count= DatabaseUtils.queryNumEntries(db,TABLE_NAME,"FELHASZNALONEV=? AND JELSZO=?",new String[]{felhasznalonev,jelszo});
        return count;
    }
    public Long regcheck(String felhasznalonev){
        SQLiteDatabase db = this.getReadableDatabase();
        Long count= DatabaseUtils.queryNumEntries(db,TABLE_NAME,"FELHASZNALONEV=?",new String[]{felhasznalonev});
        return count;
    }
}
