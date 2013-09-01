package br.uff.hacktonapp;

public class UrbanProblem {

	private static final int [] drawables = {R.drawable.tree, R.drawable.asphalt, R.drawable.pole, R.drawable.no_parking};
	public RequestType type;
	public int code;
	public String description;

	public UrbanProblem(RequestType type, int code, String description){
		this.type = type;
		this.code = code;
		this.description = description;
	}
	
	public int getDrawableResource(){
		return drawables[type.ordinal()];
	}
}
