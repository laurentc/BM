package com.bm;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

public class MainActivity extends Activity {
	Button refresh = null;
	Button launchpreview = null;
	CheckBox schedule = null;
	ImageView preview = null;
	BMDBParameters parameters = null;
	
	private OnClickListener scheduleAction = new OnClickListener() {
		
		public void onClick(View v) {
			setSchedule(schedule.isChecked());
		}
	};
	
	private OnClickListener maskPreview = new OnClickListener() {
		
		public void onClick(View v) {
			preview.setVisibility(ImageView.INVISIBLE);
		}
	};
	
	private void setSchedule(boolean status){
	    AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
	    Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.HOUR_OF_DAY, 10);
	    calendar.set(Calendar.MINUTE, 30);
	    calendar.set(Calendar.SECOND, 0);
	    PendingIntent pi = PendingIntent.getService(getApplicationContext(), 0, new Intent(getApplicationContext(), BMLauncher.class), PendingIntent.FLAG_UPDATE_CURRENT);
		if(status){
		    am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
		    parameters.setValue("schedule", "1");
		}else{
			am.cancel(pi);
		    parameters.setValue("schedule", "0");
		}
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parameters = BMDBParameters.getInstance(getApplicationContext());
		BMDesktop.getInstance(getApplicationContext()).clearCache();

        setContentView(R.layout.activity_main);

        refresh = (Button)findViewById(R.id.refresh);
        schedule = (CheckBox)findViewById(R.id.schedule);
        preview = (ImageView)findViewById(R.id.preview);
        launchpreview = (Button)findViewById(R.id.launchpreview);
        boolean isSchedule = (parameters.getValue("schedule") != null && parameters.getValue("schedule").equals("1"))?true:false;
        schedule.setChecked(isSchedule);        
        
        refresh.setOnClickListener(new BMDesktopOnClickListener(this));  
        schedule.setOnClickListener(scheduleAction);
        preview.setOnClickListener(maskPreview);
        launchpreview.setOnClickListener(new BMShowpreviewOnClickListener(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
