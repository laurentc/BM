package com.bm;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class BMAlarmReceiverActivity extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BMDesktop bm = BMDesktop.getInstance(this.getBaseContext());
		bm.clearCache();
		bm.refreshDesktop();
	}

}
