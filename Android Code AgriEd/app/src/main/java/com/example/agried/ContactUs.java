package com.example.agried;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ContactUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EditText et1, et2, et3, et4;
        Button b1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);
        et1 = (EditText) findViewById(R.id.first_name);
        et2 = (EditText) findViewById(R.id.last_name);
        et3 = (EditText) findViewById(R.id.email);
        et4 = (EditText) findViewById(R.id.msg);
        b1 = (Button) findViewById(R.id.submit_contact);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = et1.getText().toString() + et2.getText().toString() + et3.getText().toString() + et4.getText().toString();
                if (!et1.getText().toString().equals("") && !et2.getText().toString().equals("") && !et3.getText().toString().equals("") && !et4.getText().toString().equals("")) {

                    Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"aanalthakkar.2211@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "Contact Us from AgriEd");
                email.putExtra(Intent.EXTRA_TEXT, message);

                //need this to prompts email client only
                email.setType("message/rfc822");
                    startActivity(Intent.createChooser(email, "Choose an Email client :"));
                }

            }
        });




    }
}
