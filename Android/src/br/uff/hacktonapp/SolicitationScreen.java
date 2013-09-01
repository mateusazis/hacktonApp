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
		setFacebookRequestName("conserto de via pública", "um");
		loadInfoScreen();
	}
	
	public void makeLightRequest(View v){
		setFacebookRequestName("reparo de iluminação pública", "um");
		loadInfoScreen();
	}

	public void makeParkingRequest(View v){
		setFacebookRequestName("solicitação de estacionamento irregular", "uma");
		loadInfoScreen();
	}
	
	public void makeTreeRequest(View v){
		setFacebookRequestName("poda de árvore", "uma");
		loadInfoScreen();
	}
	
	public static void setFacebookRequestName(String name, String pronoum){
		SolicitationResultScreen.setSolicitationName(name, pronoum);
	}
	
	public void loadInfoScreen(){
		Intent i = new Intent(this, SolicitationInfoScreen.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(i);
		SlideTransition.forwardTransition(this);
		
	}
	
}
