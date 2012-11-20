package com.bm;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class BMShowpreviewOnClickListener implements OnClickListener {
	BMMainActivity activity = null;

	BMShowpreviewOnClickListener(BMMainActivity pActivity){
		activity = pActivity;
	}

	public void onClick(final View v) {
		final ProgressDialog wait = ProgressDialog.show(activity, null, "Récupération de l'image", true, false);
		Thread thread = new Thread(new Runnable() {
			
			public void run() {
				final Bitmap bitmap = BMDesktop.getInstance(activity).getImage();				
				activity.runOnUiThread(new Runnable() {			
					public void run() {
						activity.preview.setImageBitmap(bitmap);
						activity.previewLo.setVisibility(ImageView.VISIBLE);
					}
				});
				wait.dismiss();
			}
		});
		thread.start();
	}

}
