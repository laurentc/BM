package com.bm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BMDBDescription extends SQLiteOpenHelper {
	private static final String CREATE_BDD = "CREATE TABLE bm_parameters (par_id INTEGER PRIMARY KEY AUTOINCREMENT, par_index TEXT NOT NULL, par_value TEXT NOT NULL);";

	public BMDBDescription(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_BDD);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
}
