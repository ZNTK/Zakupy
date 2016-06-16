package com.example.robert.zakupy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Robert on 2016-06-16.
 */
public class PopIlosc extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popupilosc);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.5),(int)(height*.3));

        final EditText ustIlosc = (EditText) findViewById(R.id.editTextIloscpPop);
        Button SetIlosc = (Button) findViewById(R.id.btn_ustawIlosc);

        final int idcurrprod = getIntent().getExtras().getInt("id");

        SetIlosc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ilosc = String.valueOf(ustIlosc.getText());

                Context context = getApplicationContext();
                DbAdapter adapter = new DbAdapter(context);
                adapter.open();


                adapter.updateCurrentProductAmount(idcurrprod,ilosc);

                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", "zmieniono ilosc");
                setResult(Activity.RESULT_OK, returnIntent);

                finish();
            }
        });
    }
}
