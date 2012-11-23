package com.bm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class BMOptionActivity extends Activity {
	BMDBParameters parameters = null;
	private EditText url = null;
	private EditText pattern = null;
	private Button save = null;

	private OnClickListener saveOptions = new OnClickListener() {
		
		public void onClick(View v) {
			BMDBParameters.getInstance(v.getContext()).setValue("url", url.getText().toString());
			BMDBParameters.getInstance(v.getContext()).setValue("pattern", pattern.getText().toString());
			
			finish();
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        parameters = BMDBParameters.getInstance(this);
		setContentView(R.layout.option);
		
		url = (EditText)findViewById(R.id.parse_url);
		pattern = (EditText)findViewById(R.id.pattern);
		save = (Button)findViewById(R.id.save);
		
		String urlValue = parameters.getValue("url");
		String patternValue = parameters.getValue("pattern");
		
		url.setText(urlValue);
		pattern.setText(patternValue);
		save.setOnClickListener(saveOptions);
	}
}
