package com.example.pallagi.david_bead;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private EditText LogFelhasz, LogPass;
    private Button LogButton, RegButton;
    private AdatbazisSegito db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        LogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login()){
                    Toast.makeText(MainActivity.this,"Sikeres bejelentkezés",Toast.LENGTH_SHORT).show();
                    Intent logged = new Intent(MainActivity.this,Logged.class);
                    logged.putExtra("user",LogFelhasz.getText().toString());
                    startActivity(logged);
                    finish();
                }
                else{
                    Toast.makeText(MainActivity.this, "Hibás felhasználó név vagy jelszó", Toast.LENGTH_SHORT).show();
                }
            }
        });
        RegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg = new Intent(MainActivity.this,Regactivity.class);
                startActivity(reg);
                finish();
            }
        });
    }
    public void init(){
        LogButton=(Button) findViewById(R.id.LogButton);
        RegButton=(Button) findViewById(R.id.RegButton);
        LogFelhasz=(EditText) findViewById(R.id.LogFelhasz);
        LogPass=(EditText) findViewById(R.id.LogPass);
        db=new AdatbazisSegito(this);
    }
    private boolean login(){
        String felhasznalonev = LogFelhasz.getText().toString();
        String jelszo = LogPass.getText().toString();
        if (felhasznalonev.isEmpty()||jelszo.isEmpty()){
            return false;
        }
        Long eredmeny = db.login(felhasznalonev,AdatbazisSegito.md5(jelszo));
        if (eredmeny==0){
            return false;
        }
        else{
            return true;
        }
    }
}
