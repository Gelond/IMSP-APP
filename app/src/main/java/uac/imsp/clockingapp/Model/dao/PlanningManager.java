package dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.NonNull;

import java.util.Arrays;

import dbAdapter.PlanningSQLite;
import entity.Planning;

public class PlanningManager {
    private SQLiteDatabase Database = null;
    private final PlanningSQLite planningSQLite;

    public PlanningManager(Context context) {
        planningSQLite = new PlanningSQLite(context);
    }

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

    public void close() {
        if (Database != null && Database.isOpen())
            Database.close();
    }

    public boolean delete(@NonNull Planning planning) {
  String query="DELETE FROM planning WHERE id_planning=?";
        SQLiteStatement statement=Database.compileStatement(query);
        statement.bindLong(1,planning.getId());
        statement.executeUpdateDelete();
        return true;
    }


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
    //setl'id du planning  puis retourne true s'il existe et false sinon
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
