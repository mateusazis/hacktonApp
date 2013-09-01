package br.uff.hacktonapp;



import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProblemAdapter extends ArrayAdapter<UrbanProblem>{

	private Activity act;
	private UrbanProblem[] objects;
	private int layoutID;
	
	public ProblemAdapter(Activity a, int textViewResourceId, UrbanProblem[] objects) {
		super(a.getBaseContext(), textViewResourceId, objects);
		act = a;
		this.objects = objects;
		this.layoutID = textViewResourceId;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
View row = convertView;
        
        if(row == null)
        {
            LayoutInflater inflater = LayoutInflater.from(act.getApplicationContext());
            row = inflater.inflate(layoutID, parent, false);
        }
        TextView nameView = (TextView)row.findViewById(R.id.problemName);
        ImageView pictureView = (ImageView)row.findViewById(R.id.problemImage);
        
        UrbanProblem b = objects[position];
        nameView.setText(Html.fromHtml(b.description));
        pictureView.setImageResource(b.getDrawableResource());

        return row;
	}
	
}