package com.example.pallagi.david_bead;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Logged extends AppCompatActivity {

    private Button Logout;
    private TextView Logged_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged);
        init();
        Intent i = getIntent();
        String Loggeduser=i.getExtras().getString("user");
        Logged_user.setText("Üdvözöllek "+Loggeduser);

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent(Logged.this,MainActivity.class);
                startActivity(logout);
                finish();
            }
        });
    }
    public void init(){
        Logout=(Button) findViewById(R.id.Logout);
        Logged_user=(TextView) findViewById(R.id.Logged_user);
    }
}
