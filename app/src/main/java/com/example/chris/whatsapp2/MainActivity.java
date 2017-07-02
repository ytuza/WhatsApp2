package com.example.chris.whatsapp2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout lp;
    int Ultimo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.fab:
                        Intent intent = new Intent(MainActivity.this, Crear.class);
                        startActivity(intent);
                        break;

                    default:


                }
            }});

    }

    @Override
        protected void onResume(){
        lp = (LinearLayout) findViewById(R.id.cont);
        DBAdapter db = new DBAdapter(this);

        db.open();

        Cursor c = db.getAllContacts();

        if (c.moveToFirst())
        {
            do {
                if (c.getInt(0) > Ultimo) {
                    final LinearLayout lineal = new LinearLayout(this);


                    final Button boton = new Button(this);
                    boton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    boton.setText(c.getString(1));
                    boton.setId(c.getInt(0));


                    boton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            lp.removeAllViewsInLayout();
                            Ultimo = 0;

                            Bundle bundle = new Bundle();
                            bundle.putInt("ID", boton.getId());

                            Intent intent= new Intent(MainActivity.this, Chat1.class);
                            intent.putExtras(bundle);

                            startActivity(intent);
                        }
                    });

                    final Button botonX = new Button(this);
                    botonX.setText("CL");
                    botonX.setId(c.getInt(0));
                    botonX.setLayoutParams(new LinearLayout.LayoutParams(90, LinearLayout.LayoutParams.WRAP_CONTENT));
                    botonX.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            borrarus(botonX.getId());
                            lp.removeAllViewsInLayout();
                            Ultimo = 0;
                            onResume();
                        }
                    });

                    lineal.addView(botonX);
                    lineal.addView(boton);

                    lp.addView(lineal);

                    Ultimo = c.getInt(0);
                }
            } while (c.moveToNext());
        }
        db.close();

        super.onResume();
    }




    public void borrarus(int pos){
        DBAdapter db = new DBAdapter(this);
        db.open();
        db.deleteContact(pos);
        db.close();
    }

}
