package br.uff.hacktonapp;

import java.util.Arrays;
import java.util.List;

import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.internal.SessionTracker;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import org.json.*;
import com.loopj.android.http.*;

public class MainActivity extends Activity implements StatusCallback, GraphUserCallback {

	private LoginButton button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button = (LoginButton)findViewById(R.id.loginButton1);
		
		Session s = Session.getActiveSession();
		if(s != null)
			s.closeAndClearTokenInformation();
		
//		if(isOpen(s))
//			loadMainScreen();
//		else
		if(isOpen(s))
			requestUserData();
		else
			setupLogin();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session s = Session.getActiveSession();	
		s.onActivityResult(this, requestCode, resultCode, data);
	}
	
	private void setupLogin(){
		Log.d("", "setup login");
		
		List<String> permissions = Arrays.asList("email");
		
		button.setReadPermissions(permissions);
		button.setSessionStatusCallback(this);

		setVisible(R.id.loader1, false);
		setVisible(R.id.loaderText, false);
	}
	
	private void setVisible(int viewId, boolean visible){
		View v = findViewById(viewId);
		int visibilityCode = visible ? View.VISIBLE : View.INVISIBLE;
		v.setVisibility(visibilityCode);
	}
	
	private void loadMainScreen(){
		Log.d("", "load main screen");
		Intent i = new Intent(this, MainScreen.class);

		super.getIntent().addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(i);
	}
	
	private boolean isOpen(Session s){
		return s != null && s.isOpened();
	}

	@Override
	public void call(Session session, SessionState state, Exception exception) {
		Log.d("", "called");
		if(session != null && session.isOpened()){
			requestUserData();
//			button.setVisibility(View.INVISIBLE);
			
//			Log.d("", "logged");
//			loadMainScreen();
		} else{
//			Log.d("", "session: " + session);
//			Log.d("", "open? " + session.isOpened());
			
		}
		
	}
	
	private void requestUserData(){
		button.setEnabled(false);
		setVisible(R.id.loader1, true);
		setVisible(R.id.loaderText, true);
		setVisible(R.id.loginButton1, false);
		setVisible(R.id.textView1, false);
		
		Session s = Session.getActiveSession();
		Request r = Request.newMeRequest(s, this);
		r.executeAsync();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Session s = Session.getActiveSession();
		if(s != null)
			s.closeAndClearTokenInformation();
	}

	@Override
	public void onCompleted(GraphUser user, Response response) {
		//user data obtained
		MainScreen.meUser = user;
		
		String id = user.getId();
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("user[name]", user.getName());
		params.put("user[email]", "E-M-A-I-L");
		params.put("user[phone]", "2222222");
		params.put("user[facebook_id]", id);
		
		String url = "http://hackathon1746.herokuapp.com/users/connect.json";
		
		
		client.post(url, new AsyncHttpResponseHandler(){
			
			@Override
			public void onSuccess(String arg0){
				Log.d("", "post response: " + arg0);
			}
			
			@Override
		     public void onFailure(Throwable e, String response) {
		         Log.d("", "post error! " + e.getMessage());
		     }
		});
		
//		url = "http://hackathon1746.herokuapp.com/neighborhoods/list.json";
//		client.get(url, new AsyncHttpResponseHandler(){
//		@Override
//		public void onSuccess(String arg0){
//			Log.d("", "post response: " + arg0);
//		}
//	});
		
		Log.d("", "posting!");
		
		loadMainScreen();
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

}
