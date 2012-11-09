package com.bm;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootcompletedReceiver extends BroadcastReceiver {
	BMDBParameters parameters = null;

	@Override
	public void onReceive(Context context, Intent intent) {
		parameters = BMDBParameters.getInstance(context);
		if(parameters.getValue("schedule") != null && parameters.getValue("schedule").equals("1")){
			AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 10);
			calendar.set(Calendar.MINUTE, 30);
			calendar.set(Calendar.SECOND, 0);
			PendingIntent pi = PendingIntent.getService(context, 0, new Intent(context, BMLauncher.class), PendingIntent.FLAG_UPDATE_CURRENT);
			am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
			parameters.setValue("schedule", "1");
		}
	}
}