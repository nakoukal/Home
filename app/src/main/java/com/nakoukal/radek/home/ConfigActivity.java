package com.nakoukal.radek.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ConfigActivity extends AppCompatActivity {
    private Boolean result = false;

    private EditText etBt01name,etBt01on,etBt01off,etBt01pin;

    private Button save;
    private ConfigDB cfgDb;
    // Config cfg;
    // Gets the data repository in write mode
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        etBt01name = (EditText) findViewById(R.id.etBt01name);
        etBt01on = (EditText) findViewById(R.id.etBt01on);
        etBt01off = (EditText) findViewById(R.id.etBt01off);
        etBt01pin = (EditText) findViewById(R.id.etBt01pin);

        save = (Button) findViewById(R.id.buttonSave);

        try {
            cfgDb = new ConfigDB(this);
            etBt01name.setText(cfgDb.GetData("bt01name"));
            etBt01on.setText(cfgDb.GetData("bt01on"));
            etBt01off.setText(cfgDb.GetData("bt01off"));
            etBt01pin.setText(cfgDb.GetData("bt01pin"));
        }
        catch (Exception e)
        {
            Toast.makeText(this.getApplicationContext(), e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }


        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                cfgDb.AddData("bt01name",etBt01name.getText().toString());
                cfgDb.AddData("bt01on",etBt01on.getText().toString());
                cfgDb.AddData("bt01off",etBt01off.getText().toString());
                cfgDb.AddData("bt01pin",etBt01pin.getText().toString());
            }
        });

    }




}
