package dao;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.NonNull;

import dbAdapter.DaySQLite;
import entity.Day;


public class DayManager {
    private SQLiteDatabase Database = null;
    private final DaySQLite daySQLite;
    public DayManager(Context context) {
        daySQLite = new DaySQLite(context);
    }

    public SQLiteDatabase open() {

        if (Database == null)
            Database = daySQLite.getWritableDatabase();
        else if (!Database.isOpen())
        {
            Database=null;
            Database = daySQLite.getWritableDatabase();
        }
        return Database;
    }

    public void close() {
        if (Database != null && Database.isOpen())
            Database.close();
    }

    public void create(Day day){
        int id=search(day);
        String query;
        SQLiteStatement statement;
        query="INSERT INTO jour(date_jour) VALUES (?)";
        if(id==-1) {
            statement = Database.compileStatement(query);
            statement.bindString(1, day.getDate());
            statement.executeInsert();
            day.setId(id);

        }
        else
            day.setId(id);

    }
    public int search(@NonNull Day day){
        int id=0;
        String query="SELECT id_jour FROM jour WHERE date_jour=? ";
        String [] sel={day.getDate()};
        Cursor cursor=Database.rawQuery(query,sel);
        if(cursor.getCount()==0)
            id=-1; //The date doesn't exist
        else if(cursor.moveToFirst())
        id=cursor.getInt(0);
        cursor.close();
        return id;
    }







}
