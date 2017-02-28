package com.nakoukal.radek.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;

public class ConfigActivity extends AppCompatActivity {
    Boolean result = false;
    EditText host,port,name,user,pass;
    Button save;
    Config cfg;
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
        String path = ConfigActivity.this.getFilesDir().getAbsolutePath();
        cfg = new Config(path);
        if(cfg.load())
        {
            host.setText(cfg.get("host"));
            port.setText(cfg.get("port"));
            name.setText(cfg.get("name"));
            user.setText(cfg.get("user"));
            pass.setText(cfg.get("pass"));
        }

        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                cfg.set("host",host.getText().toString());
                cfg.set("port",port.getText().toString());
                cfg.set("name",name.getText().toString());
                cfg.set("user",user.getText().toString());
                cfg.set("pass",pass.getText().toString());
                cfg.store();

            }
        });



    }


}
