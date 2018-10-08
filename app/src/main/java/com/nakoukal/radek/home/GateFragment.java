package com.nakoukal.radek.home;

/**
 * gate fragment class
 */


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

/**
 * A placeholder fragment containing a simple view.
 */
public class GateFragment extends Fragment implements TaskCompleted{
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private ConfigDB cfgDb;
    private Context thiscontext;

    public String address,hostname,port,name,user,pass;
    private static final String ARG_SECTION_NUMBER = "section_number";
    public GateFragment() {

    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static GateFragment newInstance(int sectionNumber) {
        GateFragment fragment = new GateFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    Button button17,button18,button24,buttonAll,button21,button22,button27;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        thiscontext = getActivity();
        final TaskCompleted self_task = this;
        View rootView = inflater.inflate(R.layout.gate_fragment, container, false);
        thiscontext = getActivity();
        try {
            cfgDb = new ConfigDB(thiscontext);
            this.hostname = cfgDb.GetData("host");
            this.port = cfgDb.GetData("port");
            this.name = cfgDb.GetData("name");
            this.user = cfgDb.GetData("user");
            this.pass = cfgDb.GetData("pass");
            this.address = "https://"+hostname+":"+port+"/smarthome/gpio_control.php?dev="+name+"&act=";
        }
        catch (Exception e)
        {
            Toast.makeText(thiscontext, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }


        button17 = rootView.findViewById(R.id.button01);
        button24 = rootView.findViewById(R.id.button02);
        button18 = rootView.findViewById(R.id.button04);
        buttonAll = rootView.findViewById(R.id.button03);

        try {
            SendRequestAsyncTask req = new SendRequestAsyncTask(thiscontext,this);
            req.SetUser(user);
            req.SetPass(pass);
            req.SetUrl(new URL(address+"readall"));
            req.execute();
        }
        catch(Exception e){
            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

        button17.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                try {
                    SendRequestAsyncTask srBt17 = new SendRequestAsyncTask(thiscontext,self_task);
                    srBt17.SetUser(user);
                    srBt17.SetPass(pass);
                    srBt17.SetUrl(new URL(address+"opengate&bit=17"));
                    srBt17.execute();
                }
                catch(Exception e){
                    Toast.makeText(getActivity().getApplicationContext(), e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        button24.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                try {
                    SendRequestAsyncTask srBt24;
                    srBt24 = new SendRequestAsyncTask(thiscontext,self_task);
                    srBt24.SetUser(user);
                    srBt24.SetPass(pass);
                    srBt24.SetUrl(new URL(address+"opengate&bit=24"));
                    srBt24.execute();
                }
                catch(Exception e){
                    Toast.makeText(getActivity().getApplicationContext(), e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        buttonAll.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                try {
                    SendRequestAsyncTask srBt24;
                    srBt24 = new SendRequestAsyncTask(thiscontext,self_task);
                    srBt24.SetUser(user);
                    srBt24.SetPass(pass);
                    srBt24.SetUrl(new URL(address+"opengate&bit=24"));
                    srBt24.execute();

                    SendRequestAsyncTask srBt17;
                    srBt17 = new SendRequestAsyncTask(thiscontext,self_task);
                    srBt17.SetUser(user);
                    srBt17.SetPass(pass);
                    srBt17.SetUrl(new URL(address+"opengate&bit=17"));
                    srBt17.execute();

                }
                catch(Exception e){
                    Toast.makeText(getActivity().getApplicationContext(), e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        button18.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                try {
                    SendRequestAsyncTask srBt18;
                    srBt18 = new SendRequestAsyncTask(thiscontext,self_task);
                    srBt18.SetUser(user);
                    srBt18.SetPass(pass);
                    srBt18.SetUrl(new URL(address+"writevalue&bit=18"));
                    srBt18.execute();
                }
                catch(Exception e){
                    Toast.makeText(getActivity().getApplicationContext(), e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        });


        return rootView;
    }


    @Override
    public void onTaskComplete(String result) {

        //This is where you return data back to caller
        try{
            JSONObject jsonRootObject = new JSONObject(result);
            JSONArray jsonArray = jsonRootObject.optJSONArray("state");
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int bit = Integer.parseInt(jsonObject.optString("bit").toString());
                int val = Integer.parseInt(jsonObject.optString("val").toString());
                String dir = jsonObject.optString("dir").toString();
                //float salary = Float.parseFloat(jsonObject.optString("salary").toString());
                switch (bit) {
                    case 17:
                        if(val == 1)
                            button17.setBackgroundColor(Color.RED);
                        else
                            button17.setBackgroundColor(Color.LTGRAY);
                        break;
                    case 24:
                        if(val == 1)
                            button24.setBackgroundColor(Color.RED);
                        else
                            button24.setBackgroundColor(Color.LTGRAY);
                        break;

                    case 18:
                        if(val == 1)
                            button18.setBackgroundColor(Color.RED);
                        else
                            button18.setBackgroundColor(Color.LTGRAY);
                        break;

                }

            }
        }catch (JSONException e) {
            Toast.makeText(thiscontext,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
