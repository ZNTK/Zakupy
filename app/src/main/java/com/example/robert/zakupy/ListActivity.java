package com.example.robert.zakupy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.robert.zakupy.model.CurrentProducts;

import java.util.ArrayList;

public class ListActivity extends Activity {
    ListView listViewCurrentProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listViewCurrentProducts = (ListView) findViewById(R.id.listViewCurrentProducts);

        Button QuickAdd = (Button) findViewById(R.id.btnQuickAdd);

        QuickAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListActivity.this,Pop.class));
            }
        });
        listViewCurrentProducts_DataBind();
    }

    public void listViewCurrentProducts_DataBind()
    {
        Context context = getApplicationContext();
        DbAdapter adapter = new DbAdapter(context);
        adapter.open();
        ArrayList<CurrentProducts> todoTaskList;
        todoTaskList = new ArrayList<>();

        Cursor c;
        c = adapter.getAllCurrentProducts();

        while (c.moveToNext()){
            int currentproductid = Integer.parseInt(c.getString(0));
            CurrentProducts currprod = adapter.getCurrentProductByRealId(currentproductid);
            todoTaskList.add(currprod);
        }
        ListAdapter myAdapter = new CurrentProductsAdapter(this, R.layout.activity_current_products_adapter, todoTaskList);
        listViewCurrentProducts.setAdapter(myAdapter);
        adapter.close();
    }
}
