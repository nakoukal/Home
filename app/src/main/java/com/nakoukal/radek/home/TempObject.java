package com.nakoukal.radek.home;

import java.io.Serializable;

/**
 * Created by uidv7359 on 19.9.2016.
 */
public class TempObject implements Serializable {
    private String des;
    private double temp;
    private String date;
    private String sensorid;

    private static final long serialVersionUID = 1L;

    public TempObject(String des,double temp,String date,String sensorid) {
        this.des = des;
        this.temp = temp;
        this.date = date;
        this.sensorid = sensorid;
    }

    public String GetDes()
    {
        return this.des;
    }

    public String GetDate()
    {
        return this.date;
    }

    public Double GetTemp()
    {
        return this.temp;
    }

    public String GetSensorid()
    {
        return this.sensorid;
    }

}
