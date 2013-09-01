package br.uff.hacktonapp;



import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RankAdapter extends ArrayAdapter<RankedUser>{

	private Activity act;
	private RankedUser[] objects;
	private int layoutID;
//	private AsyncPictureLoader loader;
	
	public RankAdapter(Activity a, int textViewResourceId, RankedUser[] objects) {
		super(a.getBaseContext(), textViewResourceId, objects);
		act = a;
		this.objects = objects;
		this.layoutID = textViewResourceId;
//		loader = new AsyncPictureLoader(a);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
View row = convertView;
        
        if(row == null)
        {
            LayoutInflater inflater = LayoutInflater.from(act.getApplicationContext());
            row = inflater.inflate(layoutID, parent, false);
        }
        TextView nameView = (TextView)row.findViewById(R.id.rankName);
        TextView numberView = (TextView)row.findViewById(R.id.rankNumber);
        TextView levelView = (TextView)row.findViewById(R.id.levelName);
        TextView levelNameView = (TextView)row.findViewById(R.id.rankLevelName);
        ImageView pictureView = (ImageView)row.findViewById(R.id.rankPicture);
        
        RankedUser b = objects[position];
        nameView.setText(b.name);
        numberView.setText(b.position + ":");
        levelView.setText(b.level + "");
        levelNameView.setText(b.levelName);
//        loader.addID(b.id, pictureView);
        MainScreen.setUserPictureAsync(pictureView, b.id, act);
//        MainScreen.setUserPicture(pictureView, b.id);
        
//        logoView.setImageResource(R.drawable.bus);
        return row;
	}
	
}