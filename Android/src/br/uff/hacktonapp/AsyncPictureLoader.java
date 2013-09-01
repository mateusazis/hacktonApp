package br.uff.hacktonapp;

import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

public class AsyncPictureLoader implements Runnable{

	private Queue<PictureData> ids = new LinkedList<PictureData>();
	private Activity act;
	private ImageSetter setter = new ImageSetter();
	
	public AsyncPictureLoader(Activity act){
		this.act = act;
	}
	
	public void addID(String id, ImageView view){
		synchronized (ids) {
			ids.offer(new PictureData(id, view));
			if(ids.size() == 1)
				new Thread(this).start();
		}
	}
	
	@Override
	public void run() {
		
		while(hasNext()){
			PictureData pd = null;
			synchronized (ids) {
				pd = ids.poll();
			}
			URL img_value = null;
			try {
				img_value = new URL("http://graph.facebook.com/"+pd.id+"/picture?type=large ");
				setter.bmp = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());
				setter.img = pd.view;
				act.runOnUiThread(setter);
			} catch (Exception e) {
				Log.e("", e.getMessage());
			}
		}
	}
	
	private boolean hasNext(){
		synchronized (ids) {
			return ids.size() > 0;
		}
	}
	
	private class PictureData{
		String id;
		ImageView view;
		public PictureData(String id, ImageView view){
			this.id = id;
			this.view = view;
		}
	}
	
	public class ImageSetter implements Runnable{
		ImageView img;
		Bitmap bmp;
		@Override
		public void run() {
			img.setImageBitmap(bmp);
		}
	}
}
