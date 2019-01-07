package com.example.pallagi.david_bead;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

public class Regactivity extends AppCompatActivity {

    private EditText RegFelhasz,RegPass,RegPassConfirm,FullName,PhoneNum;
    private Button RegButton,Cancel;
    private AdatbazisSegito db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regactivity);
        init();


        RegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDataEntered();
                String pass=RegPass.getText().toString();
                String passcomf = RegPassConfirm.getText().toString();
                if (RegFelhasz.getError()==null && RegPass.getError()==null && RegPassConfirm.getError()==null && FullName.getError()==null && PhoneNum.getError()==null){
                    adatRogzites();
                    Intent vissza = new Intent(Regactivity.this,MainActivity.class);
                    startActivity(vissza);
                    finish();
                }
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vissza = new Intent(Regactivity.this,MainActivity.class);
                startActivity(vissza);
                finish();
            }
        });

    }
    public void init(){
        RegFelhasz=(EditText) findViewById(R.id.RegFelhasz);
        RegPass=(EditText) findViewById(R.id.RegPass);
        RegPassConfirm=(EditText) findViewById(R.id.RegPassConfirm);
        FullName=(EditText) findViewById(R.id.FullName);
        PhoneNum=(EditText) findViewById(R.id.PhoneNum);
        RegButton=(Button) findViewById(R.id.RegButton);
        Cancel=(Button) findViewById(R.id.Cancel);
        db = new AdatbazisSegito(this);
    }
    private void checkDataEntered(){
        if (isEmpty(RegFelhasz)){
            RegFelhasz.setError("Add meg a felhasználónevet");
        }
        if (isEmpty(RegPass)){
            RegPass.setError("Add meg a jelszót");
        }
        if (isEmpty(RegPassConfirm)){
            RegPassConfirm.setError("Add meg a jelszó megerösitést");
        }
        if (isEmpty(FullName)){
            FullName.setError("Add meg a teljes nevet");
        }
        if (isEmpty(PhoneNum)){
            PhoneNum.setError("Add meg a telefonszámot");
        }
    }
    private boolean isEmpty(EditText text){
        CharSequence str=text.getText().toString();
        return TextUtils.isEmpty(str);
    }
    public void adatRogzites()
    {
        String felhasznalonev = RegFelhasz.getText().toString();
        String jelszo = RegPass.getText().toString();
        String teljesnev = FullName.getText().toString();
        String telefonszam = PhoneNum.getText().toString();
        boolean eredmeny=false;
        if (db.regcheck(felhasznalonev)==0){
            eredmeny = db.adatRogzites(felhasznalonev,jelszo,teljesnev,telefonszam);
            if (eredmeny)
            {
                Toast.makeText(this, "Sikeres adatrögzítés", Toast.LENGTH_SHORT).show();
            }else
            {
                Toast.makeText(this, "Sikertelen adatrögzítés", Toast.LENGTH_SHORT).show();
            }
        }
        if(db.regcheck(felhasznalonev)!=0){
            Toast.makeText(this,"Ezzel a felhasználó névvel már regisztráltak",Toast.LENGTH_SHORT).show();
        }

    }
}
