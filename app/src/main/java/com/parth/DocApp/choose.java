package com.onlyforadventure.DocApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Locale;

public class choose extends AppCompatActivity {

    EditText location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        location=findViewById(R.id.location);

        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),locDoctor.class);
                intent.putExtra("loc",location.getText().toString().trim().toLowerCase(Locale.ROOT));
                startActivity(intent);
            }
        });
    }
}