package br.uff.hacktonapp;

public class Solicitation {

	public int drawableID;
	public String description;
	public String protocol;
	
	public Solicitation(String description, String protocol, int drawableID){
		this.description = description;
		this.protocol = protocol;
		this.drawableID = drawableID;
	}
}
