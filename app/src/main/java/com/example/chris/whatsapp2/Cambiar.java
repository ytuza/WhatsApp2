package com.example.chris.whatsapp2;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Cambiar extends AppCompatActivity implements View.OnClickListener {

    EditText editNom;
    EditText editCel;
    Button btActualizar;
    int pos;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar);

        Bundle bundle = getIntent().getExtras();
        pos = bundle.getInt("ID");

        editNom = (EditText) findViewById(R.id.nombrec);
        editCel = (EditText) findViewById(R.id.numeroc);
        btActualizar = (Button) findViewById(R.id.appendc);

        DBAdapter db = new DBAdapter(this);
        db.open();
        Cursor c = db.getContact(pos);
        editNom.setText(c.getString(1));
        editCel.setText(c.getString(2));
        db.close();

        btActualizar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appendc:
                DBAdapter db = new DBAdapter(this);
                db.open();
                db.updateContact(pos, editNom.getText().toString(), editCel.getText().toString());
                db.close();
                finish();
                break;

            default:
                break;
        }
    }
}
