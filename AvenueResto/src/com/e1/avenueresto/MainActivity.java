package com.e1.avenueresto;



import android.os.Bundle;
import android.os.StrictMode;

import android.view.Window;
import android.view.WindowManager;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

public class MainActivity extends Activity {
	
  
	  private static ProgressDialog dialog;
	
	  private static Handler handler;
	  private static  FragmentManager fm;
	  private Thread downloadThread;
	  private static Context ctx;
	  
	  public static Activity act;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        
        setContentView(R.layout.activity_main);
        
       
        
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
        .permitAll()
        .build();
        StrictMode.setThreadPolicy(policy);//ics req
        
        // Create a handler to update the UI
        handler = new Handler();
        fm = getFragmentManager();
        ctx = getBaseContext();
        act = this;
        DataManager.ctx = ctx;
       
        // Check if the thread is already running
        downloadThread = (Thread) getLastNonConfigurationInstance();
        if (downloadThread != null && downloadThread.isAlive()) {
          dialog = ProgressDialog.show(this, "Download", "downloading");
        }
        
        downloadMainMenu();
        
   
    }
   
    public void downloadMainMenu() {
        dialog = ProgressDialog.show(this, "Downloading Menus", "downloading");
        downloadThread = new MyThread();
        downloadThread.start();
      }
    
    // dismiss dialog if activity is destroyed
    @Override
    protected void onDestroy() {
      if (dialog != null && dialog.isShowing()) {
        dialog.dismiss();
        dialog = null;
      }
      super.onDestroy();
    }
    
    static public class MyThread extends Thread {
        @Override
        public void run() {
          try
          {
           	DataManager.GetMainMenus();
           //	DataManager.GetAllOrder();
            handler.post(new MyRunnable());
          } catch (Exception e) {
        	 
              e.printStackTrace();
          } finally {

          }
        }
      }

      static public class MyRunnable implements Runnable {
        public void run() {
    	
	             FragmentTransaction ft = fm.beginTransaction();
	             
	             StartFragment sf = new StartFragment();
	             ft.add(R.id.mainfragment,sf);
	             ft.commit();
	             dialog.dismiss();
        	 }
        	
          
        
      }
		
	
   
}
