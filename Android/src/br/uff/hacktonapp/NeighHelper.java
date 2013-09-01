package br.uff.hacktonapp;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.widget.ArrayAdapter;

public class NeighHelper {

	private static NeighHelper instance;
	private String [] originals;
	private String [] sorted;
	
	public static NeighHelper getInstance(Activity act){
		if(instance == null)
			instance = new NeighHelper(act);
		return instance;
	}
	
	public ArrayAdapter<String> makeAdapter(Activity act){
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(act, android.R.layout.simple_spinner_item, getSorted());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return adapter;
	}
	
	private NeighHelper(Activity act){
		this.originals = act.getResources().getStringArray(R.array.neighborhoods);
		
		sorted = new String[originals.length];
		System.arraycopy(originals, 0, sorted, 0, originals.length);
		
		Arrays.sort(sorted);
	}
	
	public String [] getSorted(){
		return sorted;
	}
	
	public int getID(String name){
		List<String> list = Arrays.asList(originals);
		return list.indexOf(name) + 1;
	}
	
	public int getID(int selectedIndex){
		return getID(sorted[selectedIndex]);
	}
	
	public String getName(int index){
		return sorted[index];
	}
	
}
