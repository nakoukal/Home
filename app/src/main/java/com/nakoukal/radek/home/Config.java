package com.nakoukal.radek.home;

/**
 * Created by uidv7359 on 29.8.2016.
 */
//package com.pinpoint.util;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Ananti
 */
public class Config {

    private Properties configuration;
    private File file;
    private String FILENAME = "config.ini";

    public Config(String path) {
        configuration = new Properties();
        this.file = new File(path, this.FILENAME);

    }

    public boolean load() {
        boolean retval = false;

        try {
            configuration.load(new FileInputStream(this.file));
            retval = true;
        } catch (IOException e) {
            Log.i("http","Configuration error: " + e.getMessage());
        }

        return retval;
    }

    public boolean store() {
        boolean retval = false;

        try {
            configuration.store(new FileOutputStream(this.file), null);
            retval = true;
        } catch (IOException e) {
            System.out.println("Configuration error: " + e.getMessage());
        }

        return retval;
    }

    public void set(String key, String value) {
        configuration.setProperty(key, value);
    }

    public String get(String key) {
        return configuration.getProperty(key);
    }
}
