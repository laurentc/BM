package com.bm;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RadioButton;

public class MainActivity extends Activity {
	Button refresh = null;
	ProgressBar wait = null;
	CheckBox schedule = null;
	BMDBParameters parameters = null;
	
	private OnClickListener refreshDesktop = new OnClickListener() {
		
		public void onClick(View v) {
			setWait(true);
			BMDesktop bm = BMDesktop.getInstance(getApplicationContext());
			bm.refreshDesktop();
			setWait(false);
		}
	};
	
	private OnClickListener scheduleAction = new OnClickListener() {
		
		public void onClick(View v) {
			setSchedule(schedule.isChecked());
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
	
	public void setWait(boolean status){
		if(status){
			wait.setVisibility(ProgressBar.VISIBLE);
			refresh.setClickable(false);
		}else{
			wait.setVisibility(ProgressBar.INVISIBLE);
			refresh.setClickable(true);
		}
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parameters = BMDBParameters.getInstance(getApplicationContext());

        setContentView(R.layout.activity_main);
        
        refresh = (Button)findViewById(R.id.refresh);
        wait = (ProgressBar)findViewById(R.id.wait);
        schedule = (CheckBox)findViewById(R.id.schedule);
        boolean isSchedule = (parameters.getValue("schedule") != null && parameters.getValue("schedule").equals("1"))?true:false;
        schedule.setChecked(isSchedule);
        
        refresh.setOnClickListener(refreshDesktop);  
        schedule.setOnClickListener(scheduleAction);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
