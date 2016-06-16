package com.example.robert.zakupy;


import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robert.zakupy.model.CurrentProducts;
import com.example.robert.zakupy.model.Product;

import java.util.ArrayList;

public class CurrentProductsAdapter extends ArrayAdapter<CurrentProducts> {
    private final Context context;
    private final int resourceID;

    public  CurrentProductsAdapter(Context context, int resource, ArrayList<CurrentProducts> bah){
        super(context, resource, bah);
        this.context = context;
        this.resourceID = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if (convertView == null)
            convertView = inflater.inflate(R.layout.row_current_products, null);
        final CurrentProducts currprod = getItem(position);

        TextView tvName = (TextView) convertView.findViewById(R.id.textViewIdOnCurrent);
        EditText tvIlosc = (EditText) convertView.findViewById(R.id.editTextIlosc);
        CheckBox tvCzykupione = (CheckBox) convertView.findViewById(R.id.radiobutonczykupine);

        final View finalConvertView = convertView;
        Context context = finalConvertView.getContext();
        final DbAdapter adapter = new DbAdapter(context);

        adapter.open();


        Product produkt = adapter.getProductById(currprod.id_product);

        adapter.close();

        tvName.setText(produkt.name);
        tvIlosc.setText(currprod.amount);
        tvCzykupione.setChecked(currprod.is_completed);

        tvCzykupione.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                adapter.open();
                adapter.updateCurrentProductCheck(currprod.id, isChecked);
                adapter.close();
            }
        });







        return convertView;
    }


}

