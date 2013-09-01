package br.uff.hacktonapp;

import java.io.IOException;
import java.net.URL;

import com.facebook.model.GraphUser;

import android.app.*;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
		if(meUser != null){
			nameView.setText(meUser.getName());
			setUserPicture(pictureView, meUser.getId());
		}
	}
	
	public static void setUserPicture(ImageView view, String userID){
		URL img_value = null;
		try {
			img_value = new URL("http://graph.facebook.com/"+userID+"/picture?type=large ");
			Bitmap mIcon1 = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());
			view.setImageBitmap(mIcon1);
		} catch (Exception e) {
			Log.e("", e.getMessage());
		}
	}
	
	public static void setUserPictureAsync(final ImageView view, String userID, final Activity act){
		try {
			final URL img_value = new URL("http://graph.facebook.com/"+userID+"/picture?type=large ");
			new Thread(new Runnable(){
				@Override
				public void run() {
					try {
						final Bitmap mIcon1 = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());
						act.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							view.setImageBitmap(mIcon1);
							
						}
					});
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}).start();
			
			
		} catch (Exception e) {
			Log.e("", e.getMessage());
		}
	}
	
	public void makeSolicitation(View v){ 
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		// 2. Chain together various setter methods to set the dialog characteristics
		builder.setMessage("Deseja incluir uma foto?\n\nVocê pode compartilhá-la mais tarde com seus amigos!").setTitle("Foto");
		builder.setPositiveButton("OK", new AddPictureListener(true));
		builder.setNegativeButton("Não agora", new AddPictureListener(false));
		
		
//		ArrayAdapter<String> adpt = new ArrayAdapter<String>(this, R.layout.list_item, R.id.textView1, new String[]{"1", "2"});
		// 3. Get the AlertDialog from create()
//		builder.setAdapter(adpt, new AddPictureListener(true));
//		builder.setCancelable(false);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	
	public void viewRanking(View v){
		Intent i = new Intent(this, Ranking.class);
		startActivity(i);
	}

	public void viewInfo(View v){
		Intent i = new Intent(this, ConfigScreen.class);
		startActivity(i);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			Bundle extras = data.getExtras();
		    SolicitationScreen.pictureBitmap = (Bitmap) extras.get("data");
			
			Intent i = new Intent(this, SolicitationScreen.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			startActivity(i);
			SlideTransition.forwardTransition(this);
		} else{
			Toast.makeText(this, "Nenhuma foto tirada.", Toast.LENGTH_SHORT).show();
		}
	}
	
	private class AddPictureListener implements OnClickListener{
		private boolean shouldAdd;
		
		public AddPictureListener(boolean shouldAdd){
			this.shouldAdd = shouldAdd;
		}
		
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			if(this.shouldAdd){
				Log.d("", "add picture");
				Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			    startActivityForResult(takePictureIntent, 0);
			}else{
				Log.d("", "skip picture");
			}
			
		}
		
	}
}
