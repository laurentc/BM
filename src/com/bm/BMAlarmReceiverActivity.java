package com.bm;

import android.app.Activity;
import android.os.Bundle;

public class BMAlarmReceiverActivity extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		BMDesktop bm = BMDesktop.getInstance(this);
		bm.clearCache();
		bm.refreshDesktop();
	}

}
