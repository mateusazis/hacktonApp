package br.uff.hacktonapp;

import android.app.*;
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
		
	}
	
	public void makeLightRequest(View v){
		
	}

	public void makeParkingRequest(View v){
		
	}
	
	public void makeTreeRequest(View v){
		
	}
	
	
}