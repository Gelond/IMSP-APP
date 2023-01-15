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
/**This od allows us to clock the given employee in it requires the employee wotks the given day**/
    public void clockIn(@NonNull Employee employee, @NonNull Day day) {
        Cursor cursor;
        String attendedEntryTime;
        String currentEntryTime;
        String [] selectArgs;
        int id;
        SQLiteStatement statement;
        String query;
        id=day.getId();

         query="SELECT TIME('NOW','LOCALTIME')"; //get the current time
        cursor=Database.rawQuery(query,null);
        cursor.moveToFirst();
        currentEntryTime=cursor.getString(0);
        cursor.close();


         query = "UPDATE  pointage SET  heure_entree=DATE(?,?)" +
                 " WHERE matricule_ref=? AND date_jour=? ";

        statement = Database.compileStatement(query);
        statement.bindLong(1,id);
        statement.bindString(2,"NOW");
        statement.bindString(3,"LOCALTIME");
        statement.bindLong(4, employee.getRegistrationNumber());
        statement.bindString(5, day.getDate());

        statement.executeUpdateDelete();
        //update the current state of the employee
       //we wanna get the attended entry time of the employee
        query="SELECT heure_debut_officielle FROM planning " +
                "JOIN employe ON id_planning=id_planning_ref" +
                " WHERE matricule=?";
        selectArgs= new String[]{String.valueOf(employee.getRegistrationNumber())};
        cursor=Database.rawQuery(query,selectArgs);
        cursor.moveToFirst();
            attendedEntryTime=cursor.getString(0);
        if(currentEntryTime.compareTo(attendedEntryTime)<=0)

            employee.setCurrentStatus("Présent");
        else
            employee.setCurrentStatus("Retard");


        cursor.close();
        updateDailyAttendance(employee,employee.getCurrentStatus());


        query="UPDATE employe SET statut=? WHERE matricule=?";
        statement=Database.compileStatement(query);
        statement.bindString(1,employee.getCurrentStatus());
        statement.bindLong(2,employee.getRegistrationNumber());
        statement.executeUpdateDelete();


    }


    public void clockIn(@NonNull Employee employee, @NonNull Day day, String time) {
        Cursor cursor;
        String attendedEntryTime;
        String currentEntryTime;
        String [] selectArgs;
        int id;
        SQLiteStatement statement;
        String query;
        id=day.getId();


        currentEntryTime=time;



        query = "UPDATE  pointage SET id_jour_ref=? AND heure_entree=?" +
                " WHERE matricule_ref=? AND date_jour=? ";

        statement = Database.compileStatement(query);
        statement.bindLong(1,id);
        statement.bindString(2,time);
        statement.bindLong(3, employee.getRegistrationNumber());
        statement.bindString(4, day.getDate());

        statement.executeUpdateDelete();
        //update the current state of the employee
        //we wanna get the attended entry time of the employee
        query="SELECT heure_debut_officielle FROM planning " +
                "JOIN employe ON id_planning=id_planning_ref" +
                " WHERE matricule=?";
        selectArgs= new String[]{String.valueOf(employee.getRegistrationNumber())};
        cursor=Database.rawQuery(query,selectArgs);
        cursor.moveToFirst();
        attendedEntryTime=cursor.getString(0);
        cursor.close();
        String status;
        if(currentEntryTime.compareTo(attendedEntryTime)<=0)

            status="Présent";
        else
          status="Retard";



        updateAttendance(employee,status,day.getDate());



    }
//for android unit test
    public void updateAttendance(@NonNull Employee employee, String status, String date){
        String query="UPDATE pointage SET statut=?" +
                " WHERE matricule_ref=? AND date_jour=?";
        SQLiteStatement statement=Database.compileStatement(query);
        statement.bindString(1,status);
        statement.bindLong(2,employee.getRegistrationNumber());
        statement.bindString(3,date);
        statement.executeUpdateDelete();

    }




    public void updateDailyAttendance(@NonNull Employee employee, String status){
        String query="UPDATE pointage SET statut=?" +
                " WHERE matricule_ref=? AND date_jour=DATE(?,?)";
        SQLiteStatement statement=Database.compileStatement(query);
        statement.bindString(1,status);
        statement.bindLong(2,employee.getRegistrationNumber());
        statement.bindString(3,"NOW");
        statement.bindString(4,"LOCALTIME");
        statement.executeUpdateDelete();

    }

    //for clocking out
    public void clockOut(@NonNull Employee employee, @NonNull Day day) {
        int id;
        id=day.getId();


        SQLiteStatement statement;
        String query = "UPDATE pointage set heure_sortie=? WHERE matricule_ref=?" +
                " AND date_jour_ref=?";
        statement = Database.compileStatement(query);
        statement.bindString(1, "TIME('NOW','LOCALTIME')");
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
        String query = "SELECT heure_entree FROM pointage " +
                "WHERE matricule_ref=? AND id_jour_ref=? " +
                " AND heure_entree IS NOT NULL AND statut!=?" ;
        String[] selectArgs = {
                String.valueOf(employee.getRegistrationNumber()),
                String.valueOf(day.getId()),
                "Absent"
        };
        Cursor cursor = Database.rawQuery(query, selectArgs);

          n=cursor.getCount();
          cursor.close();
        return n == 0;
    }



    public boolean hasNotClockedOut(@NonNull Employee employee, @NonNull Day day) {

       // open();
        String out;
int id=day.getId(),n;
        String query = "SELECT heure_sortie FROM pointage WHERE matricule_ref=? AND id_jour_ref=?";
        String[] selectArgs = {
                String.valueOf(employee.getRegistrationNumber()),String.valueOf(id)
        };
        Cursor cursor = Database.rawQuery(query, selectArgs);
        cursor.moveToFirst();
        n=cursor.getCount();
        out=cursor.getString(3);
        cursor.close();
        return n == 0 || out.equals("");
    }


}