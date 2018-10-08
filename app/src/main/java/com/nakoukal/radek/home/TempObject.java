package com.nakoukal.radek.home;

import java.io.Serializable;

/**
 * Created by uidv7359 on 19.9.2016.
 */
public class TempObject implements Serializable {
    private String des;
    private double temp;
    private double req;
    private int stat;
    private String date;
    private String sensorid;

    private static final long serialVersionUID = 1L;

    public TempObject() {
    }

    public TempObject(String des,double temp,double req, String date,int stat ,String sensorid) {
        this.des = des;
        this.temp = temp;
        this.req = req;
        this.date = date;
        this.sensorid = sensorid;
        this.stat = stat;
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

    public Double GetReq()
    {
        return this.req;
    }

    public Integer GetStat() {
        return this.stat;
    }

    public String GetSensorid()
    {
        return this.sensorid;
    }

}
