package com.pdonlon.screenfiltercolors;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.*;

public class MainActivity extends Activity 
{
	static MainActivity a;
	static boolean activated = false;
	static Intent serviceIntent;
	static int screenWidth, screenHeight;
	FilterService fs;
	HUDView hv;
	
	AdView adView;

	protected void onPause()
	{
		super.onPause();
		FilterService.setFilter(true);
		//call pause function (stops timer and dims screen)
		//same thing for pressing the clock
	}
	protected void onResume()
	{
		super.onResume();

		FilterService.setFilter(false);
	}
	
	public String getText()
	{
		if (activated)
			return "Stop Pause Button";
		else
			return "Start Pause Button";
	}
	
	public int getStatusBarHeight() {
		int result = 0;
		int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Display display = getWindowManager().getDefaultDisplay();

		screenWidth = display.getWidth();  // deprecated
		screenHeight = display.getHeight();  // deprecated
		
		a = this;

		final Button b = (Button) findViewById(R.id.button1);
		b.setText(getText());
		b.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if (!activated)
				{
					serviceIntent = new Intent(a, FilterService.class);
					startService(serviceIntent);
					activated = true;
					b.setText(getText());
					onBackPressed();
				}
				else
				{
					stopService(serviceIntent);
					activated = false;
					b.setText(getText());
				}
			}
		});
		
//		  // Create the adView.
//	    adView = new AdView(this);
//	    adView.setAdUnitId("ca-app-pub-8148658375496745/9345051306");
//	    adView.setAdSize(AdSize.BANNER);
//
//	    // Lookup your LinearLayout assuming it's been given
//	    // the attribute android:id="@+id/mainLayout".
//	    LinearLayout layout = (LinearLayout)findViewById(R.id.adLayout);
//
//	    // Add the adView to it.
//	    layout.addView(adView);
//
//	    // Initiate a generic request.
//	    AdRequest adRequest = new AdRequest.Builder().build();
//
//	    // Load the adView with the ad request.
//	    adView.loadAd(adRequest);

	}

}