package com.bm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BMDBParameters {
	
	public static final String VAR_SCHEDULE = "schedule";

	private static final int VERSION_BDD = 1;
	private static final String NOM_BDD = "bmadame.db";
	private static BMDBParameters instance = null;
 
	private SQLiteDatabase bdd;
 
	private BMDBDescription dbDescription;
 
	public BMDBParameters(Context context){
		//On créer la BDD et sa table
		dbDescription = new BMDBDescription(context, NOM_BDD, null, VERSION_BDD);
	}
	
	public static BMDBParameters getInstance(Context context){
		if(instance == null){
			instance = new BMDBParameters(context);
		}
		
		return instance;
	}
 
	public void open(){
		bdd = dbDescription.getWritableDatabase();
	}
 
	public void close(){
		bdd.close();
	}
	
	public String getValue(String index){
		open();
		Cursor results = bdd.query("bm_parameters", new String[]{"par_value"}, "par_index=?", new String[]{index}, null, null, null);
		if(results.moveToNext()){
			return results.getString(0);
		}
		close();
		
		return null;
	}
	
	public void setValue(String index, String value){
		ContentValues cv = new ContentValues();
		cv.put("par_value", value);
		if(getValue(index) == null){
			open();
			cv.put("par_index", index);
			bdd.insert("bm_parameters", null, cv);
		}else{
			open();
			bdd.update("bm_parameters", cv, "par_index=?", new String[]{index});
		}
		close();
	}
}
