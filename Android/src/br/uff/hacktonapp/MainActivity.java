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
		
		if(isOpen(s))
			loadMainScreen();
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
//		
		button.setReadPermissions(permissions);
		button.setSessionStatusCallback(this);
//		s.addCallback(this);

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
//		i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
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
			button.setEnabled(false);
//			Log.d("", "logged");
//			loadMainScreen();
		} else{
			Log.d("", "session: " + session);
			Log.d("", "open? " + session.isOpened());
			
		}
		
	}
	
	private void requestUserData(){
		setVisible(R.id.loader1, true);
		setVisible(R.id.loaderText, true);
		
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
		MainScreen.meUser = user;
		loadMainScreen();
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

}
