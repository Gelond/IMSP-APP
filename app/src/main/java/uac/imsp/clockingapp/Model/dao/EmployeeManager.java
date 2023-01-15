package dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.NonNull;

import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Objects;

import dbAdapter.EmployeeSQLite;
import entity.Day;
import entity.Employee;
import entity.Planning;
import entity.Service;


public class EmployeeManager {
    public final static int CAN_NOT_LOGIN = 15;
    private SQLiteDatabase Database = null;
    private final EmployeeSQLite employeeSQLite;

    //private Context context;
    public EmployeeManager(Context context) {
        employeeSQLite = new EmployeeSQLite(context);
    }

    public SQLiteDatabase open() {
        if (Database == null )
            Database = employeeSQLite.getWritableDatabase();
        else if (!Database.isOpen())
        {
            Database=null;
            Database = employeeSQLite.getWritableDatabase();
        }
        return Database;
    }

    public void close() {
        if (Database != null && Database.isOpen())
            Database.close();
    }

    public int connectUser(@NonNull Employee employee) {

        int nb_employee;
        String correctPassword , givenPassword = employee.getPassword();
        int regNumber;

        String query = "SELECT matricule,password FROM employe WHERE username=?";


        String[] selectArgs = {employee.getUsername()};

        Cursor cursor = Database.rawQuery(query, selectArgs);
        cursor.moveToFirst();
        nb_employee = cursor.getCount();
        regNumber = cursor.getInt(0);
        correctPassword = cursor.getString(1);//get  encrypted password
        cursor.close();
        if (nb_employee == 1 && correctPassword.equals(getMd5(givenPassword))) {
            employee.setRegistrationNumber(regNumber);
            setInformations(employee);
            return 0;
        }

        return CAN_NOT_LOGIN;

    }
    public String[] retrieveAccount(@NonNull Employee employee){
        String query = "SELECT username,password FROM employe WHERE matricule=?";
        String[] selectArgs = {String.valueOf(employee.getRegistrationNumber())};

        Cursor cursor = Database.rawQuery(query, selectArgs);
        cursor.moveToFirst();

       String username = cursor.getString(0);
        String password = cursor.getString(1);//get  encrypted password
        cursor.close();
        return new String[]{username,password};

    }


    //create,delete,update

    public void create(@NonNull Employee employee) {

        // open();
        SQLiteStatement statement;
        String query = "INSERT INTO employe (matricule,nom,prenom,sexe," +
                "couriel,username,password,type,date_ajout,est_admin) VALUES(?,?,?,?,?,?,?,?,DATE(?,?),?) ";
        statement = Database.compileStatement(query);

        statement.bindLong(1, employee.getRegistrationNumber());

        statement.bindString(2, employee.getLastname());
        statement.bindString(3, employee.getFirstname());
        statement.bindString(4, Character.toString(employee.getGender()));
        statement.bindString(5, employee.getMailAddress());
        statement.bindString(6, employee.getUsername());
        statement.bindString(7, getMd5(employee.getPassword()));
        statement.bindString(8, employee.getType());
        statement.bindString(9, "NOW");
        statement.bindString(10, "LOCALTIME");
        statement.bindString(11, employee.isAdmin()?"true":"false");

        statement.executeInsert();
        //if the birthdate is given
        if (employee.getBirthdate()!=null) {
            query = "UPDATE employe SET birthdate=? WHERE matricule=?";
            statement = Database.compileStatement(query);
            statement.bindString(1, employee.getBirthdate());
            statement.bindLong(2, employee.getRegistrationNumber());
            statement.executeUpdateDelete();
        }

    }
    public void retrieveAddDate(@NonNull Employee employee){
        String querry="SELECT date_ajout FROM employe WHERE matricule=?";
        String[] selectArgs={String.valueOf(employee.getRegistrationNumber())};
        Cursor cursor=Database.rawQuery(querry,selectArgs);
        if(cursor.moveToNext())
            employee.setAddDate(cursor.getString(0));

        cursor.close();
    }

    public void setAccount(@NonNull Employee employee) {

        String query = "SELECT username, password FROM employe WHERE matricule=?";
        String[] selectArgs = {String.valueOf(employee.getRegistrationNumber())};
        Cursor cursor = Database.rawQuery(query, selectArgs);
        if (cursor.moveToFirst()) {
            employee.setUsername(cursor.getString(0));
            employee.setPassword(cursor.getString(1));
        }
        cursor.close();


    }

    public void changePassword(@NonNull Employee employee, String newPassword) {

        String query = "UPDATE employe SET password=? WHERE matricule=?";

        SQLiteStatement statement = Database.compileStatement(query);
        statement.bindString(1, getMd5(newPassword));

        statement.bindLong(2, employee.getRegistrationNumber());

        statement.executeUpdateDelete();


    }
//On peut modifier le courier ou la photo de l'employé


    //update the email of employee
    public void update(@NonNull Employee employee, String mailAddress) {

        String query = "UPDATE employe SET couriel =? WHERE matricule=?";
        SQLiteStatement statement;
        statement = Database.compileStatement(query);
        statement.bindString(1, mailAddress);
        statement.bindLong(2, employee.getRegistrationNumber());
        statement.executeUpdateDelete();
    }


    public void changeGrade(@NonNull Employee employee, String type) {

        String query = "UPDATE employe SET type =? WHERE matricule=?";
        SQLiteStatement statement;
        statement = Database.compileStatement(query);
        statement.bindString(1, type);
        statement.bindLong(2, employee.getRegistrationNumber());
        statement.executeUpdateDelete();
    }

    //To update the picture of the employee
    public void update(@NonNull Employee employee, byte[] picture) {
        String query = "UPDATE employe SET photo =? WHERE matricule=?";
        SQLiteStatement statement;
        statement = Database.compileStatement(query);
        statement.bindBlob(1, picture);
        statement.bindLong(2, employee.getRegistrationNumber());
        statement.executeUpdateDelete();
    }

    public Planning getPlanning(@NonNull Employee employee) {
        Planning planning = null;
        String query = "SELECT heure_debut_officielle,heure_fin_officielle," +
                "jours_de_travail FROM planning  JOIN employe ON " +
                "id_planning=id_planning_ref WHERE matricule=?";
        String[] selectArgs = {String.valueOf(employee.getRegistrationNumber())};
        Cursor cursor = Database.rawQuery(query, selectArgs);
        if (cursor.moveToFirst())


            planning = new Planning(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getBlob(2)
            );
        cursor.close();
        return planning;
    }

    public Service getService(@NonNull Employee employee) {
        Service service;
        String query = "SELECT service.nom  " +
                "FROM employe JOIN service ON id_service=id_service_ref " +
                "WHERE matricule=?";
        String[] selectArgs = {String.valueOf(employee.getRegistrationNumber())};
        Cursor cursor = Database.rawQuery(query, selectArgs);
        cursor.moveToFirst();
        service = new Service(cursor.getString(0));

        cursor.close();
        return service;
    }

    public void delete(@NonNull Employee employee) {
        String query = "DELETE FROM employe WHERE matricule=?";
        SQLiteStatement statement;

        statement = Database.compileStatement(query);
        statement.bindLong(1, employee.getRegistrationNumber());
        statement.executeUpdateDelete();
    }
//Pour rechercher un employé
/*On peut rechercher par matricule,nom,prénom,ou service
//Cette méthode prend la donnée à rechercher et retourne
un tableau contenant les emplyés vérifiant le motif de recherche*/

    public Employee[] search(String data) {
        String query, queryAll;

        queryAll = "SELECT matricule, employe.nom,prenom,photo " +
                "FROM employe JOIN service ON id_service=id_service_ref ";

        query = "SELECT matricule, employe.nom,prenom,photo " +
                "FROM employe JOIN service ON id_service=id_service_ref " +
                "WHERE  matricule||employe.nom||prenom||service.nom " +
                "LIKE '%'||?||'%'";

        String[] selectArg = {data};
        ArrayList<Employee> employeeSet = new ArrayList<>();
        Employee employee;
        Cursor cursor;
        if (Objects.equals(data, "*"))
            cursor = Database.rawQuery(queryAll, null);
        else
            cursor = Database.rawQuery(query, selectArg);

        while (cursor.moveToNext()) {
            employee = new Employee(cursor.getInt(0));
            employee.setLastname(cursor.getString(1));
            employee.setFirstname(cursor.getString(2));
            employee.setPicture(cursor.getBlob(3));
            setStatus(employee);
            employeeSet.add(employee);
        }
        cursor.close();
        return employeeSet.toArray(new Employee[employeeSet.size()]);

    }


    public boolean exists(@NonNull Employee employee) {

        return registrationNumberExists(employee);


    }

    public boolean registrationNumberExists(@NonNull Employee employee) {
        boolean test;
        String query = "SELECT matricule FROM employe WHERE matricule=?";
        String[] selectArg = {Integer.valueOf(employee.getRegistrationNumber()).toString()
        };
        Cursor cursor = Database.rawQuery(query, selectArg);
        test = cursor.moveToFirst();

        cursor.close();

        return test;
    }

    public boolean usernameExists(@NonNull Employee employee) {

        boolean test;
        String query = "SELECT username FROM employe WHERE username =?";
        String[] selectArg = {employee.getUsername()};
        Cursor cursor = Database.rawQuery(query, selectArg);
        test = cursor.moveToFirst();

        cursor.close();
        return test;
    }

    public boolean emailExists(@NonNull Employee employee) {

        boolean test;
        String query = "SELECT couriel FROM employe WHERE couriel =?";
        String[] selectArg = {employee.getMailAddress()};
        Cursor cursor = Database.rawQuery(query, selectArg);
        test = cursor.moveToFirst();
        cursor.close();
        return test;
    }


    public void setInformations(@NonNull Employee employee) {
        String query;
        String[] selectArgs;
        Cursor cursor;


        query = "SELECT nom,prenom,sexe,photo,type,couriel,username,birthdate,jours_de_travail," +
                "est_admin" +
                " FROM employe JOIN planning ON id_planning=id_planning_ref WHERE matricule=?";
        selectArgs = new String[]{
                Integer.valueOf(employee.getRegistrationNumber()).toString()
        };

        cursor = Database.rawQuery(query, selectArgs);
        if (cursor.moveToFirst()) {
            employee.setLastname(cursor.getString(0));
            employee.setFirstname(cursor.getString(1));
            employee.setGender(cursor.getString(2).charAt(0));
            employee.setPicture(cursor.getBlob(3));
            employee.setType(cursor.getString(4));
            employee.setMailAddress(cursor.getString(5));
            employee.setUsername(cursor.getString(6));
            employee.setBirthdate(cursor.getString(7));
            employee.setWorkdays(cursor.getBlob(8));
            employee.setAdmin(Objects.equals(cursor.
                            getString(9),
                    "true"));


        }
        cursor.close();


    }


    public void setInformationsWithoutPiture(@NonNull Employee employee) {

        String query;
        String[] selectArgs;
        Cursor cursor;


        query = "SELECT nom,prenom,sexe,type,couriel," +
                "username,birthdate,est_admin FROM employe WHERE matricule=?";
        selectArgs = new String[]{
                Integer.valueOf(employee.getRegistrationNumber()).toString()
        };

        cursor = Database.rawQuery(query, selectArgs);
        if (cursor.moveToFirst()) {
            //employee.setRegistrationNumber();
            employee.setLastname(cursor.getString(0));
            employee.setFirstname(cursor.getString(1));
            employee.setGender(cursor.getString(2).charAt(0));
            employee.setType(cursor.getString(3));
            employee.setMailAddress(cursor.getString(4));
            employee.setUsername(cursor.getString(5));
            employee.setBirthdate(cursor.getString(6));
            employee.setAdmin(Objects.equals(cursor.getString(7), "true"));


        }
        cursor.close();

    }


    public void update(@NonNull Employee employee, @NonNull Service service) {
        String query = "UPDATE employe SET id_service_ref=? WHERE matricule=?";
        SQLiteStatement statement = Database.compileStatement(query);
        statement.bindLong(1, service.getId());
        statement.bindLong(2, employee.getRegistrationNumber());
        statement.executeUpdateDelete();

    }
    /**
     * Update the planning of the given employee
     * The url argument must specify an absolute <a href="#{@link}">{@link URL}</a>. The name
     * argument is a specifier that is relative to the url argument.
     * <p>
     * This method always returns immediately, whether or not the
     * image exists. When this applet attempts to draw the image on
     * the screen, the data will be loaded. The graphics primitives
     * that draw the image will incrementally paint on the screen.
     *
     * @param  employee  an absolute URL giving the base location of the image
     * @param  planning the location of the image, relative to the url argument
     * @see   Employee
     * @see Planning
     */
    public void update(@NonNull Employee employee, @NonNull Planning planning) {
        String query = "UPDATE employe SET id_planning_ref=? WHERE matricule=?";
        SQLiteStatement statement = Database.compileStatement(query);
        statement.bindLong(1, planning.getId());
        statement.bindLong(2, employee.getRegistrationNumber());
        statement.executeUpdateDelete();

    }

    public int getNumberOfWorkDays(Employee employee, int month, int year){
        Day day=new Day();
        int total=0;
        String[] state = getPresenceReportForEmployee(employee,month,year);

        for(int i=0;i< state.length;i++) {
            if (i == day.getDayOfMonth())
                break;
            if(!Objects.equals(state[i], "Hors service") && !Objects.equals(state[i],"Undefined"))
                total++;
        }
        return total;

    }

    public int[] getAttendanceReportForEmployee(Employee employee, int month, int year){
        int p=0,a=0,r=0;
        int [] report=new int[4];
        String []state=getPresenceReportForEmployee(employee,month,year);
        int total=getNumberOfWorkDays(employee,month,year);
        for (String str : state) {
            if (Objects.equals(str, "Présent"))
                p++;
            else if (str.equals("Absent"))
                a++;
            else if (str.equals("Retard"))
                r++;

        }
            report[0]=p;
            report[1]=a;
            report[2]=r;
            report[3]=total;
            return report;


    }

    // presence report in a month for an employee
    /**Test**/
    public String[] getPresenceReportForEmployee(
            @NonNull Employee employee, int month, int year)  {
        //int nb;
        Hashtable<String,String> status=new Hashtable<>();

        Day day, d;
        String date,state;
        String[] table;
        Cursor cursor;
        String []selectArgs;
        int i;
        day = new Day(month,year);
        d=day;
        table = new String[day.getLenthOfMonth()];
     retrieveAddDate(employee);
        String query="SELECT date_jour,statut FROM pointage WHERE matricule_ref=? " +
                "AND date_jour BETWEEN ? AND ?  ";
        day=new Day(day.getYear(),month,1);
        d=new Day(d.getYear(),month,d.getLenthOfMonth());
        selectArgs=new String[]
                {
                String.valueOf(employee.getRegistrationNumber()),
                        day.getDate(),
                        d.getDate()
        };
         cursor=Database.rawQuery(query,selectArgs );
        while ((cursor.moveToNext()))
        {
            date=cursor.getString(0);
            state=cursor.getString(1);
            status.put(date,state);
        }
   cursor.close();
      day=new Day(day.getYear(),day.getMonth(),1);
        for (i = 0; i < table.length; i++) {
            //to browse the calendar especially the concerned month
            day = new Day(day.getYear(), month, i + 1);
            if(day.getDate().compareTo(employee.getAddDate())<0)
                 table[i]="Undefined";
            else
                 table[i] = status.getOrDefault(day.getDate(), "Undefined");

        }



        return table;
    }

    public boolean isNotSuperUser(@NonNull Employee employee) {
        boolean test;
        String query = "SELECT type FROM employe WHERE matricule=?";
        String[] selectArgs = new String[]{String.valueOf(employee.getRegistrationNumber())};
        Cursor cursor = Database.rawQuery(query, selectArgs);
        test = cursor.moveToFirst() && !Objects.equals(cursor.getString(0), "Simple");
        cursor.close();
        return !test;


    }

    public String getMd5(@NonNull String password) {

        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(password.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            StringBuilder hashtext = new StringBuilder(no.toString(16));
            while (hashtext.length() < 32) {
                hashtext.insert(0, "0");
            }
            return hashtext.toString();
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean shouldNotWorkThatDay(@NonNull Employee employee, @NonNull Day day) {
        Cursor cursor;
        byte[] workDays;
        //byte
        int dayOfWeek=day.getDayOfWeek();
        String query = "SELECT jours_de_travail FROM planning " +
                "JOIN employe ON id_planning=id_planning_ref" +
                " WHERE matricule=?";
        String[] selectArgs = {String.valueOf(employee.getRegistrationNumber())};
        cursor = Database.rawQuery(query, selectArgs);
        cursor.moveToFirst();
        workDays=cursor.getBlob(0);
        cursor.close();
        return workDays[dayOfWeek - 1] != 'T';
    }

    public boolean shouldNotWorkToday(@NonNull Employee employee) {
        Cursor cursor;
        byte[] workDays;
        //byte
        Day day=new Day();
        int dayOfWeek=day.getDayOfWeek();
        String query = "SELECT jours_de_travail FROM planning " +
                "JOIN employe ON id_planning=id_planning_ref" +
                " WHERE matricule=?";
        String[] selectArgs = {String.valueOf(employee.getRegistrationNumber())};
        cursor = Database.rawQuery(query, selectArgs);
        cursor.moveToFirst();
        workDays=cursor.getBlob(0);
        cursor.close();
        return workDays[dayOfWeek - 1] != 'T';
    }
    public void updateCurrentAttendance(@NonNull Employee employee, String status){
        SQLiteStatement statement;
        employee.setCurrentStatus(status);
        String query="UPDATE employe SET statut=? WHERE matricule=?";
        statement=Database.compileStatement(query);
        statement.bindString(1,employee.getCurrentStatus());
        statement.bindLong(2,employee.getRegistrationNumber());
        statement.executeUpdateDelete();

    }
    public void setDayAttendance(@NonNull Employee employee, String status,
                                 @NonNull Day day){
        SQLiteStatement statement;
        String query;

        employee.setCurrentStatus(status);
        updateCurrentAttendance(employee,status);
         query="INSERT INTO pointage (matricule_ref,date_jour,statut,id_jour_ref)" +
                " VALUES(?,?,?,?)";
        statement=Database.compileStatement(query);
        statement.bindLong(1,employee.getRegistrationNumber());
        statement.bindString(2,day.getDate());
        statement.bindString(3,status);
        statement.bindLong(4,day.getId());
        statement.executeInsert();

    }

    public int getStatus(@NonNull Employee employee, @NonNull Day day){
        String status,exitTime = null;
        int index = -1;

        String query = "SELECT statut,heure_sortie FROM pointage " +
                "WHERE matricule_ref=? AND date_jour=?" ;
        String[] selectArgs = {
                String.valueOf(employee.getRegistrationNumber()),
                String.valueOf(day.getDate()),
        };
        Cursor cursor = Database.rawQuery(query, selectArgs);

        if(!cursor.moveToFirst())
            status="Sorti";
        else {
            status = cursor.getString(0);
            exitTime=cursor.getString(1);
        }
        cursor.close();
        if(Objects.equals(status, "Absent"))
            index=1;
        else if(Objects.equals(status, "Hors service"))
            index=3;
        else if(!Objects.equals(exitTime, ""))
            //case "Sorti":
            index=4;
        else if(Objects.equals(status, "Présent"))
            index=0;
        
      else   if(Objects.equals(status, "Retard"))
            index=2;
      
        return index;

    }
//for unit test
    public void setAttendance(@NonNull Employee employee, String status, String date){
        SQLiteStatement statement;
        String query;
        Day day=new Day(date);
        employee.setCurrentStatus(status);
        updateCurrentAttendance(employee,status);
        query="INSERT INTO pointage (matricule_ref,date_jour,statut,id_jour_ref)" +
                " VALUES(?,?,?,?)";
        statement=Database.compileStatement(query);
        statement.bindLong(1,employee.getRegistrationNumber());
        statement.bindString(2,day.getDate());
        statement.bindString(3,status);
        statement.bindLong(4,day.getId());
        statement.executeInsert();

    }
public void updateVariable(){

        Day day=new Day();
String query="UPDATE variable SET last_update=?";
SQLiteStatement statement=Database.compileStatement(query);
statement.bindString(1,day.getDate());
statement.executeUpdateDelete();


}
public String selectVariable(){
        String date="";
    String query="SELECT last_update FROM variable";
    Cursor cursor=Database.rawQuery(query,null);
    if(cursor.moveToFirst())
        date=cursor.getString(0);

    cursor.close();
        return date;
}
    public void setStatus(@NonNull Employee employee){
        Cursor cursor;
        String status;
        String query = "SELECT statut FROM employe WHERE matricule=?";
        String[] selectArgs = {String.valueOf(employee.getRegistrationNumber())};
        cursor = Database.rawQuery(query, selectArgs);
        cursor.moveToFirst();
        status=cursor.getString(0);
        employee.setCurrentStatus(status);
        cursor.close();


    }

}