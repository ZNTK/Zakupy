package com.example.robert.zakupy;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robert.zakupy.model.CurrentProducts;
import com.example.robert.zakupy.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {
    ListView listViewProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listViewProducts = (ListView) findViewById(R.id.listViewProducts);



        AdapterView.OnItemClickListener myListViewClicked = new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(ProductsActivity.this, "Clicked at positon = " + position, Toast.LENGTH_SHORT).show();
//                Object obj = (Product)parent.getItemAtPosition(position);



                Product product = (Product)parent.getItemAtPosition(position);
                Context context = getApplicationContext();
                DbAdapter adapter = new DbAdapter(context);
                adapter.open();

                if(checkIfProductIsInCurrentProducts(product.id, view) == false)
                {
                    adapter.insertCurrentProduct(product.id,"");
                    view.setBackgroundColor(Color.parseColor("#00FF00"));
                }

                else
                {
                    adapter.deleteCurrentProduct(product.id);
                    view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
                adapter.close();



            }
        };

        listViewProducts.setOnItemClickListener(myListViewClicked);

        listViewProducts_DataBind("Name");

        TextView tvUnit =  (TextView) findViewById(R.id.editTextNewProductUnit);
        tvUnit.setText("szt.");

        RadioButton radioButtonName = (RadioButton) findViewById(R.id.radioButtonName);
        radioButtonName.setChecked(true);

        Spinner spinnerCategory =  (Spinner) findViewById(R.id.spinnerCategory);
        ArrayAdapter<String> adapter;
        List<String> list;

        list = new ArrayList<String>();
        list.add("Ogólne");
        list.add("Chemia");
        list.add("Warzywniak");
        list.add("Odzież");
        list.add("Elektronika");
        list.add("Apteka");
        adapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.spinner_item_category, list);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerCategory.setAdapter(adapter);

    }


    public void refreshProductList(String orderBy)
    {
        if(orderBy == "")
        {
            RadioButton radioButtonName = (RadioButton) findViewById(R.id.radioButtonName);
            RadioButton radioButtonCategory = (RadioButton) findViewById(R.id.radioButtonCategory);

            if(radioButtonName.isChecked())
            {
                orderBy = "Name";
            }
            if(radioButtonCategory.isChecked())
            {
                orderBy = "id_category";
            }
        }



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
        Log.d("GIT", "redjo baton!");
        RadioButton radioButtonName = (RadioButton) findViewById(R.id.radioButtonName);
        RadioButton radioButtonCategory = (RadioButton) findViewById(R.id.radioButtonCategory);

        if(radioButtonName.isChecked())
        {
            refreshProductList("Name");
        }
        if(radioButtonCategory.isChecked())
        {
            refreshProductList("id_category");
        }


    }

    public boolean checkIfProductIsInCurrentProducts(int productId, View view)
    {
        Context context = view.getContext();
        DbAdapter adapter = new DbAdapter(context);
        adapter.open();
        CurrentProducts currentProduct = adapter.getCurrentProductById(productId);
        adapter.close();

        if(currentProduct == null)
            return false;
        else return true;
    }

    public void btnAddProduct_onClick(View view)
    {
        TextView tvName =  (TextView) findViewById(R.id.editTextNewProductName);
        TextView tvUnit =  (TextView) findViewById(R.id.editTextNewProductUnit);

        String productName = (String) tvName.getText().toString();
        if(productName.replace(" ","") == "" || productName == null || productName.isEmpty())
            Toast.makeText(ProductsActivity.this, "Podaj nazwę produktu!", Toast.LENGTH_SHORT).show();

        else
        {
            int categoryId = 5;
            Spinner spinnerCategory =  (Spinner) findViewById(R.id.spinnerCategory);
            String categoryName = (String)spinnerCategory.getSelectedItem();
            if(categoryName == "Apteka")
                categoryId = 1;
            if(categoryName == "Elektronika")
                categoryId = 2;
            if(categoryName == "Chemia")
                categoryId = 3;
            if(categoryName == "Odzież")
                categoryId = 4;
            if(categoryName == "Warzywniak")
                categoryId = 6;

            Context context = view.getContext();
            DbAdapter adapter = new DbAdapter(context);
            adapter.open();
            adapter.insertProduct(tvName.getText().toString(), categoryId, tvUnit.getText().toString());
            adapter.close();
            refreshProductList("");
            tvName.setText("");

            Toast.makeText(ProductsActivity.this, "Dodano " + productName + " do bazy produktów", Toast.LENGTH_SHORT).show();
        }

    }

    public void btngotozakupy_OnClick(View view)
    {
        Intent i = new Intent(this, ListActivity.class);
        startActivity(i);

    }
}




