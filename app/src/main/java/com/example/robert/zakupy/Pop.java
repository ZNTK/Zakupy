package com.example.robert.zakupy;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
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

        EditText editTextJednostka = (EditText) findViewById(R.id.editTextJednostka);
        EditText editTextIlosc = (EditText) findViewById(R.id.editTextIlosc);

        TextView textViewJednostka = (TextView) findViewById(R.id.textViewJednostka);
        TextView textViewIlosc = (TextView) findViewById(R.id.textViewIlosc);

        editTextJednostka.setWidth((int)(width*.40));
        editTextIlosc.setWidth((int)(width*.40));
        textViewIlosc.setWidth((int)(width*.45));
        textViewJednostka.setWidth((int)(width*.45));



    }
}
