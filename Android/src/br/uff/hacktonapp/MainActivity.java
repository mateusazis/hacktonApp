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
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import org.json.*;

import com.loopj.android.http.*;

public class MainActivity extends Activity implements StatusCallback, GraphUserCallback {

	private LoginButton button;
	private TextView loaderText;
	private boolean skipExtraData = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button = (LoginButton)findViewById(R.id.loginButton1);
		loaderText = (TextView)findViewById(R.id.loaderText);
		
		Session s = Session.getActiveSession();
//		if(s != null){
//			s.closeAndClearTokenInformation();
//			Log.d("", "closed and cleared token info");
//		}
		
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
		
		List<String> permissions = Arrays.asList("email", "photo_upload");
		
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
		
		if(session != null && session.isOpened()){
			Log.d("", "logged");
			List<String> perms = session.getPermissions();
			String str = "My permissions: ";
			for(String s : perms)
				str += s + ",";
			Log.d("", str);
			requestUserData();
		} else{
		}
		
	}
	
	private void requestUserData(){
		button.setEnabled(false);
		setVisible(R.id.loader1, true);
		setVisible(R.id.loaderText, true);
		setVisible(R.id.loginButton1, false);
		setVisible(R.id.rankNumber, false);
		loaderText.setText("Buscando dados do Facebook...");
		
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
		
		if(skipExtraData)
			on1746UserRetrieved(null);
		else{
			
			Request r = new Request(Session.getActiveSession(), "/me?fields=email");
			
			r.setCallback(new Request.Callback() {
				@Override
				public void onCompleted(Response response) {
					//e-mail retrieved
					GraphObject obj = response.getGraphObject();
					Log.d("", "response: " + response.toString());
					String email = "teste@teste.com";
					if(obj != null){
						email = (String)obj.getProperty("email");
						Log.d("", "email: " + email);
					}
					
					FetchTask task = FetchTask.connectTask("mateus", "a@b.c", "222", "123123", new FetchCallback() {
	//					
						@Override
						public void onResult(boolean success, JSONObject response) {
							if(success)
								on1746UserRetrieved(response);
						}
					});
					loaderText.setText("Buscando cadastro no 1746...");
					task.execute();
				}
			});
			loaderText.setText("Buscando e-mail do Facebook...");
			r.executeAsync();
		}
	}
	
	public void on1746UserRetrieved(JSONObject user){
		loadMainScreen();
	}

}
