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
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class BMMainActivity extends Activity {
	Button refresh = null;
	Button launchpreview = null;
	CheckBox schedule = null;
	ImageView preview = null;
	BMDBParameters parameters = null;
	RelativeLayout previewLo = null;
	
	
	private void init(){
		if(parameters.getValue("url") == null){
			parameters.setValue("url", "http://www.bonjourmadame.fr");
		}
		if(parameters.getValue("pattern") == null){
			parameters.setValue("pattern", "<div[^>]*class=\"photo-panel\">.+?<img[^>]*src=\"([^\"]*)\"");
		}
	}
	
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
			previewLo.setVisibility(RelativeLayout.INVISIBLE);
		}
	};
	
	public void setSchedule(){
	    AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
	    Calendar calendar = Calendar.getInstance();
	    //calendar.set(Calendar.HOUR_OF_DAY, 10);
	    //calendar.set(Calendar.MINUTE, 30);
        Intent intent = new Intent(this, BMAlarmReceiverActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 666, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		if(parameters.getValue(BMDBParameters.VAR_SCHEDULE) != null
				&& parameters.getValue(BMDBParameters.VAR_SCHEDULE).equals("1")){
	        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + 30000, 30000, pi);
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
		init();

        setContentView(R.layout.index);
        
        refresh = (Button)findViewById(R.id.refresh);
        schedule = (CheckBox)findViewById(R.id.schedule);
        preview = (ImageView)findViewById(R.id.preview);
        launchpreview = (Button)findViewById(R.id.launchpreview);
        boolean isSchedule = (parameters.getValue("schedule") != null && parameters.getValue("schedule").equals("1"))?true:false;
        schedule.setChecked(isSchedule);
        previewLo = (RelativeLayout)findViewById(R.id.previewLo);
        
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
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if(item.getItemId() == R.id.menu_settings){
    		startActivity(new Intent(this,BMOptionActivity.class));
    		
    		return true;
    	}
    	
    	return false;
    }
}
