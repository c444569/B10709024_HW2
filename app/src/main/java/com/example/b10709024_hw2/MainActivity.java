package com.example.b10709024_hw2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent addintent = new Intent();
                addintent.setClass(MainActivity.this, nextPage.class);
                //addintent.putExtra("Count", 12);
                startActivity(addintent);
                break;
            case R.id.setting:
                Intent settingintent = new Intent();
                settingintent.setClass(MainActivity.this, settingPage.class);
                //settingintent.putExtra("Count", 12);
                startActivity(settingintent);
                break;
            default:

        }
        return true;
        //return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
