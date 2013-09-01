package br.uff.hacktonapp;

import android.app.*;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class SolicitationScreen extends Activity implements OnItemSelectedListener{

	private ImageView picturePreview;
	public static Bitmap pictureBitmap;
	private LinearLayout problemLayout;
	private ListView problemList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.solicitation_layout);
		
//		picturePreview = (ImageView)findViewById(R.id.solicitationPreview);
//		if(pictureBitmap != null)
//			picturePreview.setImageBitmap(pictureBitmap);
		
		problemLayout = (LinearLayout)findViewById(R.id.problemLayout);
		problemLayout.setVisibility(View.INVISIBLE);
		problemList = (ListView)findViewById(R.id.problemList);
		problemList.setOnItemSelectedListener(this);
	}
	
	public void setProblems(UrbanProblem [] problems){
		ProblemAdapter adapter = new ProblemAdapter(this, R.layout.layout_problem, problems);
		problemList.setAdapter(adapter);
		problemLayout.setVisibility(View.VISIBLE);
	}
	
	public void makeStreetRequest(View v){
		UrbanProblem [] p = { 
			new UrbanProblem(RequestType.STREET, 0, "rua esburacada"),
			new UrbanProblem(RequestType.STREET, 1, "rua tensa"),
			new UrbanProblem(RequestType.STREET, 2, "rua mais tensa"),
		};
		setProblems(p);
		setFacebookRequestName("conserto de via pública", "um");
		SolicitationInfoScreen.requestType = RequestType.STREET;
		loadInfoScreen();
	}
	
	public void makeLightRequest(View v){
		UrbanProblem [] p = { 
				new UrbanProblem(RequestType.LIGTH, 0, "rua esburacada"),
				new UrbanProblem(RequestType.LIGTH, 1, "rua tensa"),
				new UrbanProblem(RequestType.LIGTH, 2, "rua mais tensa"),
			};
			setProblems(p);
		setFacebookRequestName("reparo de iluminação pública", "um");
		SolicitationInfoScreen.requestType = RequestType.LIGTH;
		loadInfoScreen();
	}

	public void makeParkingRequest(View v){
		UrbanProblem [] p = { 
				new UrbanProblem(RequestType.PARK, 0, "rua esburacada"),
				new UrbanProblem(RequestType.PARK, 1, "rua tensa"),
				new UrbanProblem(RequestType.PARK, 2, "rua mais tensa"),
			};
			setProblems(p);
		setFacebookRequestName("solicitação de estacionamento irregular", "uma");
		SolicitationInfoScreen.requestType = RequestType.PARK;
		loadInfoScreen();
	}
	
	public void makeTreeRequest(View v){
		UrbanProblem [] p = { 
				new UrbanProblem(RequestType.TREE, 0, "rua esburacada"),
				new UrbanProblem(RequestType.TREE, 1, "rua tensa"),
				new UrbanProblem(RequestType.TREE, 2, "rua mais tensa"),
			};
			setProblems(p);
		setFacebookRequestName("poda de árvore", "uma");
		SolicitationInfoScreen.requestType = RequestType.TREE;
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

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		UrbanProblem p = (UrbanProblem) problemList.getItemAtPosition(position);
		SolicitationInfoScreen.problem = p;
		loadInfoScreen();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
