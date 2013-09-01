package br.uff.hacktonapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
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
	private boolean isGet;
	
	public FetchTask(String relativeURL, FetchCallback callback, boolean isGet, String...params){
		this.absoluteURL = "http://hackathon1746.herokuapp.com/" + relativeURL;
		this.strParams = params;
		this.callback = callback;
		this.isGet = isGet;
	}
	
	public static FetchTask connectTask(String name, String email, String phone, String facebook, FetchCallback callback){
		return new FetchTask("users/connect.json", callback, false,
				"user[name]", name,
				"user[email]", email, 
				"user[phone]", phone, 
				"user[facebook]", facebook				
		);
	}
	
	public static FetchTask generalRankTask(int limit, FetchCallback callback){
		return new FetchTask("ranking/overall.json", callback, true);
	}
	
	public static FetchTask friendsRankTask(String facebookID, String accessToken, int limit, FetchCallback callback){
		return new FetchTask("ranking/friend.json", callback, false,
				"access_token", accessToken,
				"facebook", facebookID);
	}
	
	public static FetchTask makeRequestTask(String facebookID, String street, String number, String description, String reference, String neighborhoodID, String code, FetchCallback callback){
		return new FetchTask("requests/create.json", callback, false,
				"address", street,
				"number", number,
				"description", description,
				"reference", reference,
				"neighborhood", neighborhoodID,
				"type", code,
				"facebook", facebookID);
	}
	
	public static FetchTask neighborhoodRankTask(int id, FetchCallback callback) {
		return new FetchTask("ranking/neighborhood.json?neighborhood="+id, callback, true);
	}
	
	public static FetchTask getNeighborhoodTask(String id, FetchCallback callback) {
		return new FetchTask("users/neighborhood.json?facebook="+id, callback, true);
	}
	
	public static FetchTask setNeighborhoodTask(String fbId, String id, FetchCallback callback) {
		return new FetchTask("users/neighborhood.json", callback, false,
				"user[facebook]", fbId,
				"user[neighborhood_id]", id);
	}
	
    @Override
    protected JSONObject doInBackground(Void... params) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpUriRequest httppost = isGet ? new HttpGet(absoluteURL) : new HttpPost(absoluteURL);

            // Add your data/
            
            if(!isGet){
            	HttpPost post = (HttpPost)httppost;
	            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	            
	            for(int i = 0; i < strParams.length; i+=2){
	            	BasicNameValuePair pair = new BasicNameValuePair(strParams[i], strParams[i+1]);
	            	nameValuePairs.add(pair);
	            	
	            }
	            post.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            
            }
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
            Log.d("", "result json: " + result11);
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