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
import static android.R.attr.host;

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


    Button button17,button18,buttonAll,button21,button22,button27;
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
            this.address = "http://"+hostname+":"+port+"/smarthome/gpio_control.php?dev="+name+"&act=";
        }
        catch (Exception e)
        {
            Toast.makeText(thiscontext, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }


        button17 = (Button) rootView.findViewById(R.id.bt02name);
        button18 = (Button) rootView.findViewById(R.id.bt01name);
        button27 = (Button) rootView.findViewById(R.id.bt05name);
        button22 = (Button) rootView.findViewById(R.id.bt04name);
        button21 = (Button) rootView.findViewById(R.id.button21);
        buttonAll = (Button) rootView.findViewById(R.id.bt03name);

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

        button18.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                try {
                    SendRequestAsyncTask srBt24 = new SendRequestAsyncTask(thiscontext,self_task);
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
                    SendRequestAsyncTask srBt18 = new SendRequestAsyncTask(thiscontext,self_task);
                    srBt18.SetUser(user);
                    srBt18.SetPass(pass);
                    srBt18.SetUrl(new URL(address+"opengate&bit=24"));
                    srBt18.execute();

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

        button21.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                try {
                    SendRequestAsyncTask srBt21 = new SendRequestAsyncTask(thiscontext,self_task);
                    srBt21.SetUser(user);
                    srBt21.SetPass(pass);
                    srBt21.SetUrl(new URL(address+"writevalue&bit=21"));
                    srBt21.execute();
                }
                catch(Exception e){
                    Toast.makeText(getActivity().getApplicationContext(), e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        button22.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                try {
                    SendRequestAsyncTask srBt22 = new SendRequestAsyncTask(thiscontext,self_task);
                    srBt22.SetUser(user);
                    srBt22.SetPass(pass);
                    srBt22.SetUrl(new URL(address+"writevalue&bit=22"));
                    srBt22.execute();
                }
                catch(Exception e){
                    Toast.makeText(getActivity().getApplicationContext(), e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        button27.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                try {
                    SendRequestAsyncTask srBt27 = new SendRequestAsyncTask(thiscontext,self_task);
                    srBt27.SetUser(user);
                    srBt27.SetPass(pass);
                    srBt27.SetUrl(new URL(address+"writevalue&bit=27"));
                    srBt27.execute();
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
                            button18.setBackgroundColor(Color.RED);
                        else
                            button18.setBackgroundColor(Color.LTGRAY);
                        break;

                    case 21:
                        if(val == 1)
                            button21.setBackgroundColor(Color.RED);
                        else
                            button21.setBackgroundColor(Color.LTGRAY);
                        break;
                    case 22:
                        if(val == 1)
                            button22.setBackgroundColor(Color.RED);
                        else
                            button22.setBackgroundColor(Color.LTGRAY);
                        break;

                    case 27:
                        if(val == 1)
                            button27.setBackgroundColor(Color.RED);
                        else
                            button27.setBackgroundColor(Color.LTGRAY);
                        break;

                }

            }
        }catch (JSONException e) {
            Toast.makeText(thiscontext,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
