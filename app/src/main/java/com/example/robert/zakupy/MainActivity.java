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

        Context context = getApplicationContext();
        DbAdapter adapter = new DbAdapter(context);
        adapter.open();
        adapter.insertProduct("Chleb", 1, "szt.");
        adapter.insertProduct("Pomidor", 1, "szt.");
       adapter.insertProduct("Wpierdol", 1, "[kg]");
        adapter.insertProduct("Ogorek", 1, "szt.");
        adapter.insertProduct("Szylook", 1, "szt.");
       // adapter.insertCategory(1, "Ogólne");
        adapter.close();
        Log.d("GIT", "Wykonano!");


    }

    public void btnProducts_onClick(View view)
    {
        Intent i = new Intent(this, ProductsActivity.class);
        startActivity(i);

    }

    public void btnList_onClick(View view)
    {
        Intent i = new Intent(this, ListActivity.class);
        startActivity(i);

    }

//    public void clearProductList()
//    {
//        listViewProducts.setAdapter(null);
//
//    }

//    public void listViewProducts_DataBind(String orderBy)
//    {
//        Context context = getApplicationContext();
//        DbAdapter adapter = new DbAdapter(context);
//        adapter.open();
//        ArrayList<Product> todoTaskList;
//        todoTaskList = new ArrayList<>();
//
//        Cursor c;
//        if(orderBy == "")
//            c = adapter.getAllProducts();
//        else c = adapter.getAllProductsOrderBy(orderBy);
//        while(c.moveToNext()) {
//            int productId = Integer.parseInt(c.getString(0));
//            Product product = adapter.getProductById(productId);
//            todoTaskList.add(product);
//
//        }
//        ListAdapter myAdapter = new ProductAdapter(this, R.layout.row_product, todoTaskList);
//        listViewProducts.setAdapter(myAdapter);
//        adapter.close();
//    }


}
