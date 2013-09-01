package br.uff.hacktonapp;

import java.io.IOException;
import java.util.List;

//import com.google.android.gms.common.*;
//import com.google.android.gms.common.GooglePlayServicesClient.*;
//import com.google.android.gms.location.LocationClient;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SolicitationInfoScreen extends Activity {

	private ProgressBar addressBar;
	private Button useButton;
	private TextView addressField;
	private static String suggestedAddress = null;
	
//	private LocationClient client;
	
	public static void setSuggestedAddress(String value){
		suggestedAddress = value;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_layout);
		addressField = (TextView)findViewById(R.id.addressField);
		useButton = (Button)findViewById(R.id.localizationButton);
		addressBar = (ProgressBar)findViewById(R.id.localizationBar);
		
//		LocationListener listener = new LocationListener();
//		client = new LocationClient(this, listener, listener);
	}
	
//	@Override
//	protected void onStart() {
//		super.onStart();
//		client.connect();
//	}
//	
//	@Override
//	protected void onStop() {
//		super.onStop();
//		client.disconnect();
//	}
	
	public void setFoundAddress(String address){
		addressBar.setVisibility(View.INVISIBLE);
		useButton.setEnabled(true);
		if(isEmpty(addressField)){
			addressField.setText(suggestedAddress);
		}
	}
	
	private static boolean isEmpty(TextView tv){
		return tv.getText().toString().length() == 0;
	}
	
	public void useSuggestedLocalization(View v){
		
	}
	
	public void send(View v){
		Intent i = new Intent(this, SolicitationResultScreen.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(i);
		SlideTransition.forwardTransition(this);
	}
	
	
//	public class LocationListener implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
//
//		@Override
//		public void onConnectionFailed(ConnectionResult arg0) {
//			Log.d("", "failed");
//			
//		}
//
//		@Override
//		public void onConnected(Bundle arg0) {
//			Log.d("", "connected");
//			Location l = client.getLastLocation();
//			if(l != null){
//				Geocoder coder = new Geocoder(SolicitationInfoScreen.this);
//				try {
//					List<Address> addresses = coder.getFromLocation(l.getLatitude(), l.getLongitude(), 100);
//					for(Address add : addresses){
//						Log.d("", "address: " + add.toString());
//					}
//					
//				} catch (IOException e) {
//					Log.d("", "geocoder error");
//				}
//			} else 
//				Log.d("", "location is null!");
//			
//		}
//
//		@Override
//		public void onDisconnected() {
//			Log.d("", "disconnected");
//			
//		}
		
//	}
}
