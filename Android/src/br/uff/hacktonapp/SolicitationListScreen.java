package br.uff.hacktonapp;


import org.json.JSONObject;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class SolicitationListScreen extends Activity implements FetchCallback {

	private ListView solicitationList;
	private Solicitation [] sols;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.solicitation_list_layout);
		solicitationList = (ListView)findViewById(R.id.solicitationList);
		
		FetchTask t = FetchTask.getNotifications(null, this);
		t.execute();
	}

	@Override
	public void onResult(boolean success, JSONObject response) {
		
		
		
		SolicitationAdapter adp = new SolicitationAdapter(this, R.layout.layout_solicitation_item, sols);
	}

	
}
