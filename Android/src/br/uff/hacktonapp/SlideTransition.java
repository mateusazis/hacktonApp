package br.uff.hacktonapp;

import android.app.Activity;

public class SlideTransition {
	public static void forwardTransition(Activity act){
		act.overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
	}
	
	public static void backTransition(Activity act){
		act.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
	}
}
