package com.example.robert.zakupy;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

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
        RadioButton tvCzykupione = (RadioButton) convertView.findViewById(R.id.radiobutonczykupine);

        final View finalConvertView = convertView;
        Context context = finalConvertView.getContext();
        DbAdapter adapter = new DbAdapter(context);

        adapter.open();


        Product produkt = adapter.getProductById(currprod.id_product);

        adapter.close();

        tvName.setText(produkt.name);
        tvIlosc.setText("1");
        tvCzykupione.setChecked(currprod.is_completed);

        return convertView;
    }
}

