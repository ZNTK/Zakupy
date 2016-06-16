package com.example.robert.zakupy;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;


import com.example.robert.zakupy.model.Category;
import com.example.robert.zakupy.model.CurrentProducts;
import com.example.robert.zakupy.model.Product;

import java.util.ArrayList;

/**
 * Created by Robert on 2016-06-11.
 */
public class ProductAdapter extends ArrayAdapter<Product> {
    private final Context context;
    private final int resourceID;
    RadioButton radioButtonCategory ;
    public ProductAdapter(Context context, int resource, ArrayList<Product> bah) {
        super(context, resource, bah);

        this.context = context;
        this.resourceID = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if(convertView == null)
            convertView = inflater.inflate(R.layout.row_product, null);
        final Product product = getItem(position);

        TextView tvName = (TextView) convertView.findViewById(R.id.textViewName);
        TextView tvUnit = (TextView) convertView.findViewById(R.id.textViewUnit);
        TextView tvCategory = (TextView) convertView.findViewById(R.id.textViewCategory);
        ImageButton btnDelete = (ImageButton) convertView.findViewById(R.id.buttonDelete);
        radioButtonCategory = (RadioButton) convertView.findViewById(R.id.radioButtonCategory);

        final View finalConvertView = convertView;
        Context context = finalConvertView.getContext();
        DbAdapter adapter = new DbAdapter(context);
        adapter.open();
        Category category = adapter.getCategoryById(product.id_category);
        adapter.close();




        tvName.setText(product.name);
        if(category != null)
            tvCategory.setText(category.name);
        else tvCategory.setText("");

        tvUnit.setText(" [" + product.unit + "]");

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = finalConvertView.getContext();
                DbAdapter adapter = new DbAdapter(context);
                adapter.open();
                adapter.deleteProduct(product.id);
                adapter.close();

                if(context instanceof ProductsActivity)
                    ((ProductsActivity) context).refreshProductList("");
            }
        });






        if(checkIfProductIsInCurrentProducts(product.id, finalConvertView) == true)
            convertView.setBackgroundColor(Color.parseColor("#00FF00"));
        else convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));


        return convertView;
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




}
