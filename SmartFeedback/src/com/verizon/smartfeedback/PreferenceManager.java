package com.verizon.smartfeedback;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferenceManager {
	private static PreferenceManager prefMgr;
	private static SharedPreferences yapPreferences;
	private static Editor editor;
	
	public static final String CUSTOMER_ID = "customerId";
	public static final String YAP_CODE = "yapCode";
	
	private PreferenceManager(Context context){
		yapPreferences = context.getSharedPreferences("YAPPrefences", Context.MODE_APPEND);
		editor = yapPreferences.edit();
	}
	public static PreferenceManager getInstance(Context context){
		prefMgr = new PreferenceManager(context);		
		return prefMgr;
	}
	public String getStringValue(String key){
		return yapPreferences.getString(key, null);
	}
	public void putStringValue(String key,String value){
		editor.putString(key, value);
		editor.commit();
	}
	public void clearPreferences(){
		editor.clear();
		editor.commit();
	}
}
