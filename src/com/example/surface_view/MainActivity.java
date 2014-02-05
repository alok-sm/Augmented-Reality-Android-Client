package com.example.surface_view;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.VideoView;

public class MainActivity extends Activity implements SurfaceHolder.Callback{
	 Camera camera;
	 String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/test.jpg";
	 String url;
	 String ip;
	 SurfaceView surfaceView;
	 SurfaceHolder surfaceHolder;
	 VideoView videoView;
	 ImageView myImage;
	 FrameLayout f1;
	 View picView;
	 AsyncPost post = null;
	 Bundle istate = null;
	 PictureCallback  picCall;
	 boolean picTaken=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		istate = savedInstanceState;
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.getip);
	}
	
	@SuppressWarnings("deprecation")
	public void setIp(View arg){
		EditText et = (EditText) findViewById(R.id.ed);
		url = "http://"+et.getText().toString()+":3000/matches";
//		"http://"+ip+":3000/matches";
//		url = "http://192.168.1.7:3000/matches";
		Log.e("ALok",url);
		setContentView(R.layout.activity_main);
	     //  getWindow().setFormat(PixelFormat.TRANSPARENT);
	      myImage = (ImageView) findViewById(R.id.imageView1);
	       myImage.setAlpha(100);
	     //  videoView.setZOrderOnTop(true);
		 surfaceView = (SurfaceView)findViewById(R.id.surfaceView1);
		//surfaceView.setZOrderMediaOverlay(true);
		// surfaceView.setZOrderOnTop(true);
	       
	       
	       surfaceHolder = surfaceView.getHolder();
	       surfaceHolder.setFormat(PixelFormat.TRANSPARENT);
	       surfaceHolder.addCallback(this);
	      //Button b1= (Button)findViewById(R.id.button1);
	    //  Button b2=(Button)findViewById(R.id.button2);
	      picCall = new PictureCallback() {

	    	    @Override
	    	    public void onPictureTaken(byte[] data, Camera camera) {
	    	    	if(!picTaken){
	    	    		picTaken=true;
	    	    	}
	    	    	else{
	    	    		camera.startPreview();
	    	    	}
	    	    		
	    	        try {
	    	            FileOutputStream fos = new FileOutputStream(imageFilePath);
	    	            fos.write(data);
	    	            fos.close();
	    	        } catch (FileNotFoundException e) {	
	    	        } catch (IOException e) {
	    	        }
	    	        
	    	        
	    	        
	    	        
	    	        
	    	        
	    	        
					
					f1=(FrameLayout) findViewById(R.id.frame);
					videoView = (VideoView)findViewById(R.id.videoView1);
					videoView.setZOrderMediaOverlay(true);
					videoView.bringToFront();
					f1.removeView(videoView);
					f1.addView(videoView);
					
					Log.e("url,imagefilepath=",url+","+imageFilePath);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					post = new AsyncPost(url, imageFilePath);
					post.execute();
					
					while(!post.done);
					String result = "error";
					try
					{
						result = post.get();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					Log.e("alok",result);
					
					if(!result.equals("error")){
					Uri uri=Uri.parse(result);
//					Uri uri=Uri.parse("http://www.tools4movies.com/dvd_catalyst_profile_samples/Harold Kumar 3 Christmas bionic.mp4");
//					videoView.setVideoPath("/sdcard/test.mp4");
//					camera.startPreview();
					videoView.setVideoURI(uri);
					videoView.start();
					myImage.setVisibility(View.INVISIBLE);}
	    	        
	    	        
	    	        
	    	        
	    	        
	    	    }
	    	};
	      surfaceView.setOnClickListener(new SurfaceView.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				camera.takePicture(null, null, picCall);//Picture taken here(Full pic so need to open and then crop it)
				
//				else finish();
			}
	    	  
	      });
	    /*  b2.setOnClickListener(new Button.OnClickListener(){
				@Override
				public void onClick(View arg0) {
					camera.takePicture(null, null, picCall);//Picture taken here(Full pic so need to open and then crop it)
					
					//while(fullImg==null){}
					
				}});*/
	      
	    /*   b1.setOnClickListener(new Button.OnClickListener(){

	
			@Override
			public void onClick(View arg0) {
				//videoView.getParent().requestTransparentRegion(videoView);
				f1=(FrameLayout) findViewById(R.id.frame);
				videoView = (VideoView)findViewById(R.id.videoView1);
				videoView.setZOrderMediaOverlay(true);
				videoView.bringToFront();
				f1.removeView(videoView);
				f1.addView(videoView);
				Uri uri=Uri.parse("http://www.tools4movies.com/dvd_catalyst_profile_samples/Harold Kumar 3 Christmas bionic.mp4");//replace with your method which retrives url
				//videoView.setVideoPath("/sdcard/test.mp4");
				videoView.setVideoURI(uri);
				videoView.start();
				myImage.setVisibility(View.INVISIBLE);
				//surfaceView.setVisibility(View.INVISIBLE);
				//videoView.setVisibility(View.VISIBLE);
				// TODO Auto-generated method stub
				
			}});*/
	       surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
			 
	       
	        
		      

	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		camera.startPreview();
		//videoView.bringToFront();
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		camera = Camera.open();
	       try {
			camera.setPreviewDisplay(surfaceHolder);
		} catch (IOException e) {
			camera.release();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       
	       
		    //videoView.bringToFront();
		    
	       
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		videoView.stopPlayback();
		camera.stopPreview();
		camera.release();
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	    	videoView.stopPlayback();
		//	camera.stopPreview(); Remove these 3 lines
	    	
	    //	camera.release();
	    //	finish();
	    	myImage.setVisibility(View.VISIBLE); // add this line 
	    }
	    return super.onKeyDown(keyCode, event);
	}
//
}
