package com.bm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BMBootCompletedReceiver extends BroadcastReceiver {
	BMDBParameters parameters = null;

	@Override
	public void onReceive(Context context, Intent intent) {
		MainActivity ma = new MainActivity();
		ma.setSchedule();
	}
}