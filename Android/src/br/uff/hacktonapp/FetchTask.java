package br.uff.hacktonapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class FetchTask extends AsyncTask<Void, Void, JSONObject> {
	
	private String absoluteURL;
	private String strParams[];
	private FetchCallback callback;
	
	public FetchTask(String relativeURL, FetchCallback callback, String...params){
		this.absoluteURL = "http://hackathon1746.herokuapp.com/" + relativeURL;
		this.strParams = params;
		this.callback = callback;
	}
	
	public static FetchTask connectTask(String name, String email, String phone, String facebook, FetchCallback callback){
		return new FetchTask("users/connect.json", callback, 
				"user[name]", name,
				"user[email]", email, 
				"user[phone]", phone, 
				"user[facebook]", facebook				
		);
	}
	
	
    @Override
    protected JSONObject doInBackground(Void... params) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(absoluteURL);

            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            
            for(int i = 0; i < strParams.length; i+=2){
            	BasicNameValuePair pair = new BasicNameValuePair(strParams[i], strParams[i+1]);
            	nameValuePairs.add(pair);
            	
            }
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            sb.append(reader.readLine() + "\n");
            String line = "0";
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            reader.close();
            String result11 = sb.toString();

            // parsing data
            return new JSONObject(result11);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        if(callback != null)
        	callback.onResult(result != null, result);
    }
}