package com.nakoukal.radek.home;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.widget.Button;

import java.net.URL;

/**
 * Created by uidv7359 on 17.03.2017.
 */

public class GpioPin extends Activity{
    private String  m_pin_name;
    private boolean m_pin_value;
    private URL m_url;
    private int m_off_timeout;
    private Context m_activity_context;
    public Button button;

    public GpioPin(Context activity_context)
    {
        m_activity_context = activity_context;
    }

    public String GetPinName()
    {
        return m_pin_name;
    }

    public void SetPinName(String pin_name){m_pin_name = pin_name; }

    public boolean GetPinValue()
    {
        return m_pin_value;
    }

    public void SetPinvalue(boolean pin_value)
    {
        m_pin_value = pin_value;
    }

    //public void SetUrl(URL url){ m_url = url; }

    public void SetOffTimeout(int off_timeout){ m_off_timeout = off_timeout; }


}
