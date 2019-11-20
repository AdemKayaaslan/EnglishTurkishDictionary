package com.ademkayaaslan.engturdictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    EditText enWordText;
    TextView trWordText;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enWordText = findViewById(R.id.enWordText);
        trWordText = findViewById(R.id.trWordText);

        dbHelper = new DatabaseHelper(this);
        try {
            dbHelper.createDataBase();
            dbHelper.openDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void translate (View view) {
        String enWord = enWordText.getText().toString();
        Cursor crs = dbHelper.getDatabase().query("kelimeler", new String[]{ "kelimeTr" }, "kelimeEn = ?", new String[]{ enWord }, null, null, null);
        if(crs.getCount()>0){
            crs.moveToFirst();
            String trWord = crs.getString(crs.getColumnIndex("kelimeTr"));
            trWordText.setText(trWord);
        }else{
            Toast.makeText(getApplicationContext(), "No word found!", Toast.LENGTH_SHORT).show();
            enWordText.getText().clear();
        }
    }

    public void changeActivity (View view) {
        Intent intent = new Intent(getApplicationContext(),TurToEng.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.close();
    }
}
