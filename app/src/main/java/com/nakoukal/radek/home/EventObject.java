package com.nakoukal.radek.home;

/**
 * Created by uidv7359 on 19.9.2016.
 */
public class EventObject {
    private String date;
    private String ip;
    private String dev;
    private int bit;
    private int val;


    public String GetDate()
    {
        return this.date;
    }

    public String GetIP()
    {
        return this.ip;
    }

    public String GetDev()
    {
        return this.dev;
    }

    public Integer GetBit()
    {
        return this.bit;
    }

    public Integer GetValue()
    {
        return this.val;
    }

    public void SetDate(String date)
    {
        this.date = date;
    }

    public void SetIP(String ip)
    {
        this.ip = ip;
    }

    public void SetDev(String dev)
    {
        this.dev = dev;
    }

    public void SetBit(int bit)
    {
        this.bit = bit;
    }

    public void SetValue(int val)
    {
        this.val = val;
    }



}
