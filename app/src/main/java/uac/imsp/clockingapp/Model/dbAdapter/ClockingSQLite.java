package dbAdapter;

import static dbAdapter.DaySQLite.ALTER_DAY_TO_DAY_TEMP;
import static dbAdapter.DaySQLite.COPY_DAY_TEMP_TO_DAY;
import static dbAdapter.DaySQLite.CREATE_DAY;
import static dbAdapter.DaySQLite.DROP_DAY_TEMP;
import static dbAdapter.EmployeeSQLite.ALTER_EMPLOYEE_TO_EMPLOYEE_TEMP;
import static dbAdapter.EmployeeSQLite.ALTER_VARIABLE_TO_VARIABLE_TEMP;
import static dbAdapter.EmployeeSQLite.COPY_EMPLOYE_TEMP_TO_EMPLOYE;
import static dbAdapter.EmployeeSQLite.COPY_VARIABLE_TEMP_TO_VARIABLE;
import static dbAdapter.EmployeeSQLite.CREATE_EMPLOYEE;
import static dbAdapter.EmployeeSQLite.CREATE_VARIABLE;
import static dbAdapter.EmployeeSQLite.DATABASE_VERSION;
import static dbAdapter.EmployeeSQLite.DROP_EMPLOYEE_TEMP;
import static dbAdapter.EmployeeSQLite.DROP_VARIABLE_TEMP;
import static dbAdapter.EmployeeSQLite.super_user;
import static dbAdapter.PlanningSQLite.ALTER_PLANNING_TO_PLANNING_TEMP;
import static dbAdapter.PlanningSQLite.COPY_PLANNING_TEMP_TO_PLANNING;
import static dbAdapter.PlanningSQLite.CREATE_PLANNING;
import static dbAdapter.PlanningSQLite.DROP_PLANNING_TEMP;
import static dbAdapter.PlanningSQLite.planning;
import static dbAdapter.ServiceSQLite.ALTER_SERVICE_TO_PLANNING_TEMP;
import static dbAdapter.ServiceSQLite.COPY_SERVICE_TEMP_TO_SERVICE;
import static dbAdapter.ServiceSQLite.CREATE_SERVICE;
import static dbAdapter.ServiceSQLite.DROP_SERVICE_TEMP;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.NonNull;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ClockingSQLite  extends SQLiteOpenHelper {

    ///private static final String DB_PATH = "/data/data/uac.imsp.clockingapp/databases/";
    private static final String DATABASE_NAME = "Clocking_database.db";


       // public static final int DATABASE_VERSION = 2;


    private static final String TABLE_POINTAGE = "pointage";

    private static final String COL_ID_POINTAGE = "id_pointage";
    private static final String COL_DATE_JOUR= "date_jour";
    private static final String COL_ID_JOUR_REF = "id_jour_ref";
    private static final String COL_ID_JOUR = "id_jour";
    private static final String COL_HEURE_ENTREE = "heure_entree";
    private static final String COL_HEURE_SORTIE= "heure_sortie";
    private static final String COL_MATRICULE="matricule";
    private static  final String COL_MATRICULE_REF="matricule_ref";
    private static final String COL_STATUT="statut";
           public static final String CREATE_CLOCKING = "CREATE TABLE IF NOT EXISTS " +

            TABLE_POINTAGE + " (" +
            COL_ID_POINTAGE + " INTEGER NOT NULL  PRIMARY KEY AUTOINCREMENT, " +
                   COL_ID_JOUR_REF + " TEXT    ," +
                   COL_DATE_JOUR+" TEXT,"+
            COL_HEURE_ENTREE + "   TEXT ," +
            COL_HEURE_SORTIE + " TEXT ," +
                   COL_STATUT+" TEXT DEFAULT NULL ,"+
            COL_MATRICULE_REF+ " INTEGER NOT NULL ," +

               " FOREIGN KEY(" + COL_MATRICULE_REF +
               " ) REFERENCES employe(" + COL_MATRICULE+" ),"+
        "  FOREIGN KEY(" + COL_ID_JOUR_REF +
            " ) REFERENCES jour(" + COL_ID_JOUR+" ))" ;
           //public static final String DROP_CLOCKING="DROP TABLE  IF EXISTS "+TABLE_POINTAGE;
    private static final String TABLE_POINTAGE_TEMP =TABLE_POINTAGE+"TEMP";
    public static final String DROP_CLOCKING_TEMP="DROP TABLE  IF EXISTS "+TABLE_POINTAGE_TEMP;
public static final String ALTER_POINTAGE_TO_POINTAGE_TEMP="ALTER TABLE "+TABLE_POINTAGE+
        " RENAME TO "+TABLE_POINTAGE_TEMP;

    public static final String COPY_CLOCKING_TEMP_TO_CLOCKING ="INSERT INTO "+TABLE_POINTAGE+" SELECT * FROM  "+TABLE_POINTAGE_TEMP;
    public ClockingSQLite(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        upgradeDatabase(db);


    }
    public void createDatabase(@NonNull SQLiteDatabase db){

        db.execSQL(CREATE_SERVICE);
        db.execSQL(CREATE_PLANNING);
        db.execSQL(CREATE_DAY);
        db.execSQL(CREATE_EMPLOYEE);
        db.execSQL(CREATE_CLOCKING);
        db.execSQL(CREATE_VARIABLE);
        SQLiteStatement statement= db.compileStatement(super_user);
        statement.bindLong(1,1);
        statement.bindString(2,"User10");
        statement.bindString(3, getMd5("Aab10%"));
        statement.bindString(4,"Directeur");
        statement.bindString(5,"M");
        statement.bindString(6,"super@gmail.com");
        statement.bindLong(7,1);
        statement.bindLong(8,1);
        statement.bindString(9,"AKOBA");
        statement.bindString(10,"Patrick");
        statement.bindString(11,"1970-01-01");
        statement.bindString(12,"true");
       // statement.bindString(12,"DATE('NOW','LOCALTIME'))");
        statement.executeInsert();
        String service = "INSERT INTO service(nom)  VALUES (?)";
        statement= db.compileStatement(service);
        //Direction
        statement.bindString(1,"Direction");
        statement.executeInsert();

        //Service scolarité
        statement.bindString(1,"Service scolarité");
        statement.executeInsert();

        //Secrétariat administratif
        statement.bindString(1,"Secrétariat administratif");
        statement.executeInsert();
        //Comptabilité
        statement.bindString(1,"Comptabilité");
        statement.executeInsert();
        //Service de coopération
        statement.bindString(1,"Service de coopération");
        statement.executeInsert();



        statement= db.compileStatement(planning);
        byte[] workDays= new byte[]{'T','T','T','T','T','F','F'};


        //08-17
        statement.bindString(1,"08:00");
        statement.bindString(2,"17:00");
        statement.bindBlob(3,workDays);

        statement.executeInsert();

        //08-18

        statement.bindString(1,"08:00");
        statement.bindString(2,"18:00");
        statement.bindBlob(3,workDays);


        statement.executeInsert();


    }public String getMd5(@NonNull String password) {

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
    public void upgradeDatabase(@NonNull SQLiteDatabase db){

        db.execSQL(ALTER_EMPLOYEE_TO_EMPLOYEE_TEMP);
        db.execSQL(CREATE_EMPLOYEE);
        db.execSQL(COPY_EMPLOYE_TEMP_TO_EMPLOYE);
        db.execSQL(DROP_EMPLOYEE_TEMP);
        db.execSQL(ALTER_VARIABLE_TO_VARIABLE_TEMP);
        db.execSQL(CREATE_VARIABLE);
        db.execSQL(COPY_VARIABLE_TEMP_TO_VARIABLE);
        db.execSQL(DROP_VARIABLE_TEMP);

        db.execSQL(ALTER_PLANNING_TO_PLANNING_TEMP);
        db.execSQL(CREATE_PLANNING);
        db.execSQL(COPY_PLANNING_TEMP_TO_PLANNING);
        db.execSQL(DROP_PLANNING_TEMP);

        db.execSQL(ALTER_SERVICE_TO_PLANNING_TEMP);
        db.execSQL(CREATE_SERVICE);
        db.execSQL(COPY_SERVICE_TEMP_TO_SERVICE);
        db.execSQL(DROP_SERVICE_TEMP);

        db.execSQL(ALTER_POINTAGE_TO_POINTAGE_TEMP);
        db.execSQL(CREATE_CLOCKING);
        db.execSQL(COPY_CLOCKING_TEMP_TO_CLOCKING);
        db.execSQL(DROP_CLOCKING_TEMP);

        db.execSQL(ALTER_DAY_TO_DAY_TEMP);
        db.execSQL(CREATE_DAY);
        db.execSQL(COPY_DAY_TEMP_TO_DAY);
        db.execSQL(DROP_DAY_TEMP);



    }
}
