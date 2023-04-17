package com.onlyforadventure.DocApp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.onlyforadventure.DocApp.mainFragments.HomeFragment;

public class ChooseSpec extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_spec);

    }
    public void spec(View view){
        String tag=view.getTag().toString();
        Intent intent=new Intent(this, displayDoctor.class);
        intent.putExtra("tag",tag);
        startActivity(intent);
    }
}