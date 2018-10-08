package com.nakoukal.radek.home;

/**
 * Created by uidv7359 on 20.03.2017.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;


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
            HttpsURLConnection conn = (HttpsURLConnection) m_url.openConnection();

            try
            {
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
            }
            catch (NoSuchAlgorithmException e)
            {
                throw new IOException(e);
            } catch (KeyManagementException e)
            {
                throw new IOException(e);
            }


            if (m_user != null)
            {
                String login  = m_user+":"+m_pass;
                String basicAuth = "Basic " + new String(Base64.encode(login.getBytes(), Base64.NO_WRAP));
                conn.setRequestProperty("Authorization", basicAuth);
            }

            conn.setReadTimeout(1000);
            conn.setConnectTimeout(1000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            //conn.setDoOutput(true);
            conn.connect();

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
