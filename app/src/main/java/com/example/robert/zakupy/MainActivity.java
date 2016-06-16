package com.example.robert.zakupy;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.robert.zakupy.model.Product;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listViewProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewProducts = (ListView) findViewById(R.id.listViewProducts);

        insertSystemData();


    }

    public void btnProducts_onClick(View view)
    {
        Intent i = new Intent(this, ProductsActivity.class);
        startActivity(i);

    }


    public void insertSystemData()
    {
        Context context = getApplicationContext();
        DbAdapter adapter = new DbAdapter(context);
        adapter.open();
        adapter.deleteProductTmp(1);
        adapter.deleteCurrentProductTmp(256);
        adapter.deleteCategoryTmp(256);

        adapter.insertCategory(1, "Apteka");
        adapter.insertCategory(2, "Elektronika");
        adapter.insertCategory(3, "Chemia");
        adapter.insertCategory(4, "Odzież");
        adapter.insertCategory(5, "Ogólne");
        adapter.insertCategory(6, "Warzywniak");

        adapter.insertProduct("Apap", 1, "op.");
        adapter.insertProduct("Pawulon", 1, "op.");
        adapter.insertProduct("Metanabol", 1, "mg");

        adapter.insertProduct("Telewizor", 2, "szt.");
        adapter.insertProduct("Słuchawki", 2, "szt.");
        adapter.insertProduct("Bateria", 2, "szt.");

        adapter.insertProduct("Mydło", 3, "szt.");
        adapter.insertProduct("Proszek do prania", 3, "kg");
        adapter.insertProduct("Płyn do naczyń", 3, "L");

        adapter.insertProduct("Czapka", 4, "szt.");
        adapter.insertProduct("Buty", 4, "szt.");
        adapter.insertProduct("Kurtka", 4, "szt.");

        adapter.insertProduct("Serek wiejski", 5, "szt.");
        adapter.insertProduct("Pierś z kury", 5, "szt.");
        adapter.insertProduct("Ryż", 5, "kg");

        adapter.insertProduct("Ogórek", 6, "szt.");
        adapter.insertProduct("Ziemniaki", 6, "kg");
        adapter.insertProduct("Papryka", 6, "szt.");

        adapter.close();

    }

    public void btnList_onClick(View view)
    {
        Intent i = new Intent(this, ListActivity.class);
        startActivity(i);

    }


}
