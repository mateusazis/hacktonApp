package br.uff.hacktonapp;

import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

public class ConfigScreen extends Activity implements OnItemSelectedListener, FetchCallback {

	private Spinner spinner;
	private ProgressDialog pd;
	private NeighHelper h;
	private TextView leftStar, rightStar, levelPctg;
	private ProgressBar levelProgress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config_layout);
		spinner = (Spinner)findViewById(R.id.spinner1);
		leftStar = (TextView)findViewById(R.id.leftStarText);
		rightStar = (TextView)findViewById(R.id.rightStarText);
		levelPctg = (TextView)findViewById(R.id.percentageText);
		levelProgress = (ProgressBar)findViewById(R.id.percentageBar);
		
		
		h = NeighHelper.getInstance(this);
		spinner.setAdapter(h.makeAdapter(this));
//		spinner.setOnItemSelectedListener(this);
		leftStar.setText(MainScreen.userLevelNumber + "");
		rightStar.setText((MainScreen.userLevelNumber+1) + "");
		levelProgress.setMax(100);
		float newPctg = MainScreen.userRelativeXP * 100;
		levelProgress.setProgress((int)newPctg);
		levelPctg.setText(String.format("%.01f", newPctg) + "%");
		requestNeighboorhood();
	}
	
	private void requestNeighboorhood(){
		pd = ProgressDialog.show(this, "Aguarde", "Buscando suas informações...");
		FetchTask task = FetchTask.getNeighborhoodTask(MainScreen.meUser.getId(), this);
		task.execute();
	}
	
	public void submit(View v){
		pd = ProgressDialog.show(this, "Aguarde", "Salvando suas informações...");
		int selectedIndex = spinner.getSelectedItemPosition();
		int id = h.getID(selectedIndex);
		String fbId = MainScreen.meUser.getId();
		FetchTask task = FetchTask.setNeighborhoodTask(fbId, ""+id, new SubmissionCallback());
		task.execute();
	}
	
	public void cancel(View v){
		finish();
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
			long arg3) {
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}

	@Override
	public void onResult(boolean success, JSONObject response) {
		//neighborhood retrieved
		
		try {
			Log.d("", "response: " + response);
			int status = response.getInt("status");
			if(success && status != 2){
				int neighborhoodID = response.getInt("neighborhood");
				String neighborhoodName = h.getOriginals()[neighborhoodID-1];
				String [] options = h.getSorted();
				int index = Arrays.binarySearch(options, neighborhoodName);
				spinner.setSelection(index);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		pd.dismiss();
	}
	
	private class SubmissionCallback implements FetchCallback{

		@Override
		public void onResult(boolean success, JSONObject response) {
			pd.dismiss();
			finish();
			
		}
		
	}
	
}
