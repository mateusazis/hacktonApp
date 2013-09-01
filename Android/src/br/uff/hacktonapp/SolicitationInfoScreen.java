package br.uff.hacktonapp;

import java.io.IOException;
import java.util.Arrays;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

public class SolicitationInfoScreen extends Activity implements OnItemSelectedListener {

	private ProgressBar addressBar;
	private Button useButton;
	private TextView streetField, numberField, commentsField;
	private Spinner neighborhoodSpinner;
	private static String suggestedAddress = null;
	
	private String [] sortedNeighborhoods;
	private String [] originalNeighborhoods;
	
//	private LocationClient client;
	
	public static void setSuggestedAddress(String value){
		suggestedAddress = value;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_layout);
		streetField = (TextView)findViewById(R.id.streetField);
		numberField = (TextView)findViewById(R.id.addressNumber);
		commentsField = (TextView)findViewById(R.id.comments);
		useButton = (Button)findViewById(R.id.localizationButton);
		addressBar = (ProgressBar)findViewById(R.id.localizationBar);
		neighborhoodSpinner = (Spinner)findViewById(R.id.neighborhoodSpinner);
		
		
		
		originalNeighborhoods = getNeighborHoods(this);
//		System.arraycopy(sortedNeighborhoods, 0, originalNeighborhoods, 0, originalNeighborhoods.length);
//		
//		Arrays.sort(sortedNeighborhoods);
		sortedNeighborhoods = getSortedNeighborHoods(originalNeighborhoods);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sortedNeighborhoods);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		neighborhoodSpinner.setAdapter(adapter);
		neighborhoodSpinner.setOnItemSelectedListener(this);
//		LocationListener listener = new LocationListener();
//		client = new LocationClient(this, listener, listener);
	}
	
	public static String [] getNeighborHoods(Activity act){
		return act.getResources().getStringArray(R.array.neighborhoods);
	}
	
	public static String [] getSortedNeighborHoods(String [] originals){
		String resp[] = new String[originals.length];
		System.arraycopy(originals, 0, resp, 0, originals.length);
		
		Arrays.sort(resp);
		return resp;
	}
	
	public static int getNeighborhoodID(String neighboordHood, String [] originalNeighborhoods){
		List<String> list = Arrays.asList(originalNeighborhoods);
		return list.indexOf(neighboordHood) + 1;
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
		if(isEmpty(streetField)){
			streetField.setText(suggestedAddress);
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

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
		int index = getNeighborhoodID(sortedNeighborhoods[position], originalNeighborhoods);
		Log.d("", "index of " + sortedNeighborhoods[position] + " is " + index);
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
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
