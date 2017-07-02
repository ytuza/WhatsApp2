package com.example.chris.whatsapp2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Crear extends AppCompatActivity implements View.OnClickListener {

    Button Crearuno;
    EditText editNom;
    EditText editCel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear);

        Crearuno = (Button) findViewById(R.id.append);
        editNom = (EditText) findViewById(R.id.nombrenew);
        editCel = (EditText) findViewById(R.id.numeronew) ;


        Crearuno.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.append:
                DBAdapter db = new DBAdapter(this);
                db.open();
                long id = db.insertContact(editNom.getText().toString(), editCel.getText().toString());
                db.close();
                finish();
                break;

            default:
                break;
        }
    }
}
