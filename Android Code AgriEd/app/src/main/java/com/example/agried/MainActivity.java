package com.example.agried;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button b1;
        EditText et1,et2;
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.activity_main);
        et1=(EditText)findViewById(R.id.username);
        et2=(EditText)findViewById(R.id.pswd);
        b1=(Button)findViewById(R.id.login);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et1.getText().toString().equals("admin") && et2.getText().toString().equals("admin"))
                {
                    Intent i = new Intent(getApplicationContext(), Choice.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Invalid userid or password",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    public void hello(View view) {
        Intent i = new Intent(getApplicationContext(), SignUp.class);
        startActivity(i);

    }
}