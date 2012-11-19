package com.bm;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
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
				URL url;
				try {
					url = new URL(BMDesktop.getInstance(v.getContext()).getImageURI());
					final Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());				
					activity.runOnUiThread(new Runnable() {			
						public void run() {
							activity.preview.setImageBitmap(bitmap);
							activity.preview.setVisibility(ImageView.VISIBLE);
							wait.dismiss();
						}
					});
				} catch (MalformedURLException e) {
					Log.e("ERROR", e.getMessage());
				} catch (IOException e) {
					Log.e("ERROR", e.getMessage());
				}
			}
		});
		thread.start();
	}

}
