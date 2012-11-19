package com.bm;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

public class BMMainActivity extends Activity {
	Button refresh = null;
	Button launchpreview = null;
	CheckBox schedule = null;
	ImageView preview = null;
	BMDBParameters parameters = null;
	
	private OnClickListener scheduleAction = new OnClickListener() {
		
		public void onClick(View v) {
			if(schedule.isChecked()){
				parameters.setValue(BMDBParameters.VAR_SCHEDULE, "1");
			}else{
				parameters.setValue(BMDBParameters.VAR_SCHEDULE, "0");
			}
			setSchedule();
		}
	};
	
	private OnClickListener maskPreview = new OnClickListener() {
		
		public void onClick(View v) {
			preview.setVisibility(ImageView.INVISIBLE);
		}
	};
	
	public void setSchedule(){
	    AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
	    Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.HOUR_OF_DAY, 10);
	    calendar.set(Calendar.MINUTE, 30);
        Intent intent = new Intent(this, BMAlarmReceiverActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 666, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		if(parameters.getValue(BMDBParameters.VAR_SCHEDULE) != null
				&& parameters.getValue(BMDBParameters.VAR_SCHEDULE).equals("1")){
	        am.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
		    parameters.setValue("schedule", "1");
		    Toast.makeText(this, R.string.schedule_activate, Toast.LENGTH_SHORT).show();
		}else{
	        am.cancel(pi);
		    parameters.setValue("schedule", "0");
		    Toast.makeText(this, R.string.schedule_desactivate, Toast.LENGTH_SHORT).show();
		}
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parameters = BMDBParameters.getInstance(this);
		BMDesktop.getInstance(this).clearCache();

        setContentView(R.layout.index);
        
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
        //getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
