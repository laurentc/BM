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
import android.widget.Toast;

public class BMDesktop {
	private static BMDesktop instance = null;
	public final String BM_URI = "http://www.bonjourmadame.fr";
	public final String BM_PATTERN = "<div[^>]*class=\"photo-panel\">.+?<img[^>]*src=\"([^\"]*)\"";
	private Context context = null;

	public BMDesktop(Context context) {
		this.context = context;
	}

	public static BMDesktop getInstance (Context context){
		if(instance == null){
			instance = new BMDesktop(context);
		}
		
		return instance;
	}
	
	public boolean refreshDesktop(){
		URL url;
		String imageURI = getImageURI();
		if(imageURI != null){
			try {
				url = new URL(imageURI);
				Bitmap bitmap = BitmapFactory.decodeStream(url.openStream());
				WallpaperManager wpm = WallpaperManager.getInstance(context);
				wpm.setBitmap(bitmap);
				Toast.makeText(context, "Fond d'écran modifié", Toast.LENGTH_SHORT).show();
				
				return true;
			} catch (MalformedURLException e) {
				System.out.println(e);
			} catch (IOException e) {
				System.out.println(e);
			}		
		}
		
		return false;
	}
	
	private String getImageURI(){
		String content = getURIContent();
		Pattern pattern = Pattern.compile(BM_PATTERN, Pattern.DOTALL);
		Matcher mtc = pattern.matcher(content.toString());				
		if(mtc.find()){
			return mtc.group(1);
		}else{
			Toast.makeText(context, "Image non trouvée", Toast.LENGTH_SHORT).show();
		}
		
		return null;
	}
	
	private String getURIContent(){
        BufferedReader br = null;
        URL url;
        StringBuilder content = new StringBuilder();
		try {
			url = new URL(BM_URI);
	        URLConnection con = url.openConnection(Proxy.NO_PROXY);
			try{
				InputStreamReader isr = new InputStreamReader(con.getInputStream());
				br =  new BufferedReader(isr);
			}catch(ClientProtocolException e){
				System.out.println(e);
			}catch(IOException e){
				System.out.println(e);
			}
		} catch (MalformedURLException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
		if(br != null){
			try {
				String line = "";
				while((line = br.readLine()) != null){
					content.append("\n" + line);
				}
			} catch (FileNotFoundException e) {
				System.out.println(e);
			} catch (IOException e) {
				System.out.println(e);
			}
		}
		
		return content.toString();
	}

}
