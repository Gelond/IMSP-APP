package dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.NonNull;

import java.util.Arrays;

import dbAdapter.PlanningSQLite;
import entity.Planning;
/** Represents a database manager for employee's table.
 *<br/>
 * Allows to manage plannning's table<br/>
 * Proposes methods to manage employee's planning<br/>
 * @author Ezéchiel G. ADEDE
 * @version 1.0
 * @since 2023*/
public class PlanningManager {
    /**Represents the database**/
    private SQLiteDatabase Database = null;
    /**Reprents a planningSQLite object for database configuration**/
    private final PlanningSQLite planningSQLite;
    /** A constructor of planning manager : it allows to
     * initialize the database configurator
     * @param context is the application's context
     */
    public PlanningManager(Context context) {
        planningSQLite = new PlanningSQLite(context);
    }
    /**Opens the SQLite database by making it writable**/
    public SQLiteDatabase open() {

        if (Database == null)
            Database = planningSQLite.getWritableDatabase();
        else if (!Database.isOpen())
        {
            Database=null;
            Database = planningSQLite.getWritableDatabase();
        }
        return  Database;
    }
    /**Closes the SQLite database**/
    public void close() {
        if (Database != null && Database.isOpen())
            Database.close();
    }
/**Deletes a planning
 * @param planning the concernded planning**/
    public void delete(@NonNull Planning planning) {
  String query="DELETE FROM planning WHERE id_planning=?";
        SQLiteStatement statement=Database.compileStatement(query);
        statement.bindLong(1,planning.getId());
        statement.executeUpdateDelete();
    }

    /**Creates a planning if it doen't exist
     * @param planning the concernded planning**/
    public void create(Planning planning) {
        String query;
        SQLiteStatement statement;
        if(!searchPlanning(planning))
        {
       query="INSERT INTO planning(heure_debut_officielle,heure_fin_officielle," +
               "jours_de_travail) VALUES (?,?,?)";
      statement=Database.compileStatement(query);
      statement.bindString(1,planning.getStartTime());
      statement.bindString(2,planning.getEndTime());
      statement.bindBlob(3, planning.getWorkDays());
      statement.executeInsert();
      //The planning exists now
            //We'll search it so as to setId

            searchPlanning(planning);

        }
    }
    /**searches a planning
     * @param planning the concernded planning
     * @return  true if the planning exists and false otherwise**/
    public boolean searchPlanning(@NonNull Planning planning){
        byte[] retrievedData;
        byte[] workdays=planning.getWorkDays();

        String query="SELECT id_planning,jours_de_travail FROM planning" +
                " WHERE heure_debut_officielle=? " +
                "AND heure_fin_officielle=?" ;
                String [] selectArgs={
                planning.getStartTime(),planning.getEndTime(),

        };
        Cursor cursor=Database.rawQuery(query,selectArgs);
        int id;

        while (cursor.moveToNext())
        {
            id= (int) cursor.getLong(0);
          retrievedData=cursor.getBlob(1)  ;
          if(Arrays.equals(retrievedData, workdays))
          {
              cursor.close();
              planning.setId(id);
              return true;
          }

        }
        return false;

        }

}
