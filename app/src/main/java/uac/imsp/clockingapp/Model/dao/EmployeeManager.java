package dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.NonNull;

import java.math.BigInteger;
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

/** Represents a database manager for employee's table.
 *<br/>
 * Allows to manage clocking's table<br/>
 * Proposes methods to create, read, update, delete employees<br/>
 * @author Ezéchiel G. ADEDE
 * @version 1.0
 * @since 2023*/
public class EmployeeManager {
    public final static int CAN_NOT_LOGIN = 15;
    /**Represents the database**/
    private SQLiteDatabase Database = null;
    /**Reprents a employeeSQLite object for database configuration**/
    private final EmployeeSQLite employeeSQLite;

    /** A constructor of employee manager : it allows to
     * initialize the database configurator
     * @param context is the application's context
     */
    public EmployeeManager(Context context) {
        employeeSQLite = new EmployeeSQLite(context);
    }
    /**Opens the SQLite database by making it writable**/
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
    /**Closes the SQLite database**/
    public void close() {
        if (Database != null && Database.isOpen())
            Database.close();
    }
/**Retrieves acccount informations of employee for login
 * @param employee  the employee that want to login
 * @return a login code 0 if success and
 *  if failure **/
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

    /**Retrieves acccount informations of employee
     * @param employee  the employee that want to login
     * @return the username and the password
     * of the employeee as a string array**/
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


    /**Creates an employee
     * It requires the employee doesn't exist**/

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
        statement.bindString(8, employee.getFunction());
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
    /** Sets to the meployee object the day the concerned
     *  employee has been created
     *  @param employee the concerned employee**/
    public void retrieveAddDate(@NonNull Employee employee){
        String querry="SELECT date_ajout FROM employe WHERE matricule=?";
        String[] selectArgs={String.valueOf(employee.getRegistrationNumber())};
        Cursor cursor=Database.rawQuery(querry,selectArgs);
        if(cursor.moveToNext())
            employee.setAddDate(cursor.getString(0));

        cursor.close();
    }
/**Sets username and password to employee
 * @param employee the concerned employee**/
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
/**Change the password of the employee
 * @param employee  the concerned employee
 * @param newPassword  the new password**/
    public void changePassword(@NonNull Employee employee, String newPassword) {

        String query = "UPDATE employe SET password=? WHERE matricule=?";

        SQLiteStatement statement = Database.compileStatement(query);
        statement.bindString(1, getMd5(newPassword));

        statement.bindLong(2, employee.getRegistrationNumber());

        statement.executeUpdateDelete();


    }
/**Changes the pmai address of the employee
 * @param employee  the concerned employee
 * @param mailAddress  the new mail address**/
    public void update(@NonNull Employee employee, String mailAddress) {

        String query = "UPDATE employe SET couriel =? WHERE matricule=?";
        SQLiteStatement statement;
        statement = Database.compileStatement(query);
        statement.bindString(1, mailAddress);
        statement.bindLong(2, employee.getRegistrationNumber());
        statement.executeUpdateDelete();
    }

/**Changes the function of the employee
 * @param employee the concerned
 * @param function the new function occupied by the employee**/
    public void changeFunction(@NonNull Employee employee, String function) {

        String query = "UPDATE employe SET type =? WHERE matricule=?";
        SQLiteStatement statement;
        statement = Database.compileStatement(query);
        statement.bindString(1, function);
        statement.bindLong(2, employee.getRegistrationNumber());
        statement.executeUpdateDelete();
    }

/**Make an employee admin or not admin
 * @param employee  thhe concerned employeee
 * @param isAdmin if true the employee becomes admin
 * if false he becomes not admin**/
    public void setAdmin(@NonNull Employee employee, boolean isAdmin) {

        String query = "UPDATE employe SET est_admin =? WHERE matricule=?";
        SQLiteStatement statement;
        statement = Database.compileStatement(query);
        statement.bindString(1,isAdmin?"true":"false" );
        statement.bindLong(2, employee.getRegistrationNumber());
        statement.executeUpdateDelete();
    }
    /**To update the picture of the employee
     * @param employee  the concerned employee
     * @param picture the new picture of the employee**/
    public void update(@NonNull Employee employee, byte[] picture) {
        String query = "UPDATE employe SET photo =? WHERE matricule=?";
        SQLiteStatement statement;
        statement = Database.compileStatement(query);
        statement.bindBlob(1, picture);
        statement.bindLong(2, employee.getRegistrationNumber());
        statement.executeUpdateDelete();
    }
/**Gets the planning of the employee
 * @param employee the employee whose planning is gonna to be retrieved
 * @return the planning of the employee**/
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
    /**Gets the service of the employee
     * @param employee the employee whose service is gonna to be retrieved
     * @return the service of the employee**/
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
/**Deletes an employee
 * @param employee the concerned employee**/
    public void delete(@NonNull Employee employee) {
        String query = "DELETE FROM employe WHERE matricule=?";
        SQLiteStatement statement;

        statement = Database.compileStatement(query);
        statement.bindLong(1, employee.getRegistrationNumber());
        statement.executeUpdateDelete();
    }
    /**Search employee(s) by  registration number,
     *  firstname, lastname or service
     * @param data a string, if *,all the employee are founded
     * @return  the employees table matching the search**/
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

        return  employeeSet.toArray(new Employee[0]);

    }

/**Checks if an employee exists
 * @param employee the concerned employee**/
    public boolean exists(@NonNull Employee employee) {

        return registrationNumberExists(employee);


    }
/**Checks if the registration number of the given
 * employee already exists
 * @param employee  the concerned employee
 * @return  true if the number exists and false otherwise**/
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
    /**Checks if the username of the given
     * employee already exists
     * @param employee  the concerned employee
     * @return  true if the username exists and false otherwise**/
    public boolean usernameExists(@NonNull Employee employee) {

        boolean test;
        String query = "SELECT username FROM employe WHERE username =?";
        String[] selectArg = {employee.getUsername()};
        Cursor cursor = Database.rawQuery(query, selectArg);
        test = cursor.moveToFirst();

        cursor.close();
        return test;
    }
/**Updates attendence status by setting it to holiday
 *  for every employee the given day
 * @param day the concerned day***/
    public void holiday( @NonNull Day day) {
        SQLiteStatement statement;
        String query;
        Employee[] employees=search("*");
        query = "UPDATE  pointage SET statut=?" +
                " WHERE matricule_ref=? AND id_jour_ref=? ";
        statement = Database.compileStatement(query);
        statement.bindString(1,"Férié");
        statement.bindLong(3, day.getId());
        for(Employee emp:employees) {
            statement.bindLong(2, emp.getRegistrationNumber());
            statement.executeUpdateDelete();
        }
    }
    /**Checks if the mail address of the given
     * employee already exists
     * @param employee  the concerned employee
     * @return  true if the mail address exists and false otherwise**/
    public boolean emailExists(@NonNull Employee employee) {

        boolean test;
        String query = "SELECT couriel FROM employe WHERE couriel =?";
        String[] selectArg = {employee.getMailAddress()};
        Cursor cursor = Database.rawQuery(query, selectArg);
        test = cursor.moveToFirst();
        cursor.close();
        return test;
    }

/**Sets informations such as firstname, lastname,
 * gender, picture, function, mail address, birthdate,
 * work days, admin to the given employee
 * @param employee the concerned employee**/
    public void setInformations(@NonNull Employee employee) {
        String query;
        String[] selectArgs;
        Cursor cursor;


        query = "SELECT nom,prenom,sexe,photo,type,couriel,username,birthdate," +
                "jours_de_travail," +
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
            employee.setFunction(cursor.getString(4));
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
    /**Sets informations'without picture) such as firstname, lastname,
     * gender, function, mail address, birthdate,
     * work days, admin to the given employee
     * @param employee the concerned employee**/

    public void setInformationsWithoutPicture(@NonNull Employee employee) {

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
            employee.setFunction(cursor.getString(3));
            employee.setMailAddress(cursor.getString(4));
            employee.setUsername(cursor.getString(5));
            employee.setBirthdate(cursor.getString(6));
            employee.setAdmin(Objects.equals(cursor.getString(7), "true"));


        }
        cursor.close();

    }

/**Update the service of an employee
 * @param employee the concerned employee
 * @param service the new service to be settled to the employee**/
    public void update(@NonNull Employee employee, @NonNull Service service) {
        String query = "UPDATE employe SET id_service_ref=? WHERE matricule=?";
        SQLiteStatement statement = Database.compileStatement(query);
        statement.bindLong(1, service.getId());
        statement.bindLong(2, employee.getRegistrationNumber());
        statement.executeUpdateDelete();

    }
    /**Update the planning of an employee
     * @param employee the concerned employee
     * @param planning the new planning to be settled to the employee**/
    public void update(@NonNull Employee employee, @NonNull Planning planning) {
        String query = "UPDATE employe SET id_planning_ref=? WHERE matricule=?";
        SQLiteStatement statement = Database.compileStatement(query);
        statement.bindLong(1, planning.getId());
        statement.bindLong(2, employee.getRegistrationNumber());
        statement.executeUpdateDelete();

    }
    /**Gets the number of workdays of the employee in the given month
     * @param employee the concerned employee
     * @param month  the concerned month
     * @param year the concerned year
     * @return  the number of times the employe is
     * attended to work in the given month
     * of yhe given year**/
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
    /**Gets the attendance report of an employee in a given month
     * @param employee  the concerned employee
     * @param month the concerned month
     * @param  year the concerned year
     * #return this report as a integer array
     *whith a lenght as same as the number of*
     * days in the concernded month**/
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
    /**Gets the attendance report of an imployee in a given month
     * @param employee  the concerned employee
     * @param month the concerned month
     * @param  year the concerned year
     * #return this report as a string array
     *whith a lenght as same as the number of*
     * days in the concernded month**/
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
/**Uses md5 algorithm to encrypt a password
 * @param password the password to be encrypted
 * @return the encrypted password**/
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
/**Checks if an employee should work the given day or not
 * @param employee the concerned employee
 * @param day the day
 * @return true if the employee shouldn't work and false else**/
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
    /**Checks if an employee should work the current day or not
     * @param employee the concerned employee
     * @return true if the employee shouldn't work and false else**/
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
    /**Updates the current attendance status of an employee
     * @param employee the concerned employee
     * @param status the status to be settle
     * **/
    public void updateCurrentAttendance(@NonNull Employee employee, String status){
        SQLiteStatement statement;
        employee.setCurrentStatus(status);
        String query="UPDATE employe SET statut=? WHERE matricule=?";
        statement=Database.compileStatement(query);
        statement.bindString(1,employee.getCurrentStatus());
        statement.bindLong(2,employee.getRegistrationNumber());
        statement.executeUpdateDelete();

    }


    /**Initializes the attendance of employees with status the given date
     * @param employee  the concerned employees
     *whose attendance is gonna be initialized
     *@param day the concerned day
     *@param status the status to settle**/
    public void initDayAttendance(@NonNull Employee employee, String status,
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
/**Gets the attendance status of an employee the given day
 * @param employee the concerned employee
 * @param day the concernded day
 * @return  an integer matching the status**/
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

        else   if(Objects.equals(status, "Férié"))
            index=5;
      
        return index;

    }
/**Sets attendance status of employee at the given date
 * @param employee  the concernde employee
 * @param status the status
 * @param date the concerned date in the format YYYY-MM-dd**/
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

    /**Uate the last day employees status has been updated
     * by setting it to the current date**/
public void updateVariable(){

        Day day=new Day();
String query="UPDATE variable SET last_update=?";
SQLiteStatement statement=Database.compileStatement(query);
statement.bindString(1,day.getDate());
statement.executeUpdateDelete();


}
/**Gets the last day employees status has been updated
 * @return this date as a string in the format YYYY-MM-dd**/
public String selectVariable(){
        String date="";
    String query="SELECT last_update FROM variable";
    Cursor cursor=Database.rawQuery(query,null);
    if(cursor.moveToFirst())
        date=cursor.getString(0);

    cursor.close();
        return date;
}
/**Sets the current attendance status of the employee
 * @param employee the concerned employee**/
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
/**Initializes the attendance of employees with status the given date
 * @param employees  the table of the employees
 *whose attendance are gonna be initialized**/
    public void initDayAttendance(@NonNull Employee[] employees, String status, Day day) {
        for(Employee employee:employees)
            initDayAttendance(employee,status,day);
    }
/**Gets the stored registration numbers
 * returns these numbers as table of integers**/
    public int[] getRegNumbers() {
        Cursor cursor;
        String query = "SELECT matricule FROM employe";
        cursor = Database.rawQuery(query, null);
        int [] regNumbers = new int[cursor.getCount()];
        int i=0;
         while (cursor.moveToNext())
         {
             regNumbers[i]=cursor.getInt(0);
             i++;
         }
        cursor.close();
         return  regNumbers;
    }
}