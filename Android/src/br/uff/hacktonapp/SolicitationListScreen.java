package br.uff.hacktonapp;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.mitosoft.ui.widgets.NoDefaultSpinner;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class SolicitationListScreen extends Activity implements FetchCallback, OnItemSelectedListener {

	/*
	private TextView neighborhoodName;
	private ListView rankingList;
	private Spinner criteriaSpinner;
	*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.solicitation_list_layout);
		/*
		rankingList = (ListView)findViewById(R.id.rankingList);
		criteriaSpinner = (Spinner)findViewById(R.id.criteriaSpinner);
		
		neighborhoodName = (TextView)findViewById(R.id.neighborhoodName);
		criteriaSpinner.setOnItemSelectedListener(this);
		*/
	}
	
	private void showNeighborhoodDialog(){
		/*
		AlertDialog.Builder b = new AlertDialog.Builder(this);
		final NeighHelper h = NeighHelper.getInstance(this);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, h.getSorted());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		b.setAdapter(adapter, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int pos) {
				String name = h.getName(pos);
				neighborhoodName.setText(name);
				int id = h.getID(name);
				
				Log.d("", "neighborhood id is " + id);
				FetchTask getRank = FetchTask.neighborhoodRankTask(id, SolicitationListScreen.this);
				getRank.execute();
			}
		});
		
		b.create().show();
		*/
	}
	
	public void setRanking(RankedUser [] objects){
		/*
		RankAdapter adapter = new RankAdapter(this, R.layout.ranking_item, objects);
		rankingList.setAdapter(adapter);
		*/
	}

	@Override
	public void onResult(boolean success, JSONObject response) {
		/*
		Log.d("", "response: " + response);
		if(success){
			try {
				List<RankedUser> users = new LinkedList<RankedUser>();
				JSONArray userArray = response.getJSONArray("users");
				for(int i = 0; i < userArray.length(); i++){
					JSONObject obj = userArray.getJSONObject(i);
					String id = obj.getString("facebook");
					int levelNumber = obj.getInt("level");
					String levelName = obj.getString("level_name");
					String name = obj.getString("name");
					
					int position = i + 1;
					RankedUser newUser = new RankedUser(name, position, id, levelNumber, levelName);
					users.add(newUser);
				}
				setRanking(users.toArray(new RankedUser[0]));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		/*
		//pos: 0 = friends, 1 = overall, 2 = neighborhood 
		FetchTask getRank = null;
		rankingList.setAdapter(null);
		LinearLayout neighLayout = (LinearLayout)findViewById(R.id.problemLayout);
		neighLayout.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
		if(position == 2)
			neighborhoodName.setText("Nenhum");
		switch(position){
		case 0:
			String userID = MainScreen.meUser.getId();
			String accessToken = Session.getActiveSession().getAccessToken();
			getRank = FetchTask.friendsRankTask(userID, accessToken, 10, this);
			break;
		case 1:
			getRank = FetchTask.generalRankTask(10, this);
			break;
		case 2:
			showNeighborhoodDialog();
			return;
		}
		getRank.execute();
		*/
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		/*
		Log.d("", "on nothing");
		*/
	}

	
}
