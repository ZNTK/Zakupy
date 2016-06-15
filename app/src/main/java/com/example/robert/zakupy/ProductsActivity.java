package com.example.robert.zakupy;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.robert.zakupy.model.Product;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {
    ListView listViewProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewProducts = (ListView) findViewById(R.id.listViewProducts);
        listViewProducts_DataBind("Name");
    }

    public void refreshProductList(String orderBy)
    {
        listViewProducts.setAdapter(null);
        listViewProducts_DataBind(orderBy);
    }


    public void listViewProducts_DataBind(String orderBy)
    {
        Context context = getApplicationContext();
        DbAdapter adapter = new DbAdapter(context);
        adapter.open();
        ArrayList<Product> todoTaskList;
        todoTaskList = new ArrayList<>();

        Cursor c;
        if(orderBy == "")
            c = adapter.getAllProducts();
        else c = adapter.getAllProductsOrderBy(orderBy);
        while(c.moveToNext()) {
            int productId = Integer.parseInt(c.getString(0));
            Product product = adapter.getProductById(productId);
            todoTaskList.add(product);

        }
        ListAdapter myAdapter = new ProductAdapter(this, R.layout.row_product, todoTaskList);
        listViewProducts.setAdapter(myAdapter);
        adapter.close();
    }

    public void radioGroupOrderBy_onClick(View view)
    {
        RadioButton radioButtonName = (RadioButton) findViewById(R.id.radioButtonName);
        RadioButton radioButtonCategory = (RadioButton) findViewById(R.id.radioButtonCategory);

        if(radioButtonName.isSelected())
        {
            refreshProductList("Name");
        }
        if(radioButtonCategory.isSelected())
        {
            refreshProductList("Category");
        }


    }
}




