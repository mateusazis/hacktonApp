package br.uff.hacktonapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;

import android.app.*;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainScreen extends Activity implements StatusCallback{

	public static GraphUser meUser;
	public static String userLevelName;
	public static int userLevelNumber;
	public static float userRelativeXP;
	private TextView nameView;
	private ImageView pictureView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);
		
		nameView = (TextView)findViewById(R.id.profileName);
		pictureView = (ImageView)findViewById(R.id.profileImage);
		
		LoginButton l = (LoginButton)findViewById(R.id.loginButton1);
		l.setSessionStatusCallback(this);
		setup();
		
	}
	
	private void setup(){
		if(meUser != null){
			nameView.setText(meUser.getName());
			setUserPicture(pictureView, meUser.getId());
			TextView tv = (TextView)findViewById(R.id.levelNumber);
			tv.setText(userLevelNumber + "");
			tv = (TextView)findViewById(R.id.levelName);
			tv.setText(userLevelName);
		}
	}
	
	public static void setUserPicture(ImageView view, String userID){
		URL img_value = null;
		try {
			img_value = new URL("http://graph.facebook.com/"+userID+"/picture?type=large ");
			Bitmap mIcon1 = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());
			view.setImageBitmap(mIcon1);
		} catch (Exception e) {
			Log.e("", e.getMessage());
		}
	}
	
//	public void logout(View v){
//		finish();
//	}
	
	public static void setUserPictureAsync(final ImageView view, String userID, final Activity act){
		try {
			final URL img_value = new URL("http://graph.facebook.com/"+userID+"/picture?type=large ");
			new Thread(new Runnable(){
				@Override
				public void run() {
					try {
						final Bitmap mIcon1 = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());
						act.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							view.setImageBitmap(mIcon1);
							
						}
					});
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}).start();
			
			
		} catch (Exception e) {
			Log.e("", e.getMessage());
		}
	}
	
	public void makeSolicitation(View v){ 
//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		builder.setMessage("Deseja incluir uma foto?\n\nVocê pode compartilhá-la mais tarde com seus amigos!").setTitle("Foto");
//		builder.setPositiveButton("OK", new AddPictureListener(true));
//		builder.setNegativeButton("Não agora", new AddPictureListener(false));
//		AlertDialog dialog = builder.create();
//		dialog.show();
		selectImage();
	}
	
	private static final int REQUEST_CAMERA = 300, SELECT_FILE = 400;
	
	private void selectImage() {
        final CharSequence[] items = { "Tirar foto", "Escolher na Galeria",
                "Cancelar" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escolha uma foto!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Tirar foto")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment
                            .getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Escolher na Galeria")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Selecione um arquivo"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancelar")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
	
	public void viewRanking(View v){
		Intent i = new Intent(this, Ranking.class);
		startActivity(i);
	}

	public void viewInfo(View v){
		Intent i = new Intent(this, ConfigScreen.class);
		startActivity(i);
	}
	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		// TODO Auto-generated method stub
//		super.onActivityResult(requestCode, resultCode, data);
//		if(resultCode == RESULT_OK){
//			Bundle extras = data.getExtras();
//		    SolicitationScreen.pictureBitmap = (Bitmap) extras.get("data");
//			
//			Intent i = new Intent(this, SolicitationScreen.class);
//			i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//			startActivity(i);
//			SlideTransition.forwardTransition(this);
//		} else{
//			Toast.makeText(this, "Nenhuma foto tirada.", Toast.LENGTH_SHORT).show();
//		}
//	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bm = null;
        if (resultCode == RESULT_OK) {
			if (requestCode == REQUEST_CAMERA) {
                File f = new File(Environment.getExternalStorageDirectory()
                        .toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
                    bm = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            btmapOptions);
                    // bm = Bitmap.createScaledBitmap(bm, 70, 70, true);
//                    String path = android.os.Environment
//                            .getExternalStorageDirectory()
//                            + File.separator
//                            + "Phoenix" + File.separator + "default";
//                    f.delete();
//                    OutputStream fOut = null;
//                    File file = new File(path, String.valueOf(System
//                            .currentTimeMillis()) + ".jpg");
                    try {
//                        fOut = new FileOutputStream(file);
//                        bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
//                        fOut.flush();
//                        fOut.close();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                String tempPath = getPath(selectedImageUri, this);
                BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
                bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
            }
			
			
			if(bm != null){
				
			    SolicitationScreen.pictureBitmap = bm;
				
				Intent i = new Intent(this, SolicitationScreen.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				startActivity(i);
				SlideTransition.forwardTransition(this);
			} else{
				Toast.makeText(this, "Nenhuma foto tirada.", Toast.LENGTH_SHORT).show();
			}
        }
    }
	
	public String getPath(Uri uri, Activity activity) {
        String[] projection = { MediaColumns.DATA };
        Cursor cursor = activity
                .managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
	
	private class AddPictureListener implements OnClickListener{
		private boolean shouldAdd;
		
		public AddPictureListener(boolean shouldAdd){
			this.shouldAdd = shouldAdd;
		}
		
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			if(this.shouldAdd){
				Log.d("", "add picture");
				Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			    startActivityForResult(takePictureIntent, 0);
			}else{
				Log.d("", "skip picture");
			}
			
		}
		
	}

	@Override
	public void call(Session session, SessionState state, Exception exception) {
		if(session.isClosed())
			finish();
		
	}
}
