package com.nakoukal.radek.home;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.widget.Button;

import java.net.URL;

/**
 * Created by uidv7359 on 17.03.2017.
 */

public class GpioPin {
    private String m_text;
    private int  m_pin_name;
    private int m_pin_value;
    private int m_delay;

    public GpioPin(String text,int pin_name, int pin_value, int delay)
    {
        m_text = text;
        m_pin_name = pin_name;
        m_pin_value = pin_value;
        m_delay = delay;
    }

    public int GetPinName()
    {
        return m_pin_name;
    }

    public void SetPinName(int pin_name){m_pin_name = pin_name; }

    public int GetPinValue()
    {
        return m_pin_value;
    }

    public void SetPinvalue(int pin_value)
    {
        m_pin_value = pin_value;
    }

    public void SetDelayt(int delay){ m_delay = delay; }

    public String GetText() {return m_text;}

    public int GetDelay() {return m_delay;}


}
