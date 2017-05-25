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

    private String bt01name,bt01on,bt01off,bt01pin;
    private String host,port,name,user,pass;
    private Button bt01,bt02,bt03,bt04,bt05,bt06;

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



    Context thiscontext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        thiscontext = getActivity();
        final TaskCompleted self_task = this;
        View rootView = inflater.inflate(R.layout.gate_fragment, container, false);
        try {
            cfgDb = new ConfigDB(thiscontext);
            this.host = cfgDb.GetData("host");
            this.port = cfgDb.GetData("port");
            this.name = cfgDb.GetData("name");
            this.user = cfgDb.GetData("user");
            this.pass = cfgDb.GetData("pass");

            this.bt01name = cfgDb.GetData("bt01name");
            this.bt01on = cfgDb.GetData("bt01on");
            this.bt01off = cfgDb.GetData("bt01off");
            this.bt01pin = cfgDb.GetData("bt01pin");
        }
        catch (Exception e)
        {
            Toast.makeText(thiscontext, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }


        bt01 = (Button) rootView.findViewById(R.id.bt01);
        bt02 = (Button) rootView.findViewById(R.id.bt02);
        bt03 = (Button) rootView.findViewById(R.id.bt03);
        bt04 = (Button) rootView.findViewById(R.id.bt04);
        bt05 = (Button) rootView.findViewById(R.id.bt05);
        bt06 = (Button) rootView.findViewById(R.id.bt06);

        if(bt01name != "")
            bt01.setEnabled(true);
            bt01.setText(bt01name);

        try {
            SendRequestAsyncTask req = new SendRequestAsyncTask(thiscontext,this);
            req.SetUser(user);
            req.SetPass(pass);
            req.SetUrl(new URL("http://"+host+":"+port+"/smarthome/gpio_control.php?dev="+name+"&act=readall"));
            req.execute();
        }
        catch(Exception e){
            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

        bt01.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
            try {
                SendRequestAsyncTask srBt01 = new SendRequestAsyncTask(thiscontext,self_task);
                srBt01.SetUrl(new URL(bt01on));
                srBt01.execute();
            }
            catch(Exception e){
                Toast.makeText(getActivity().getApplicationContext(), e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }

            }
        });
        /*
        button18.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                try {
                    SendRequestAsyncTask srBt18 = new SendRequestAsyncTask(thiscontext,self_task);
                    srBt18.SetUrl(new URL("http://"+host+":"+port+"/smarthome/gpio_control.php?dev="+name+"&act=opengate&bit=18"));
                    srBt18.execute();
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
                    srBt18.SetUrl(new URL("http://"+host+":"+port+"/smarthome/gpio_control.php?dev="+name+"&act=opengate&bit=18"));
                    srBt18.execute();

                    SendRequestAsyncTask srBt17 = new SendRequestAsyncTask(thiscontext,self_task);
                    srBt17.SetUrl(new URL("http://"+host+":"+port+"/smarthome/gpio_control.php?dev="+name+"&act=opengate&bit=17"));
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
                    srBt21.SetUrl(new URL("http://"+host+":"+port+"/smarthome/gpio_control.php?dev="+name+"&act=writevalue&bit=21"));
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
                    srBt22.SetUrl(new URL("http://"+host+":"+port+"/smarthome/gpio_control.php?dev="+name+"&act=writevalue&bit=22"));
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
                    srBt27.SetUrl(new URL("http://"+host+":"+port+"/smarthome/gpio_control.php?dev="+name+"&act=writevalue&bit=27"));
                    srBt27.execute();
                }
                catch(Exception e){
                    Toast.makeText(getActivity().getApplicationContext(), e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        });
        */
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
                /*
                switch (bit) {
                    case 17:
                        if(val == 1)
                            button17.setBackgroundColor(Color.RED);
                        else
                            button17.setBackgroundColor(Color.LTGRAY);
                        break;
                    case 18:
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
*/
            }
        }catch (JSONException e) {
            Toast.makeText(thiscontext,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
