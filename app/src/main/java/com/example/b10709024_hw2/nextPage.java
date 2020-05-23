package com.example.b10709024_hw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class nextPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_page);

        final EditText name = (EditText)findViewById(R.id.ediName);
        EditText num = (EditText)findViewById(R.id.ediNum);
        Button OK = (Button) findViewById(R.id.btnOK);
        Button Cancel = (Button) findViewById(R.id.btnCancel);


        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
