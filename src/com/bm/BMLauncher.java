package com.bm;

import android.app.Activity;

public class BMLauncher extends Activity{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BMLauncher handle = new BMLauncher();
		BMDesktop bm = BMDesktop.getInstance(handle.getApplicationContext());
		bm.refreshDesktop();
	}

}
