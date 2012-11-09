package com.bm;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends Activity {
	Button refresh = null;
	ProgressBar wait = null;
	
	private OnClickListener refreshDesktop = new OnClickListener() {
		
		public void onClick(View v) {
			setWait(true);
			BMDesktop bm = BMDesktop.getInstance(getApplicationContext());
			bm.refreshDesktop();
			setWait(false);
		}
	};
	
	public void setWait(boolean status){
		if(status){
			wait.setVisibility(ProgressBar.VISIBLE);
			refresh.setClickable(false);
		}else{
			wait.setVisibility(ProgressBar.INVISIBLE);
			refresh.setClickable(true);
		}
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        
        refresh = (Button)findViewById(R.id.refresh);
        wait = (ProgressBar)findViewById(R.id.wait);
        
        refresh.setOnClickListener(refreshDesktop);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
