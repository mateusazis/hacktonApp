package br.uff.hacktonapp;

import android.app.*;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SolicitationScreen extends Activity{

	private ImageView picturePreview;
	public static Bitmap pictureBitmap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.solicitation_layout);
		
		picturePreview = (ImageView)findViewById(R.id.solicitationPreview);
		if(pictureBitmap != null)
			picturePreview.setImageBitmap(pictureBitmap);
		
	}
	
	public void makeStreetRequest(View v){
		loadInfoScreen();
	}
	
	public void makeLightRequest(View v){
		loadInfoScreen();
	}

	public void makeParkingRequest(View v){
		loadInfoScreen();
	}
	
	public void makeTreeRequest(View v){
		loadInfoScreen();
	}
	
	public void loadInfoScreen(){
		Intent i = new Intent(this, SolicitationInfoScreen.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(i);
		SlideTransition.forwardTransition(this);
		
	}
	
}
