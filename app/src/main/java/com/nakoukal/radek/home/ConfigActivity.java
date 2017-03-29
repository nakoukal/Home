package com.nakoukal.radek.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ConfigActivity extends AppCompatActivity {
    private Boolean result = false;
    private EditText host,port,name,user,pass;
    private Button save;
    private ConfigDB cfgDb;
    // Config cfg;
    // Gets the data repository in write mode
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        host = (EditText) findViewById(R.id.etHost);
        port = (EditText) findViewById(R.id.etPort);
        name = (EditText) findViewById(R.id.etName);
        user = (EditText) findViewById(R.id.etUser);
        pass = (EditText) findViewById(R.id.etPass);
        save = (Button) findViewById(R.id.buttonSave);

        try {
            cfgDb = new ConfigDB(this);
            host.setText(cfgDb.GetData("host"));
            port.setText(cfgDb.GetData("port"));
            name.setText(cfgDb.GetData("name"));
            user.setText(cfgDb.GetData("user"));
            pass.setText(cfgDb.GetData("pass"));
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
                cfgDb.AddData("host",host.getText().toString());
                cfgDb.AddData("port",port.getText().toString());
                cfgDb.AddData("name",name.getText().toString());
                cfgDb.AddData("user",user.getText().toString());
                cfgDb.AddData("pass",pass.getText().toString());
            }
        });

    }




}
