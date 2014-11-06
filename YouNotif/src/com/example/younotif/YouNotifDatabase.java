package com.example.younotif;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class YouNotifDatabase extends SQLiteOpenHelper {
	
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "YouNotifDB";
    
    //only for the table groups
    private static final String TABLE_GROUPS = "GROUP_SHARE";
    private static final String KEY_ID = "ID_GROUP";
    private static final String KEY_NAME = "GROUP_NAME";
    private static final String[] COLUMNS = {KEY_ID,KEY_NAME};
    
    //only for the table notif
    private static final String TABLE_NOTIF = "NOTIF";
    private static final String KEY_ID_NOTIF = "ID_NOTIF";
    private static final String KEY_AUTHOR = "AUTHOR";

    private static final String KEY_DATE_CREATION = "DATE_CREATION";
    private static final String KEY_TITLE = "TITLE";

    private static final String KEY_CONTENT = "CONTENT";
    private static final String KEY_PERIOD = "PERIOD";
    private static final String KEY_TYPE = "TYPE";

    private static final String JOUR_MODULE = "DAY";
    private static final String KEY_DATE_DEBUT = "BEGIN_DAY";
    private static final String KEY_DATE_FIN = "END_DAY";
    private static final String KEY_HEURE_MODULE = "BEGIN_HOUR";

    private static final String KEY_HEURE_FIN_MODULE = "END_HOUR";
    private static final String KEY_NOTIF_CODE = "NOTIF_CODE";
    private static final String KEY_TARGET = "CIBLE";
    private static final String[] COLUMNS_NOTIF = {KEY_ID_NOTIF,KEY_AUTHOR,KEY_DATE_CREATION,KEY_TITLE,KEY_CONTENT,KEY_TYPE,KEY_PERIOD,JOUR_MODULE,KEY_HEURE_MODULE,KEY_DATE_DEBUT,KEY_DATE_FIN,KEY_HEURE_FIN_MODULE,KEY_NOTIF_CODE,KEY_TARGET};
    public static int currentNotifIndex = 0;
	public YouNotifDatabase(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	public YouNotifDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
        String CREATE_GROUPS_TABLE = "CREATE TABLE GROUP_SHARE ( " +
                "ID_GROUP INTEGER PRIMARY KEY AUTOINCREMENT, " + 
                "GROUP_NAME TEXT)";
        db.execSQL(CREATE_GROUPS_TABLE);
        
        String CREATE_NOTIF_TABLE = "CREATE TABLE NOTIF ( " +
        		  "ID_NOTIF INTEGER PRIMARY KEY  AUTOINCREMENT,"+
        		  "AUTHOR TEXT,"+
        		  "DATE_CREATION TEXT,"+
        		  "TITLE TEXT,"+
        		  "CONTENT TEXT,"+
        		  "PERIOD TEXT,"+
        		  "TYPE TEXT,"+
        		  "DAY TEXT,"+
        		  "BEGIN_DAY TEXT,"+
        		  "END_DAY TEXT,"+
        		  "BEGIN_HOUR TEXT,"+
        		  "END_HOUR TEXT,"+
        		  "CIBLE TEXT,"+
        		  "NOTIF_CODE"+ ")";
        		  db.execSQL(CREATE_NOTIF_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	public void addGroup(String name){
        SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);  
 
        db.insert(TABLE_GROUPS, 
                null, 
                values); 
         db.close(); 
	}

	public String getGroup(int id){
	    SQLiteDatabase db = this.getReadableDatabase();
	    
	    Cursor cursor = 
	            db.query(TABLE_GROUPS, // a. table
	            COLUMNS, // b. column names
	            " ID_GROUP = ?", // c. selections 
	            new String[] { String.valueOf(id) }, // d. selections args
	            null, // e. group by
	            null, // f. having
	            null, // g. order by
	            null); // h. limit
	 
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	   
	    return cursor.getString(1);
	}
	public List<String> getAllGroups(){
		   List<String> results = new LinkedList<String>();
	       String query = "SELECT  * FROM " + TABLE_GROUPS;
	              SQLiteDatabase db = this.getWritableDatabase();
	       Cursor cursor = db.rawQuery(query, null);
	 
	       if (cursor.moveToFirst()) {
	           do {
	        	   
	               results.add(cursor.getString(1));
	           } while (cursor.moveToNext());
	       }
		   
		   
		  return results;
		}

	public boolean existNotif(String notifCode){
	    SQLiteDatabase db = this.getReadableDatabase();
	    
	    Cursor cursor = 
	            db.query(TABLE_NOTIF, // a. table
	            COLUMNS_NOTIF, // b. column names
	            " NOTIF_CODE = ?", // c. selections 
	            new String[] { String.valueOf(notifCode) }, // d. selections args
	            null, // e. group by
	            null, // f. having
	            null, // g. order by
	            null); // h. limit
	 
	    if (cursor != null){
	        cursor.moveToFirst();
	    	if(cursor.getCount() > 0){
			    return true;

	    	} else {
			    return false;

	    	}

	    } else {
	    	return false;
	    }
	}
	public boolean existGroup(String groupName){
	    SQLiteDatabase db = this.getReadableDatabase();
	    
	    Cursor cursor = 
	            db.query(TABLE_GROUPS, // a. table
	            COLUMNS, // b. column names
	            " GROUP_NAME = ?", // c. selections 
	            new String[] { String.valueOf(groupName) }, // d. selections args
	            null, // e. group by
	            null, // f. having
	            null, // g. order by
	            null); // h. limit
	 
	    if (cursor != null){
	        cursor.moveToFirst();
	    	if(cursor.getCount() > 0){
			    return true;

	    	} else {
			    return false;

	    	}

	    } else {
	    	return false;
	    }
	}

	public List<NotificationModel> getNotifByTarget(String target){
	    SQLiteDatabase db = this.getReadableDatabase();
		List<NotificationModel> results = new LinkedList<NotificationModel>();

	    Cursor cursor = 
	            db.query(TABLE_NOTIF, // a. table
	            COLUMNS_NOTIF, // b. column names
	            " CIBLE = ?", // c. selections 
	            new String[] { String.valueOf(target) }, // d. selections args
	            null, // e. group by
	            null, // f. having
	            null, // g. order by
	            null); // h. limit
	 
       
       if (cursor.moveToFirst()) {
           do {
        	   /*TODO*/
        	   
               //results.add(...);
        	   //results.add(new Notification());
           } while (cursor.moveToNext());
       }

	   
	    return results;
	}
	public void addNotif(String title,String author,String dateCrea,String contentNotif,String type,String period,String jour,String heure_debut,String date_debut,String date_fin,String heure_fin_module,String notifCode,String target){
        SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
        values.put(KEY_CONTENT, contentNotif);  
        values.put(KEY_TITLE, title);  
        values.put(KEY_AUTHOR, author);  
        values.put(KEY_DATE_CREATION, dateCrea);  

        values.put(KEY_TYPE, type);  
        values.put(KEY_PERIOD, period);  
        values.put(JOUR_MODULE, jour);  
        values.put(KEY_HEURE_MODULE, heure_debut);  
        values.put(KEY_DATE_DEBUT, date_debut);  
        values.put(KEY_DATE_FIN, date_fin);  
        values.put(KEY_HEURE_FIN_MODULE, heure_fin_module);  
        values.put(KEY_NOTIF_CODE, notifCode);  
        values.put(KEY_TARGET, target);  
        db.insert(TABLE_NOTIF, 
                null, 
                values); 
         db.close(); 
	}
	public void addNotif(NotificationModel notif){
		if(!existNotif(notif.getNotifCode())) {
        SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
        values.put(KEY_CONTENT, notif.getContent());  
        values.put(KEY_TITLE, notif.getTitle());  
        values.put(KEY_AUTHOR, notif.getAuthor());  
        values.put(KEY_DATE_CREATION, notif.getDateCrea());  

        values.put(KEY_TYPE, notif.getType());  
        values.put(KEY_PERIOD, notif.getPeriod());  
        values.put(JOUR_MODULE, notif.getDay());  
        values.put(KEY_HEURE_MODULE, notif.getBeginHour());  
        values.put(KEY_DATE_DEBUT, notif.getBeginDate());  
        values.put(KEY_DATE_FIN, notif.getEndDate());  
        values.put(KEY_HEURE_FIN_MODULE, notif.getEndHour());  
        values.put(KEY_NOTIF_CODE, notif.getNotifCode());  
        values.put(KEY_TARGET, notif.getGroup());  
        db.insert(TABLE_NOTIF, 
                null, 
                values); 
         db.close(); 
		}
	}

	public List<NotificationModel> getAllNotifs(){
		   List<NotificationModel> results = new LinkedList<NotificationModel>();
	       String query = "SELECT * FROM NOTIF";
	       SQLiteDatabase db = this.getWritableDatabase();
	       Cursor cursor = db.rawQuery(query, null);
	 
	       if (cursor.moveToFirst()) {
	           do {
	        	   NotificationModel currentnotif = new NotificationModel(cursor.getString(12), cursor.getString(3), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(7), cursor.getString(4), cursor.getString(6), cursor.getString(5));
	        	   currentnotif.setAuthorDateCode(cursor.getString(13), cursor.getString(2), cursor.getString(1));
	        	   if(this.existGroup(currentnotif.getGroup())){
		        	   results.add(currentnotif);

	        	   }
	        	   
	        	   
	           } while (cursor.moveToNext());
	       }
		   
		   
		  return results;
		}

	public void updateGroups(String name,int id){
	    SQLiteDatabase db = this.getWritableDatabase();
	    
	    ContentValues values = new ContentValues();
	    values.put("GROUP_NAME", name);  
	 
	    int i = db.update(TABLE_GROUPS, 
	            values, 
	            KEY_ID+" = ?", 
	            new String[] { String.valueOf(id) });
	 
	    db.close();
	}
	public void deleteGroup(int id){
        SQLiteDatabase db = this.getWritableDatabase();
 
        db.delete(TABLE_GROUPS, 
                KEY_ID+" = ?",  
                new String[] { String.valueOf(id) }); 
         db.close();
	}
	public void deleteGroup(String grName){
        SQLiteDatabase db = this.getWritableDatabase();
 
        db.delete(TABLE_GROUPS, 
        		KEY_NAME+" = ?",  
                new String[] { String.valueOf(grName) }); 
         db.close();
	}

}
