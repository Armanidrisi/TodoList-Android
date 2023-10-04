package com.armanidrisi.todolost;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.content.Intent;
import android.os.Handler;

public class SplashActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
		
		new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {		
					Intent i = new Intent(SplashActivity.this, MainActivity.class);
					startActivity(i);
					finish();
				}
			}, 2000);
    }
    
}
