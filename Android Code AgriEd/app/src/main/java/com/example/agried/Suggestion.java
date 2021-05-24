package com.example.agried;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Suggestion extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EditText et1;
        Button b1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggestion);
        et1 = (EditText) findViewById(R.id.suggestion);
        b1 = (Button) findViewById(R.id.submit_suggestion);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!et1.getText().toString().equals("")) {
                    et1.setText("");
                    Toast.makeText(getApplicationContext(), "Thank You For Your Suggestion", Toast.LENGTH_LONG).show();
                }
            }
        });
}
}
