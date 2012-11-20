package com.bm;

import android.app.ProgressDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class BMDesktopOnClickListener implements OnClickListener {
	BMMainActivity activity = null;

	BMDesktopOnClickListener(BMMainActivity pActivity){
		activity = pActivity;
	}

	public void onClick(final View v) {
		final ProgressDialog wait = ProgressDialog.show(activity, null, "Modification du fond d'écran", true, false);
		Thread thread = new Thread(new Runnable() {
			
			public void run() {
				final boolean success = BMDesktop.getInstance(v.getContext()).refreshDesktop();
				activity.runOnUiThread(new Runnable() {
					public void run() {
						wait.dismiss();
						if(success){
							Toast.makeText(v.getContext(), "Fond d'écran modifié", Toast.LENGTH_SHORT).show();
						}
					}
				});
				
			}
		});
		thread.start();
	}

}
