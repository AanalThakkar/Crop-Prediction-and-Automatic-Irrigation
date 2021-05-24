package com.example.agried;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileObserver;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Bug extends AppCompatActivity {
    private static final int PICK_IMAGE = 100;
    ImageView imageView;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EditText et1;
        Button b1,b2;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bug);
        et1 = (EditText) findViewById(R.id.bug);
        b1 = (Button) findViewById(R.id.attachment);
        b2 = (Button) findViewById(R.id.submit_bug);
        imageView = (ImageView)findViewById(R.id.imageView);

        b1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                 startActivityForResult(gallery, PICK_IMAGE);
             }
         });

         b2.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 imageView.setVisibility(View.INVISIBLE);
                 if(!et1.getText().toString().equals("")){
                     et1.setText("");
                     Toast.makeText(getApplicationContext(),"Bug Successfully Submitted",Toast.LENGTH_LONG).show();
                 }
             }
         });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }
}
