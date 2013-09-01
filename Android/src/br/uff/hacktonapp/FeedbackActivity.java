package br.uff.hacktonapp;


import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class FeedbackActivity extends Activity implements FetchCallback {

	private String requestID, protocols[];
	private TextView protocol;
	private EditText comments;
	private RatingBar bar;
	private ProgressDialog pd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback_layout);
		requestID = getIntent().getExtras().getString("request_id");
		protocols = getIntent().getExtras().getStringArray("protocols");
		
		
		protocol = (TextView)findViewById(R.id.protocol);
		comments = (EditText)findViewById(R.id.comments);
		bar = (RatingBar)findViewById(R.id.ratingBar1);
		
		protocol.setText("");
		for(int i = 0; i < protocols.length; i++){
			protocol.append(protocols[i]);
			if(i < protocols.length -2)
				protocol.append(", ");
			else if(i < protocols.length - 1)
				protocol.append(" e ");
		}
	}
	
	public void send(View v){
		String commentsStr = comments.getText().toString();
		float rating = 2f*bar.getRating();
		String userID = MainScreen.meUser.getId();
		
		FetchTask ft = FetchTask.sendFeedback(userID, rating, commentsStr, requestID, this);
		ft.execute();
		pd = ProgressDialog.show(this, "Aguarde...", "Enviando seus comentários. Obrigado pela participação!");
	}

	@Override
	public void onResult(boolean success, JSONObject response) {
		Log.d("", "feedback response: " + response);
		pd.dismiss();
		finish();
	}
	
}
