package dao;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.NonNull;

import java.util.Objects;

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

        if(id==-1) {
            String query;
            SQLiteStatement statement;
            query="INSERT INTO jour(date_jour) VALUES (?)";
            statement = Database.compileStatement(query);
            statement.bindString(1, day.getDate());
            statement.executeInsert();
           id= search(day);
           assert id!=-1;
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


    public void setHoliday(@NonNull Day day) {
        String query="UPDATE jour SET ferie=? WHERE id_jour=?";
        SQLiteStatement statement=Database.compileStatement(query);
        statement.bindString(1,"true");
        statement.bindLong(2,day.getId());
        statement.executeInsert();

    }

    public boolean isHoliday(@NonNull Day day) {
        boolean isHoliday;
        String query="SELECT ferie FROM jour WHERE id_jour=? ";
        String [] sel={String.valueOf(day.getId())};
        Cursor cursor=Database.rawQuery(query,sel);
        cursor.moveToFirst();
        isHoliday= Objects.equals(cursor.getString(0), "true");
        cursor.close();
        return isHoliday;

    }
}
