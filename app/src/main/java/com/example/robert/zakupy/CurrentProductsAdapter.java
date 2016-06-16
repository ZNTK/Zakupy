package com.example.robert.zakupy;


import android.app.Activity;
import android.content.Context;

import android.content.Intent;
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
    private Activity activitylist;

    public  CurrentProductsAdapter(Activity activitylist, Context context, int resource, ArrayList<CurrentProducts> bah){
        super(context, resource, bah);
        this.context = context;
        this.resourceID = resource;
        this.activitylist = activitylist;
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
        TextView tvJednostka = (TextView) convertView.findViewById(R.id.textViewJednostkanaliscie);

        final View finalConvertView = convertView;
        final Context context = finalConvertView.getContext();
        final DbAdapter adapter = new DbAdapter(context);

        adapter.open();


        Product produkt = adapter.getProductById(currprod.id_product);

        adapter.close();

        tvName.setText(produkt.name);
        tvIlosc.setText(currprod.amount);
        tvCzykupione.setChecked(currprod.is_completed);
        tvJednostka.setText(produkt.unit);




        tvCzykupione.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                adapter.open();
                adapter.updateCurrentProductCheck(currprod.id, isChecked);
                adapter.close();

            }
        });




        tvIlosc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // context.startActivity(new Intent(activitylist,PopIlosc.class));
                Intent newactivity = new Intent(activitylist,PopIlosc.class);
                newactivity.putExtra("id",currprod.id);

                ((Activity) context).startActivityForResult(newactivity, 1);

            }
        });




        return convertView;
    }


}

