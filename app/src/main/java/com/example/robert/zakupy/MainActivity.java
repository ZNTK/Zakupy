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
       // adapter.deleteProductTmp(1);
//        adapter.insertProduct("Chleb", 1, "szt.");
//        adapter.insertProduct("Pomidor", 1, "szt.");
//       adapter.insertProduct("Wpierdol", 2, "[kg]");
//        adapter.insertProduct("Ogorek", 2, "szt.");
//        adapter.insertProduct("Szylook", 1, "szt.");
        adapter.deleteCurrentProductTmp(256);
        adapter.deleteCategoryTmp(256);
        adapter.insertCategory(1, "Ogólne");
        adapter.insertCategory(2, "Chemia");
        adapter.insertCategory(3, "Warzywniak");
        adapter.insertCategory(4, "Odzież");
        adapter.insertCategory(5, "Elektronika");
        adapter.insertCategory(6, "Apteka");
        adapter.close();
        Log.d("GIT", "Wykonano!");


    }

    public void btnProducts_onClick(View view)
    {
        Intent i = new Intent(this, ProductsActivity.class);
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
