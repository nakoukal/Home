package com.nakoukal.radek.home;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by uidv7359 on 25.8.2016.
 */
public class TempFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout swipeLayout;
    ListView listView;
    ArrayList<TempObject> arrayList;
    private String host,port,name,user,pass;

    public static TempFragment newInstance() {
        TempFragment fragment = new TempFragment();
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.temp_fragment, container, false);
        String path = TempFragment.this.getActivity().getFilesDir().getAbsolutePath();
        Config cfg = new Config(path);
        if (cfg.load()) {
            this.host = cfg.get("host");
            this.port = cfg.get("port");
            this.name = cfg.get("name");
            this.user = cfg.get("user");
            this.pass = cfg.get("pass");
        }
        listView = (ListView) rootView.findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // send object to new activity over intent
                Intent intent = new Intent(getContext(), TempDetailActivity.class);
                //add object
                intent.putExtra("temp", arrayList.get(position));

                startActivity(intent);
            }
        });


        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        swipeLayout.setOnRefreshListener(this);
        try {
            AsyncActivity req = new AsyncActivity();
            req.setUrl(new URL("http://"+host+":"+port+"/smarthome/actualTemp2.php"));
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
            req.setUrl(new URL("http://"+host+":"+port+"/smarthome/actualTemp2.php"));
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
                String login  = user+":"+pass;
                String basicAuth = "Basic " + new String(Base64.encode(login.getBytes(), Base64.NO_WRAP));
                HttpURLConnection conn = (HttpURLConnection) this.url.openConnection();
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
                JSONArray jsonArray = jsonRootObject.optJSONArray("temp");
                arrayList = new ArrayList(jsonArray.length());
                for(int i=0;i < jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.optString("name").toString();
                    Double act = Double.parseDouble(jsonObject.optString("act").toString());
                    String req  = jsonObject.optString("req").toString();
                    String sens  = jsonObject.optString("sens").toString();

                    TempObject to = new TempObject(name,act,req,sens);
                    arrayList.add(to);
                }
                TempAdapter adapter = new TempAdapter(getActivity(),arrayList);
                listView.setAdapter(adapter);

                swipeLayout.setRefreshing(false);
            } catch (JSONException e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

}
