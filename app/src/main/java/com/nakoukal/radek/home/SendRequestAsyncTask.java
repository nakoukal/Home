package com.nakoukal.radek.home;

/**
 * Created by uidv7359 on 20.03.2017.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class SendRequestAsyncTask extends AsyncTask<String, String, String> {
    private TaskCompleted m_task_completed;
    private ProgressDialog mProgress;
    private Context m_activity_context;
    private URL m_url;
    private String m_user;
    private String m_pass;

    public SendRequestAsyncTask(Context context,TaskCompleted activityContext){
        m_activity_context = context;
        m_task_completed = activityContext;
    }

    public void SetUrl(URL url)
    {
        m_url = url;
    }

    public void SetUser(String user)
    {
        m_user = user;
    }

    public void SetPass(String pass)
    {
        m_pass = pass;
    }


    @Override
    public void onPreExecute() {
        mProgress = new ProgressDialog(m_activity_context);
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

        Integer iResult = 0;

        try {
            String login  = m_user+":"+m_pass;
            String basicAuth = "Basic " + new String(Base64.encode(login.getBytes(), Base64.NO_WRAP));
            HttpURLConnection conn = (HttpURLConnection) m_url.openConnection();
            conn.setRequestProperty("Authorization", basicAuth);
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

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
    protected void onPostExecute(String unused)
    {
        mProgress.dismiss();
        m_task_completed.onTaskComplete(unused);
    }

}
