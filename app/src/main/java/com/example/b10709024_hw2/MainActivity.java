package com.example.b10709024_hw2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;


import com.example.b10709024_hw2.data.WaitlistContract;
import com.example.b10709024_hw2.data.WaitlistDbHelper;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    GuestListAdapter mAdapter;
    static SQLiteDatabase mDb;
    static WaitlistDbHelper dbHelper;
    static RecyclerView waitlistRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        waitlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_guests_list_view);
        waitlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new WaitlistDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

        ShapeDrawable s = new ShapeDrawable();
        s.setShape(new OvalShape());

        mAdapter = new GuestListAdapter(this,getAllGuests(),s);
        waitlistRecyclerView.setAdapter(mAdapter);

        final Context context = this;
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final long id = (long)viewHolder.itemView.getTag();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("警告")
                        .setMessage("確認刪除 ?")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removeGuest(id);
                                mAdapter.reset(getAllGuests());
                                System.out.println("sout : swiped");
                                dbHelper.printTable();
                            }
                        });
                builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);

                AlertDialog a = builder.create();
                a.show();
            }
        }).attachToRecyclerView(waitlistRecyclerView);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        setColor(sp.getString("color","pink"));
        sp.registerOnSharedPreferenceChangeListener(this);
    }
    public Cursor getAllGuests() {
        return mDb.query(
                WaitlistContract.WaitlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                WaitlistContract.WaitlistEntry.COLUMN_TIMESTAMP
        );
    }
    private void removeGuest(long id){
        mDb.execSQL(String.format("delete from %s where %s = %s",WaitlistContract.WaitlistEntry.TABLE_NAME,
                WaitlistContract.WaitlistEntry._ID,
                id+""));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add){
            Intent intent = new Intent(this, nextPage.class);
            startActivityForResult(intent,100);
            return true;
        }
        if(item.getItemId() == R.id.setting){
            Intent intent = new Intent(this, settingPage.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onActivityResult(int request,int response,Intent data) {
        super.onActivityResult(request, response, data);
        if(request==100&&response==101) {
            mAdapter.reset(getAllGuests());
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setColor(String color){
        switch (color){
            case "pink":
                mAdapter.setColor(getColor(R.color.pink));
                break;
            case "blue":
                mAdapter.setColor(getColor(R.color.blue));
                break;
            case "green":
                mAdapter.setColor(getColor(R.color.green));
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals("color")){
            setColor(sharedPreferences.getString(key,"pink"));
        }
    }
}
