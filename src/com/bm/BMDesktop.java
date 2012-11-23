package com.bm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class BMDesktop {
	private static BMDesktop instance = null;
	private String uri_to_parse = null;
	public String pattern_to_find = null;
	private Context context = null;
	private String uri = null;
	private BMDBParameters parameters;

	public BMDesktop(Context context) {
		parameters = BMDBParameters.getInstance(context);
		uri_to_parse = parameters.getValue("url");
		pattern_to_find = parameters.getValue("pattern");
		this.context = context;
	}
	
	public static BMDesktop getInstance (Context context){
		if(instance == null){
			instance = new BMDesktop(context);
		}
		
		return instance;
	}
	
	public boolean refreshDesktop(){
		String imageURI = getImageURI();
		if(imageURI != null){
			try {
				WallpaperManager wpm = WallpaperManager.getInstance(context);
				Bitmap bitmap = getImage();
				if(bitmap != null){
					int width = wpm.getDesiredMinimumWidth();//960
					int height = wpm.getDesiredMinimumHeight();//800
					int imageWidth = bitmap.getWidth();
					int imageHeight = bitmap.getHeight();
					Bitmap resized = Bitmap.createScaledBitmap(bitmap, wpm.getDesiredMinimumWidth(), wpm.getDesiredMinimumHeight(), true);
					wpm.setBitmap(resized);
					
					return true;
				}
			} catch (MalformedURLException e) {
				Log.e("BMDesktop.refreshDesktop", e.getMessage());
			} catch (IOException e) {
				Log.e("BMDesktop.refreshDesktop", e.getMessage());
			}catch (Exception e){
				Log.e("BMDesktop.refreshDesktop", e.getMessage());
			}
		}
		
		return false;
	}
	
	public String getImageURI(){
		if(uri != null){
			return uri;
		}
		String content = getURIContent();
		Pattern pattern = Pattern.compile(pattern_to_find, Pattern.DOTALL);
		Matcher mtc = pattern.matcher(content.toString());				
		if(mtc.find()){
			return mtc.group(1);
		}else{
			Log.i("BMDesktop.getImageURI", "Image non trouvée");
		}
		
		return null;
	}
	
	private String getURIContent(){
        BufferedReader br = null;
        URL url;
        StringBuilder content = new StringBuilder();
		try {
			url = new URL(uri_to_parse);
	        URLConnection con = url.openConnection(Proxy.NO_PROXY);
			try{
				InputStreamReader isr = new InputStreamReader(con.getInputStream());
				br =  new BufferedReader(isr);
			}catch(ClientProtocolException e){
				Log.e("BMDesktop.getURIContent", e.getMessage());
			}catch(IOException e){
				Log.e("BMDesktop.getURIContent", e.getMessage());
			}
		} catch (MalformedURLException e) {
			Log.e("BMDesktop.getURIContent", e.getMessage());
		} catch (IOException e) {
			Log.e("BMDesktop.getURIContent", e.getMessage());
		}
		if(br != null){
			try {
				String line = "";
				while((line = br.readLine()) != null){
					content.append("\n" + line);
				}
			} catch (FileNotFoundException e) {
				Log.e("BMDesktop.getURIContent", e.getMessage());
			} catch (IOException e) {
				Log.e("BMDesktop.getURIContent", e.getMessage());
			}
		}
		
		return content.toString();
	}
	
	public void clearCache(){
		uri = null;
	}

	public Bitmap getImage(){
		URL url;
		String uri = getImageURI();
		if(uri != null){
			try {
				url = new URL(getImageURI());
				URLConnection uc = url.openConnection();			
				return BitmapFactory.decodeStream(uc.getInputStream());		
			} catch (MalformedURLException e) {
				Log.e("BMDesktop.getImage", e.getMessage());
			} catch (IOException e) {
				Log.e("BMDesktop.getImage", e.getMessage());
			}
		}
		
		return null;
	}
}
