package dao;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.NonNull;

import java.util.Objects;

import dbAdapter.DaySQLite;
import entity.Day;

/** Represents a database manager for day's table.
 *<br/>
 * Allows to manage clocking's table<br/>
 * Proposes methods to create new days, search days and manage holidays easily<br/>
 * @author Ezéchiel G. ADEDE
 * @version 1.0
 * @since 2023*/
public class DayManager {
    /**Represents the database**/
    private SQLiteDatabase Database = null;
    /**Reprents a daySQLite object for database configuration**/
    private final DaySQLite daySQLite;
    /** A constructor of day manager : it allows to
     * initialize the database configurator
     * @param context is the application's context
     */
    public DayManager(Context context) {
        daySQLite = new DaySQLite(context);
    }
    /**Opens the SQLite database by making it writable**/
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

    /**Closes the SQLite database**/
    public void close() {
        if (Database != null && Database.isOpen())
            Database.close();
    }
/**Creates a new day if it doesn't exist
 * @param day the day to create**/
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
    
    /**Seach a day: checked it's already created, that's
     *  to say if it has already inserted into the database
     *@param day the day to search
     * @return -1 if the day doesn't exist and the day's id if it exists**/
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
    /**Marks a day as a holiday
     * @param day  the day to be maked as a holiday**/
    public void setHoliday(@NonNull Day day) {
        String query="UPDATE jour SET ferie=? WHERE id_jour=?";
        SQLiteStatement statement=Database.compileStatement(query);
        statement.bindString(1,"true");
        statement.bindLong(2,day.getId());
        statement.executeInsert();

    }
/**Checks if the given day is a holiday ior not
 * @param day the concerned day
 * @return true if the given day is a holiday and false otherwise**/
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
