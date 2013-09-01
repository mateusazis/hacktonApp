package br.uff.hacktonapp;

public class RankedUser {
	
	public String name;
	public int position;
	public String id;
	public int level;
	public String levelName;
	
	public RankedUser(String name, int position, String facebook_id, int level, String levelName){
		this.name = name;
		this.position = position;
		this.id = facebook_id;
		this.level = level;
		this.levelName = levelName;
	}

}
