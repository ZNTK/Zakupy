package com.example.robert.zakupy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by YOSS on 2016-06-15.
 */
public class Pop extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.popupquickadd);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.9),(int)(height*.45));

        final EditText editTextNazwa = (EditText) findViewById(R.id.editTextNazwa);
        final EditText editTextJednostka = (EditText) findViewById(R.id.editTextJednostka);
        final EditText editTextIlosc = (EditText) findViewById(R.id.editTextIlosc);

        TextView textViewJednostka = (TextView) findViewById(R.id.textViewJednostka);
        TextView textViewIlosc = (TextView) findViewById(R.id.textViewIlosc);

        editTextJednostka.setWidth((int)(width*.40));
        editTextIlosc.setWidth((int)(width*.40));
        textViewIlosc.setWidth((int)(width*.45));
        textViewJednostka.setWidth((int)(width*.45));


        Button AddToProduct = (Button) findViewById(R.id.btnDodajDoListyZakupow);

        AddToProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nazwa = String.valueOf(editTextNazwa.getText());
                String jednostki = String.valueOf(editTextJednostka.getText());
                String ilosc = String.valueOf(editTextIlosc.getText());

                Context context = getApplicationContext();
                DbAdapter adapter = new DbAdapter(context);
                adapter.open();

                long productid = adapter.insertProduct(nazwa, 1, jednostki);


                adapter.insertCurrentProduct((int) productid, ilosc);

                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", "dodano produkt");
                setResult(Activity.RESULT_OK, returnIntent);


                finish();
            }
        });

    }
}
