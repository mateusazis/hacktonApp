package br.uff.hacktonapp;

import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

public class ConfigScreen extends Activity implements OnItemSelectedListener, FetchCallback {

	private Spinner spinner;
	private ProgressDialog pd;
	private NeighHelper h;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config_layout);
		spinner = (Spinner)findViewById(R.id.neighborhoodSpinner);

		h = NeighHelper.getInstance(this);
		spinner.setAdapter(h.makeAdapter(this));
//		spinner.setOnItemSelectedListener(this);
		
		requestNeighboorhood();
	}
	
	private void requestNeighboorhood(){
		pd = ProgressDialog.show(this, "Aguarde", "Buscando suas informações...");
		FetchTask task = FetchTask.neighborhoodRankTask(2, this);
		task.execute();
	}
	
	public void submit(View v){
		pd = ProgressDialog.show(this, "Aguarde", "Salvando suas informações...");
		FetchTask task = FetchTask.neighborhoodRankTask(2, new SubmissionCallback());
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
			String neighborhoodName = response.getString("");
			String [] options = h.getSorted();
			int index = Arrays.binarySearch(options, neighborhoodName);
			spinner.setSelection(index);
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
