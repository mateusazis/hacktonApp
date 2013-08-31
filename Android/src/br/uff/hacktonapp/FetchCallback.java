package br.uff.hacktonapp;

import org.json.JSONObject;

public interface FetchCallback{
	public void onResult(boolean success, JSONObject response);
}
