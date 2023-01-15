package dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.NonNull;

import dbAdapter.ClockingSQLite;
import entity.Day;
import entity.Employee;


public class ClockingManager {

    private SQLiteDatabase Database = null;

    private final ClockingSQLite clockingSQLite;

    public ClockingManager(Context context) {

        clockingSQLite = new ClockingSQLite(context);
    }


    public SQLiteDatabase    open() {
        if (Database == null)
            Database = clockingSQLite.getWritableDatabase();
        else if (!Database.isOpen())
        {
            Database=null;
            Database = clockingSQLite.getWritableDatabase();
        }
        return Database;
    }

    public void close() {
        if (Database != null && Database.isOpen())
            Database.close();
    }
/**This od allows us to clock the given employee in it
 * requires the employee wotks the given day**/
    public void clockIn(@NonNull Employee employee, @NonNull Day day) {
        Cursor cursor;
        String currentEntryTime;
        String query;

         query="SELECT STRFTIME('%H:%M', 'NOW','LOCALTIME');"; //get the current time
        cursor=Database.rawQuery(query,null);
        cursor.moveToFirst();
        currentEntryTime=cursor.getString(0);
        cursor.close();
        clockIn(employee,day,currentEntryTime);


    }

    public String getAttendedEntryTime(@NonNull Employee employee){
      String   query="SELECT heure_debut_officielle FROM planning " +
                "JOIN employe ON id_planning=id_planning_ref" +
                " WHERE matricule=?";
       String[] selectArgs= new String[]{String.valueOf(employee.getRegistrationNumber())};
       Cursor cursor=Database.rawQuery(query,selectArgs);
        cursor.moveToFirst();
       String attendedEntryTime=cursor.getString(0);
        cursor.close();
        return  attendedEntryTime;

    }

/**j*/
    public void clockIn(@NonNull Employee employee, @NonNull Day day, String time) {
        String attendedEntryTime;
        String currentEntryTime;
        int id;
        SQLiteStatement statement;
        String query;
        id=day.getId();
        currentEntryTime=time;
        attendedEntryTime=getAttendedEntryTime(employee);


        String status;
        if(currentEntryTime.compareTo(attendedEntryTime)<=0)

            status="Présent";
        else
            status="Retard";

        query = "UPDATE  pointage SET heure_entree=?, statut=?" +
                " WHERE matricule_ref=? AND id_jour_ref=? ";

        statement = Database.compileStatement(query);
        statement.bindString(1,time);
        statement.bindString(2,status);
        statement.bindLong(3, employee.getRegistrationNumber());
        statement.bindLong(4, id);

        statement.executeUpdateDelete();





    }

    /*public void update(Employee employee, String status,String date){

    }

    public void updateDailyAttendance(@NonNull Employee employee, String status){

        String query="UPDATE pointage SET statut=?" +
                " WHERE matricule_ref=? AND date_jour=STRFTIME('%H:%M', ?,?)";
        SQLiteStatement statement=Database.compileStatement(query);
        statement.bindString(1,status);
        statement.bindLong(2,employee.getRegistrationNumber());
        statement.bindString(3,"NOW");
        statement.bindString(4,"LOCALTIME");
        statement.executeUpdateDelete();

    }*/

    //for clocking out
    public void clockOut(@NonNull Employee employee, @NonNull Day day) {
        int id;
        id=day.getId();


        SQLiteStatement statement;
        String query = "UPDATE pointage set heure_sortie=? WHERE matricule_ref=?" +
                " AND id_jour_ref=?";
        statement = Database.compileStatement(query);
        statement.bindString(1, "STRFTIME('%H:%M', 'NOW','LOCALTIME')");
        statement.bindLong(2, employee.getRegistrationNumber());
        statement.bindLong(3, id);
        statement.executeUpdateDelete();

     employee.setCurrentStatus("Sortie");
        query="UPDATE employee SET statut=? WHERE matricule=?";
        statement=Database.compileStatement(query);
        statement.bindString(1,employee.getCurrentStatus());
        statement.bindLong(2,employee.getRegistrationNumber());
        statement.executeUpdateDelete();

    }
/**This method check if the given employee has(already) clocked in the given day**/
    public boolean hasNotClockedIn(@NonNull Employee employee, @NonNull Day day) {

        int n;
        String query = "SELECT * FROM pointage " +
                "WHERE matricule_ref=? AND id_jour_ref=? " +
                " AND heure_entree IS NOT NULL" ;
        String[] selectArgs = {
                String.valueOf(employee.getRegistrationNumber()),
                String.valueOf(day.getId()),
        };
        Cursor cursor = Database.rawQuery(query, selectArgs);
        cursor.moveToFirst();

          n=cursor.getCount();
          cursor.close();
        return n == 0;
    }



    public boolean hasNotClockedOut(@NonNull Employee employee, @NonNull Day day) {
        String out;
int id=day.getId(),n;
        String query = "SELECT heure_sortie FROM pointage WHERE " +
                "matricule_ref=? AND id_jour_ref=?";
        String[] selectArgs = {
                String.valueOf(employee.getRegistrationNumber()),String.valueOf(id)
        };
        Cursor cursor = Database.rawQuery(query, selectArgs);
        cursor.moveToFirst();
        n=cursor.getCount();
        out=cursor.getString(0);
        cursor.close();
        return n == 0 || out.equals("");
    }


}