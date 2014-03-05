package com.pdonlon.screenfiltercolors;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.*;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.*;
import android.graphics.*;
import android.graphics.Paint.Style;
import android.os.*;
import android.os.Process;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.view.*;
import android.widget.*;

public class FilterService extends Service implements View.OnTouchListener {
	HUDView mView;
	
	private WindowManager windowManager;
	WindowManager.LayoutParams lp;
	int counter = 0;

	java.lang.Process thisProcess;
	static MainActivity mactivity;

	final Runtime runtime = Runtime.getRuntime();

	static int screenWidth, screenHeight;
	
	static boolean filterOn = false;

	public IBinder onBind(Intent intent) {
		return null;
	}
	
	public static void setFilter(boolean on)
	{
		filterOn = on;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mView = new HUDView(this, MainActivity.a);
		
		int nFlags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|
		WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
		lp = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
				nFlags,
				PixelFormat.TRANSLUCENT);

		lp.height = mactivity.screenHeight;
		lp.width = mactivity.screenWidth;
		
		//lp.gravity = Gravity.TOP | Gravity.LEFT;
		lp.gravity = Gravity.FILL;
		
		
		mView.setOnTouchListener(this);
		windowManager.addView(mView, lp);

		Builder builder = new Builder(this);
		builder.setContentTitle("Pause Button is running");
		builder.setContentText("Tap here to stop the daemon.");
		builder.setSmallIcon(R.drawable.ic_launcher);

		Intent mainscreen = new Intent(this, MainActivity.class);
		builder.setContentIntent(PendingIntent.getActivity(this, 0, mainscreen, PendingIntent.FLAG_UPDATE_CURRENT));

		Notification n = builder.build();
		startForeground(1, n);
		
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		stopForeground(true);
		String[] aa = {"exit"};
		if(mView != null)
		{
			((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(mView);
			mView = null;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
	
		return true;
		
	}

}

class HUDView extends View {

	private Paint mLoadPaint;
	FilterService ps;
	
	public HUDView(Context context, MainActivity mactivity) {
		super(context);
		this.ps = (FilterService) context;
		Toast.makeText(getContext(),"Pause Button Started", Toast.LENGTH_SHORT).show();
		FilterService.mactivity = mactivity;
		mLoadPaint = new Paint();
		mLoadPaint.setAntiAlias(true);
		mLoadPaint.setTextSize(10);
		mLoadPaint.setARGB(255, 255, 0, 0);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);		
		
		if (FilterService.filterOn)
		{
			mLoadPaint.setColor(Color.argb(100, 255, 140, 0));
			mLoadPaint.setStyle(Style.FILL);

//			canvas.drawRect(0, 0-(FilterService.mactivity.getStatusBarHeight()), MainActivity.screenWidth, MainActivity.screenHeight+FilterService.mactivity.getStatusBarHeight(), mLoadPaint);
			canvas.drawRect(0, 0-500, 100000000, 100000000, mLoadPaint);

		}

		
		
	}

	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
	}



}