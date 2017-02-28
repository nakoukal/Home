package com.nakoukal.radek.home;

/**
 * Created by uidv7359 on 22.8.2016.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * A placeholder fragment containing a simple view.
 */
public class GateFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private String host,port,name,user,pass;

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
    Context thiscontext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        thiscontext = getActivity();
        View rootView = inflater.inflate(R.layout.gate_fragment, container, false);

        String path = GateFragment.this.getActivity().getFilesDir().getAbsolutePath();
        Config cfg = new Config(path);
        if (cfg.load()) {
            this.host = cfg.get("host");
            this.port = cfg.get("port");
            this.name = cfg.get("name");
            this.user = cfg.get("user");
            this.pass = cfg.get("pass");
        }

        button17 = (Button) rootView.findViewById(R.id.button17);
        button18 = (Button) rootView.findViewById(R.id.button18);
        button27 = (Button) rootView.findViewById(R.id.button27);
        button22 = (Button) rootView.findViewById(R.id.button22);
        button21 = (Button) rootView.findViewById(R.id.button21);
        buttonAll = (Button) rootView.findViewById(R.id.buttonAll);

        try {
            AsyncMessage req = new AsyncMessage();
            req.setUrl(new URL("http://"+host+":"+port+"/smarthome/gpio_control.php?dev="+name+"&act=readall"));
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
                AsyncMessage srBt17 = new AsyncMessage();
                srBt17.setUrl(new URL("http://"+host+":"+port+"/smarthome/gpio_control.php?dev="+name+"&act=opengate&bit=17"));
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
                    AsyncMessage srBt18 = new AsyncMessage();
                    srBt18.setUrl(new URL("http://"+host+":"+port+"/smarthome/gpio_control.php?dev="+name+"&act=opengate&bit=18"));
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
                    AsyncMessage srBt18 = new AsyncMessage();
                    srBt18.setUrl(new URL("http://"+host+":"+port+"/smarthome/gpio_control.php?dev="+name+"&act=opengate&bit=18"));
                    srBt18.execute();

                    AsyncMessage srBt17 = new AsyncMessage();
                    srBt17.setUrl(new URL("http://"+host+":"+port+"/smarthome/gpio_control.php?dev="+name+"&act=opengate&bit=17"));
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
                    AsyncMessage srBt21 = new AsyncMessage();
                    srBt21.setUrl(new URL("http://"+host+":"+port+"/smarthome/gpio_control.php?dev="+name+"&act=writevalue&bit=21"));
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
                    AsyncMessage srBt22 = new AsyncMessage();
                    srBt22.setUrl(new URL("http://"+host+":"+port+"/smarthome/gpio_control.php?dev="+name+"&act=writevalue&bit=22"));
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
                    AsyncMessage srBt27 = new AsyncMessage();
                    srBt27.setUrl(new URL("http://"+host+":"+port+"/smarthome/gpio_control.php?dev="+name+"&act=writevalue&bit=27"));
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

    public class AsyncMessage extends AsyncTask<String, String, String> {

        ProgressDialog mProgress;
        private TaskCompleted mCallback;
        private URL url;
        private Button button;

        public void setUrl(URL url)
        {
            this.url = url;
        }


        @Override
        public void onPreExecute() {
            mProgress = new ProgressDialog(thiscontext);
            mProgress.setMessage("Gate");
            mProgress.setMax(100);
            mProgress.show();
        }

        @Override
        protected void onProgressUpdate(String... values) {

            mProgress.setMessage(values[0]);
        }

        @Override
        protected String doInBackground(String... values) {
            Integer iResult=0;
            try{
                //JSONObject postDataParams = new JSONObject();
                //postDataParams.put("name", "abhay");
                //postDataParams.put("email", "abhay@gmail.com");
                //Log.e("params",postDataParams.toString());
                String login  = user+":"+pass;
                String basicAuth = "Basic " + new String(Base64.encode(login.getBytes(), Base64.NO_WRAP));
                HttpURLConnection conn = (HttpURLConnection) this.url.openConnection();
                conn.setRequestProperty("Authorization",basicAuth);
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);


                //OutputStream os = conn.getOutputStream();
                //BufferedWriter writer = new BufferedWriter(
                //        new OutputStreamWriter(os, "UTF-8"));
                //writer.write(getPostDataString(postDataParams));

                //writer.flush();
                //writer.close();
                //os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {
                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String results) {
            mProgress.dismiss();
            //This is where you return data back to caller
            try{
                JSONObject jsonRootObject = new JSONObject(results);
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

                }
            }catch (JSONException e) {
                Toast.makeText(thiscontext,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
    }


}
