package dbAdapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


public class Database  extends SQLiteAssetHelper {
	private static final String DATABASE_NAME = "northwind.db";
	private static final int DATABASE_VERSION = 2;
	public Database(Context context, String name, String storageDirectory, SQLiteDatabase.CursorFactory factory, int version) {
		super(context, name, storageDirectory, factory, version);
	}
 public Database(Context context){
	 super(context,"",null,1);
	 //setForcedUpgrade();


	 // you can use an alternate constructor to specify a database location
	 // (such as a folder on the sd card)
	 // you must ensure that this folder is available and you have permission
	 // to write to it
	 //super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath(), null, DATABASE_VERSION);

	 // call this method to force a database overwrite every time the version number increments:
	 //setForcedUpgrade();

	 // call this method to force a database overwrite if the version number
	 // is below a certain threshold:
	 //setForcedUpgrade(2);

 }

}


