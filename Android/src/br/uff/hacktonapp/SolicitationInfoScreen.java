package br.uff.hacktonapp;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

//import com.google.android.gms.common.*;
//import com.google.android.gms.common.GooglePlayServicesClient.*;
//import com.google.android.gms.location.LocationClient;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SolicitationInfoScreen extends Activity implements OnItemSelectedListener, FetchCallback {

	private ProgressBar addressBar;
	private Button useButton;
	private EditText streetField, numberField, commentsField, referenceField;
	private Spinner neighborhoodSpinner;
	private ProgressDialog pd;
	
	
	private static String suggestedAddress = null;
	public static RequestType requestType;
	
//	private LocationClient client;
	
	public static void setSuggestedAddress(String value){
		suggestedAddress = value;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_layout);
		streetField = (EditText)findViewById(R.id.streetField);
		numberField = (EditText)findViewById(R.id.addressNumber);
		commentsField = (EditText)findViewById(R.id.comments);
		referenceField = (EditText)findViewById(R.id.refField);
		useButton = (Button)findViewById(R.id.localizationButton);
		addressBar = (ProgressBar)findViewById(R.id.localizationBar);
		neighborhoodSpinner = (Spinner)findViewById(R.id.neighborhoodSpinner);
		
		
		
//		originalNeighborhoods = getNeighborHoods(this);
//		System.arraycopy(sortedNeighborhoods, 0, originalNeighborhoods, 0, originalNeighborhoods.length);
//		
//		Arrays.sort(sortedNeighborhoods);
//		sortedNeighborhoods = getSortedNeighborHoods(originalNeighborhoods);
		NeighHelper h = NeighHelper.getInstance(this);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, h.getSorted());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		neighborhoodSpinner.setAdapter(adapter);
		neighborhoodSpinner.setOnItemSelectedListener(this);
//		LocationListener listener = new LocationListener();
//		client = new LocationClient(this, listener, listener);
	}
	
	
//	public static int getNeighborhoodID(String neighboordHood, String [] originalNeighborhoods){
//		List<String> list = Arrays.asList(originalNeighborhoods);
//		return list.indexOf(neighboordHood) + 1;
//	}
	
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
	
	private static boolean isEmpty(EditText tv){
		return tv.getText().toString().length() == 0;
	}
	
	public void useSuggestedLocalization(View v){
		
	}
	
	private void showToast(String message){
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	
	public void send(View v){
		if(isEmpty(streetField)){
			streetField.requestFocus();
			showToast("Por favor, informe um endereço.");
			
		} else if(isEmpty(numberField)){
			numberField.requestFocus();
			showToast("Por favor, informe um número.");
		} else {
			String facebookID = MainScreen.meUser.getId();
			String street = streetField.getText().toString();
			String number = numberField.getText().toString();
			String description = commentsField.getText().toString();
			String reference = referenceField.getText().toString();
			String neighborhoodID = NeighHelper.getInstance(this).getID(neighborhoodSpinner.getSelectedItemPosition()) + "";
			String code = "10";
			FetchTask r = FetchTask.makeRequestTask(facebookID, street, number, description, reference, neighborhoodID, code, this);
			
			pd = ProgressDialog.show(this, "Enviando", "Aguarde, enviando sua solicitação...");
			r.execute();
		}
	}
	
	public void showResult(){
		Intent i = new Intent(this, SolicitationResultScreen.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(i);
		SlideTransition.forwardTransition(this);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
		NeighHelper h = NeighHelper.getInstance(this);
		int index = h.getID(position);
//		Log.d("", "index of " + sortedNeighborhoods[position] + " is " + index);
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResult(boolean success, JSONObject response) {
		Log.d("", "solicitation response: " + response);
		pd.dismiss();
		if(success){
			try {
				String protocol = response.getString("protocol_code");
				SolicitationResultScreen.protocol = protocol;
			} catch (JSONException e) {
				e.printStackTrace();
			}
			showResult();
		}
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
