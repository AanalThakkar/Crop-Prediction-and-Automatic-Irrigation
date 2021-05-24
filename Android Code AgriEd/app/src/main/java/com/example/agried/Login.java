package com.example.agried;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EditText et1,et2;
        Button b1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        et1=(EditText)findViewById(R.id.uid);
        et2=(EditText)findViewById(R.id.pswd);
        b1=(Button)findViewById(R.id.loginbtn);
        String username = et1.getText().toString();
        String pswd = et2.getText().toString();
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
                    Toast t = Toast.makeText(getApplicationContext(),"Invalid userid or password",Toast.LENGTH_SHORT);
                    t.show();
                    et1.setText("");
                    et2.setText("");
                }
            }
        });

    }

}
