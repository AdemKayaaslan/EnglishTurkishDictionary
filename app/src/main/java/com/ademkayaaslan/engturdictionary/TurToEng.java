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

public class TurToEng extends AppCompatActivity {
    EditText trWordText;
    TextView enWordText;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tur_to_eng);

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
        String trWord = trWordText.getText().toString();
        Cursor crs = dbHelper.getDatabase().query("kelimeler", new String[]{ "kelimeEn" }, "kelimeTr = ?", new String[]{ trWord }, null, null, null);
        if(crs.getCount()>0){
            crs.moveToFirst();
            String enWord = crs.getString(crs.getColumnIndex("kelimeEn"));
            enWordText.setText(enWord);
        }else{
            Toast.makeText(getApplicationContext(), "No word found!", Toast.LENGTH_SHORT).show();
            trWordText.getText().clear();
        }
    }

    public void changeActivity (View view) {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}
