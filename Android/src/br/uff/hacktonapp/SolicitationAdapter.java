package br.uff.hacktonapp;



import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SolicitationAdapter extends ArrayAdapter<Solicitation>{

	private Activity act;
	private Solicitation[] objects;
	private int layoutID;
	
	public SolicitationAdapter(Activity a, int textViewResourceId, Solicitation[] objects) {
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
        TextView protocol = (TextView)row.findViewById(R.id.protocol);
        ImageView pictureView = (ImageView)row.findViewById(R.id.problemImage);
        
        
        
        Solicitation b = objects[position];
        nameView.setText(Html.fromHtml(b.description));
        pictureView.setImageResource(b.drawableID);
        protocol.setText(b.protocol);

        return row;
	}
	
}