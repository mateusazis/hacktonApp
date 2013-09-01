package br.uff.hacktonapp;

import com.facebook.Request;
import com.facebook.Request.Callback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SolicitationResultScreen extends Activity implements Callback {

//	private ImageView picture;
	private TextView xpText;
	private ProgressDialog progressDialog;
	public static String solicitationName;
	private static String solicitationPronoum;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.solicitation_result);
//		picture = (ImageView)findViewById(R.id.pictureView);
		xpText = (TextView)findViewById(R.id.xpText);
		
		
		
		
		
		setup();
	}
	
	public void setup(){
//		Animation rotation = AnimationUtils.loadAnimation(this, R.anim.img_rotation);
//		picture.startAnimation(rotation);
//		
//		if(SolicitationScreen.pictureBitmap != null)
//			picture.setImageBitmap(SolicitationScreen.pictureBitmap);
	}
	
	public void setEarnedXP(int value){
		xpText.setText("Você ganhou +" + value + " pontos!");
	}
	
	public void share(View v){
		Bitmap b = SolicitationScreen.pictureBitmap;
		if(b != null){
			Session s = Session.getActiveSession();
			Request r;
			r = Request.newUploadPhotoRequest(s, b, this);
			Bundle params = r.getParameters();
			
			
			String message = String.format("Eu solicitei %s %s ao 1746! Ajude também e veja quem é o cidadão mais ativo! Baixe o aplicativo gratuitamente em http://bit.ly/abcde", solicitationPronoum, solicitationName);
			params.putString("message", message);
			
			progressDialog = ProgressDialog.show(this, "Compartilhando", "Aguarde enquanto a foto é enviada...");
			
			r.executeAsync();
		}
	}
	
	public void goBack(View v){
		finish();
		SlideTransition.backTransition(this);
	}

	@Override
	public void onCompleted(Response response) {
		progressDialog.dismiss();
		GraphObject obj = response.getGraphObject();
		Log.d("", "response: " + obj.getInnerJSONObject());
		
		String txt = "Publicado com sucesso!";
		Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
		goBack(null);
	}

	public static void setSolicitationName(String name, String pronoum) {
		solicitationName = name;
		solicitationPronoum = pronoum;
		
	}
	
}
