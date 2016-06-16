package com.example.robert.zakupy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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
                startActivityForResult(new Intent(ListActivity.this,Pop.class),1);
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

    public void refreshCurrentProductList()
    {
        Log.d("GIT", "redjo baton!");
        listViewCurrentProducts.setAdapter(null);
        listViewCurrentProducts_DataBind();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                refreshCurrentProductList();
            }
        }
    }
}
