package com.example.chris.whatsapp2;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

import java.lang.reflect.Array;

public class Chat1 extends AppCompatActivity implements View.OnClickListener {


    LinearLayout contenedor;
    TextView cchat1;
    Button boton;
    EditText mensaje;
    Button actual;
    int obId;
    int fin = 0;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat1);

        cchat1 = (TextView) findViewById(R.id.Contenido);
        boton = (Button)findViewById(R.id.BotonEnviar);
        mensaje = (EditText) findViewById(R.id.msjingresado);
        Bundle bundle = getIntent().getExtras();
        obId = bundle.getInt("ID");

        actual.setAllCaps(false);

        boton.setOnClickListener( this);
        actual.setOnClickListener(this);



    }

    @Override
    protected void onResume() {

        DBAdapter db = new DBAdapter(this);
        db.open();
        actual.setText("  " + db.getContact(obId).getString(1));
        Cursor c = db.getMensajes(obId);

        if (c.moveToFirst())
        {
            do {
                if (c.getInt(0)>fin) {
                    final Button boton = new Button(this);
                    boton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));

                    boton.setAllCaps(false);
                    boton.setText(c.getString(2));
                    boton.setId(c.getInt(0));
                    boton.setPadding(20,0,0,0);
                    contenedor.addView(boton);
                    registerForContextMenu(boton);

                    fin = c.getInt(0);
                }
            } while (c.moveToNext());
        }
        db.close();

        super.onResume();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // Creamos el objeto que debe inflar la vista del menú en la pantalla.
        MenuInflater inflater = new MenuInflater(this);

        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.BotonEnviar) {

                DBAdapter db = new DBAdapter(this);
                db.open();
                db.insertMensaje(obId, mensaje.getText().toString());
                db.close();
                mensaje.setText("");
                onResume();
        }
    }

    public void eliMensaje(int pos){
        DBAdapter db = new DBAdapter(this);
        db.open();
        db.deleteMensaje(pos);
        db.close();
    }
}