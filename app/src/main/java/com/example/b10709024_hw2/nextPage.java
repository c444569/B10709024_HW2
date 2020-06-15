package com.example.b10709024_hw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.b10709024_hw2.data.WaitlistContract;
import com.example.b10709024_hw2.data.WaitlistDbHelper;

public class nextPage extends AppCompatActivity {

    Button btnOk,btnCancel;
    EditText edName,edNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_page);

        btnCancel = findViewById(R.id.btnCancel);
        btnOk = findViewById(R.id.btnOK);
        edName = findViewById(R.id.ediName);
        edNumber = findViewById(R.id.ediNum);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final WaitlistDbHelper h = new WaitlistDbHelper(this);
        final SQLiteDatabase db = h.getWritableDatabase();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put(WaitlistContract.WaitlistEntry.COLUMN_NAME,edName.getText().toString());
                cv.put(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE,Integer.parseInt(edNumber.getText().toString()));
                db.insert(WaitlistContract.WaitlistEntry.TABLE_NAME,null,cv);
                h.printTable();
                setResult(101);
                finish();
            }
        });
    }

}