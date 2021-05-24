package com.example.agried;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button b1;
        EditText t1,t2,t3,t4;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        b1=(Button)findViewById(R.id.submit);
        t1=(EditText)findViewById(R.id.uname);
        t2=(EditText)findViewById(R.id.psd);
        t4=(EditText)findViewById(R.id.address);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(t1.getText().toString().equals("") || t2.getText().toString().equals("") || t4.getText().toString().equals("")){
                    boolean b1=t1.getText()!=null;
                    Log.v("aanal", String.valueOf(b1));
                    Toast.makeText(getApplicationContext(), "Complete all the fields", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Signed Up successfully", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);

                }
            }
        });
    }
}
