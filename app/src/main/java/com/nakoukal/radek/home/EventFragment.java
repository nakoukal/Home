package com.nakoukal.radek.home;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

/**
 * Created by uidv7359 on 25.8.2016.
 */
public class EventFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout swipeLayout;
    ListView listView;
    ArrayList<EventObject> arrayList;
    private String host,port,name,user,pass;
    private ConfigDB cfgDb;
    private Context thiscontext;

    public static EventFragment newInstance() {
        EventFragment fragment = new EventFragment();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        thiscontext = getActivity();
        View rootView = inflater.inflate(R.layout.activity_fragment, container, false);
        listView = (ListView) rootView.findViewById(R.id.list);
        String path = EventFragment.this.getActivity().getFilesDir().getAbsolutePath();

        try {
            cfgDb = new ConfigDB(thiscontext);
            this.host = cfgDb.GetData("host");
            this.port = cfgDb.GetData("port");
            this.name = cfgDb.GetData("name");
            this.user = cfgDb.GetData("user");
            this.pass = cfgDb.GetData("pass");
        }
        catch (Exception e)
        {
            Toast.makeText(thiscontext, e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        swipeLayout.setOnRefreshListener(this);
        try {
            AsyncActivity req = new AsyncActivity();
            req.setUrl(new URL("https://"+host+":"+port+"/smarthome/gpio_control.php?dev="+name+"&act=readallevents"));
            req.execute();
        }
        catch(Exception e){
            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRefresh() {
        try {
            AsyncActivity req = new AsyncActivity();
            req.setUrl(new URL("https://"+host+":"+port+"/smarthome/gpio_control.php?dev=\"+name+\"&act=readallevents"));
            req.execute();
        }
        catch(Exception e){
            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
        //swipeLayout.setRefreshing(false);
    }

    public class AsyncActivity extends AsyncTask<String, String, String> {
        private URL url;
        private Button button;

        public void setUrl(URL url) {
            this.url = url;
        }


        @Override
        protected String doInBackground(String... values) {
            Integer iResult = 0;
            try {
                String login = user + ":" + pass;
                String basicAuth = "Basic " + new String(Base64.encode(login.getBytes(), Base64.NO_WRAP));
                HttpsURLConnection conn = (HttpsURLConnection) this.url.openConnection();

                // Create an SSLContext that uses our TrustManager
                SSLContext context = SSLContext.getInstance("TLS");
                TrustManager[] tmlist = {new MyTrustManager()};
                context.init(null, tmlist, null);
                conn.setSSLSocketFactory(context.getSocketFactory());
                conn.setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        //TODO: Make this more restrictive
                        return true;
                    }
                });
                conn.setRequestProperty("Authorization", basicAuth);
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String results) {
            //This is where you return data back to caller
            try {
                JSONObject jsonRootObject = new JSONObject(results);
                JSONArray jsonArray = jsonRootObject.optJSONArray("event");
                arrayList = new ArrayList(jsonArray.length());
                for(int i=0;i < jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String time = jsonObject.optString("time").toString();
                    String ip  = jsonObject.optString("ip").toString();
                    String device  = jsonObject.optString("device").toString();
                    int bit  = Integer.parseInt(jsonObject.optString("bit").toString());
                    int value  = Integer.parseInt(jsonObject.optString("value").toString());

                    EventObject eo = new EventObject();
                    eo.SetDate(time);
                    eo.SetIP(ip);
                    eo.SetDev(device);
                    eo.SetBit(bit);
                    eo.SetValue(value);
                    arrayList.add(eo);

                }
                EventAdapter adapter = new EventAdapter(getActivity(),arrayList);
                listView.setAdapter(adapter);

                swipeLayout.setRefreshing(false);
            } catch (JSONException e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

 }
