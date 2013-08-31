package br.uff.hacktonapp;

import java.net.URL;

import com.facebook.model.GraphUser;

import android.app.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class MainScreen extends Activity{

	public static GraphUser meUser;
	private TextView nameView;
	private ImageView pictureView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);
		
		nameView = (TextView)findViewById(R.id.profileName);
		pictureView = (ImageView)findViewById(R.id.profileImage);
		
		setup();
	}
	
	private void setup(){
		nameView.setText(meUser.getName());
		setUserPicture(pictureView, meUser.getId());
	}
	
	private void setUserPicture(ImageView view, String userID){
		URL img_value = null;
		try {
			img_value = new URL("http://graph.facebook.com/"+userID+"/picture?type=large ");
			Bitmap mIcon1 = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());
			view.setImageBitmap(mIcon1);
		} catch (Exception e) {
			Log.e("", e.getMessage());
		}
	}
	
}
