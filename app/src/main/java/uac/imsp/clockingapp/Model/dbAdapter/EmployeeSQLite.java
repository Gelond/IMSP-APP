package dbAdapter;


import static dbAdapter.ClockingSQLite.ALTER_POINTAGE_TO_POINTAGE_TEMP;
import static dbAdapter.ClockingSQLite.COPY_CLOCKING_TEMP_TO_CLOCKING;
import static dbAdapter.ClockingSQLite.CREATE_CLOCKING;
import static dbAdapter.ClockingSQLite.DROP_CLOCKING_TEMP;
import static dbAdapter.DaySQLite.ALTER_DAY_TO_DAY_TEMP;
import static dbAdapter.DaySQLite.COPY_DAY_TEMP_TO_DAY;
import static dbAdapter.DaySQLite.CREATE_DAY;
import static dbAdapter.DaySQLite.DROP_DAY_TEMP;
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

public class EmployeeSQLite extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Clocking_database.db";
    public static final int DATABASE_VERSION = 2;

    public static final String TABLE_EMPLOYE = "employe";
    public static final String EMPLOYEE_TEMP = "employe_temp";

    public static final String COL_MATRICULE = "matricule";
    public static final String COL_NOM = "nom";
    public static final String COL_PRENOM = "prenom";
    public static final String COL_SEXE = "sexe";
    public static final String COL_BIRTHDATE = "birthdate";
    public static final String COL_EMAIL = "couriel";
    public static final String COL_PHOTO = "photo";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_TYPE = "type";
    public static final String COL_ADD_DATE = "date_ajout";
    public static final String COL_ID_PLANNING_REF = "id_planning_ref";
    public static final String COL_ID_PLANNING = "id_planning";
    private static final String COL_ID_SERVICE_REF = "id_service_ref";
    private static final String COL_ID_SERVICE = "id_service";
    private static final String COL_STATUS = "statut";
    private static final String COL_IS_ADMIN = "est_admin";
    private static final String TABLE_VARIABLE = "variable";


    public static final String super_user = "INSERT INTO employe(matricule," +
            "username,password,type,sexe,couriel,id_service_ref," +
            "id_planning_ref,nom,prenom,birthdate,date_ajout,est_admin)" +


            " VALUES (?,?,?,?,?,?,?,?,?,?,?,DATE('NOW','LOCALTIME'),?)";


    public static final String CREATE_VARIABLE = "CREATE  TABLE IF NOT EXISTS variable" +
            " AS SELECT '1970-01-01' AS last_update ";
    public static final String ALTER_EMPLOYEE_TO_EMPLOYEE_TEMP = "ALTER TABLE EMPLOYE" +
            " RENAME TO " + EMPLOYEE_TEMP;
    public static final String CREATE_EMPLOYEE = "CREATE TABLE  IF NOT EXISTS " + TABLE_EMPLOYE + " (" +
            COL_MATRICULE + " INTEGER NOT NULL  PRIMARY KEY, " +
            COL_NOM + " TEXT NOT NULL ," +
            COL_PRENOM + " TEXT NOT NULL," +
            COL_SEXE + " TEXT NOT NULL ," +
            COL_EMAIL + " TEXT UNIQUE NOT NULL," +
            COL_BIRTHDATE + " TEXT , " +
            COL_PHOTO + "  BLOB ," +
            COL_USERNAME + " TEXT UNIQUE NOT NULL ," +
            COL_PASSWORD + " TEXT NOT NULL , " +
            COL_IS_ADMIN + " TEXT DEFAULT 'false'," +
            COL_TYPE + " TEXT DEFAULT 'Simple', " +
            COL_ID_PLANNING_REF + " INTEGER  , " +
            COL_ID_SERVICE_REF + " INTEGER   ," +

            COL_STATUS + " TEXT DEFAULT 'Hors Service', " +
            COL_ADD_DATE + " TEXT ," +
            " FOREIGN KEY(" + COL_ID_SERVICE_REF +
            " ) REFERENCES service(" + COL_ID_SERVICE + " )," +
            " FOREIGN KEY(" + COL_ID_PLANNING_REF +
            " ) REFERENCES planning(" + COL_ID_PLANNING + " )" +
            ")";


    public static final String COPY_EMPLOYE_TEMP_TO_EMPLOYE = "INSERT INTO " + TABLE_EMPLOYE + " SELECT * FROM  " + EMPLOYEE_TEMP;

    //public static final String DROP_EMPLOYEE="DROP TABLE IF EXISTS "+TABLE_EMPLOYE;
    public static final String DROP_EMPLOYEE_TEMP = "DROP TABLE IF EXISTS " + EMPLOYEE_TEMP;
    //public  static final String DROP_TEMP="DROP TABLE IF EXISTS "+ TABLE_VARIABLE;


    private static final String TABLE_VARIABLE_TEMP = TABLE_VARIABLE + "TEMP";
    public static final String DROP_VARIABLE_TEMP="DROP TABLE  IF EXISTS "+TABLE_VARIABLE_TEMP;
    public static final String ALTER_VARIABLE_TO_VARIABLE_TEMP = "ALTER TABLE " + TABLE_VARIABLE +
            " RENAME TO " + TABLE_VARIABLE_TEMP;
    public static final String COPY_VARIABLE_TEMP_TO_VARIABLE = "INSERT INTO " + TABLE_VARIABLE + " SELECT * FROM  " + TABLE_VARIABLE_TEMP;


    public EmployeeSQLite(Context context) {


        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        createDatabase(db);

    }

    public void upgradeDatabase(@NonNull SQLiteDatabase db) {

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


    public void createDatabase(@NonNull SQLiteDatabase db) {
        db.execSQL(CREATE_SERVICE);
        db.execSQL(CREATE_PLANNING);
        db.execSQL(CREATE_DAY);
        db.execSQL(CREATE_EMPLOYEE);
        db.execSQL(CREATE_CLOCKING);
        db.execSQL(CREATE_VARIABLE);

        SQLiteStatement statement = db.compileStatement(super_user);
        statement.bindLong(1, 1);
        statement.bindString(2, "User10");
        statement.bindString(3, getMd5("Aab10%"));
        statement.bindString(4, "Directeur");
        statement.bindString(5, "M");
        statement.bindString(6, "super@gmail.com");
        statement.bindLong(7, 1);
        statement.bindLong(8, 1);
        statement.bindString(9, "AKOBA");
        statement.bindString(10, "Patrick");
        statement.bindString(11, "1970-01-01");
        statement.bindString(12, "true");
        statement.executeInsert();
        String service = "INSERT INTO service(nom)  VALUES (?)";
        statement = db.compileStatement(service);
        //Direction
        statement.bindString(1, "Direction");
        statement.executeInsert();

        //Service scolarité
        statement.bindString(1, "Service scolarité");
        statement.executeInsert();

        //Secrétariat administratif
        statement.bindString(1, "Secrétariat administratif");
        statement.executeInsert();
        //Comptabilité
        statement.bindString(1, "Comptabilité");
        statement.executeInsert();
        //Service de coopération
        statement.bindString(1, "Service de coopération");
        statement.executeInsert();


        statement = db.compileStatement(planning);
        byte[] workDays = new byte[]{'T', 'T', 'T', 'T', 'T', 'F', 'F'};


        //08-17
        statement.bindString(1, "08:00");
        statement.bindString(2, "17:00");
        statement.bindBlob(3, workDays);

        statement.executeInsert();

        //08-18

        statement.bindString(1, "08:00");
        statement.bindString(2, "18:00");
        statement.bindBlob(3, workDays);

        statement.executeInsert();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        upgradeDatabase(db);


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
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}


