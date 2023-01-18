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
import static dbAdapter.PlanningSQLite.COL_ID_PLANNING;
import static dbAdapter.PlanningSQLite.COPY_PLANNING_TEMP_TO_PLANNING;
import static dbAdapter.PlanningSQLite.CREATE_PLANNING;
import static dbAdapter.PlanningSQLite.DROP_PLANNING_TEMP;
import static dbAdapter.PlanningSQLite.planning;
import static dbAdapter.ServiceSQLite.ALTER_SERVICE_TO_PLANNING_TEMP;
import static dbAdapter.ServiceSQLite.COL_ID_SERVICE;
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
/** Represents a database adapter for employee table.
 *<br/>
 * Allow to configure employee's table<br/>
 * Proposes methods to create,delete and upgrage database<br/>
 * @author Ezéchiel G. ADEDE
 * @version 1.0
 * @since 2023*/

public class EmployeeSQLite extends SQLiteOpenHelper {

    /**Represents the name of the database **/
    public static final String DATABASE_NAME = "Clocking_database.db";
    /**Represents the version of the database **/
    public static final int DATABASE_VERSION = 1;

    /**Represents the name of employee's table **/
    public static final String TABLE_EMPLOYE = "employe";
    /**Represents temp employee's table **/
    public static final String EMPLOYEE_TEMP = "employe_temp";
    /**Represents the registration number column **/
    public static final String COL_MATRICULE = "matricule";
    /**Represents the employee's lastname column **/
    private static final String COL_NOM = "nom";
    /**Represents the employee's firstname column **/
    private static final String COL_PRENOM = "prenom";
    /**Represents the employee's gender column **/
    private static final String COL_SEXE = "sexe";
    /**Represents the employee's birthdate column **/
    private static final String COL_BIRTHDATE = "birthdate";
    /**Represents the employee's mail address column **/
    private static final String COL_EMAIL = "couriel";
    /**Represents the employee's picture column **/
    private static final String COL_PHOTO = "photo";
    /**Represents the employee's username column **/
    private static final String COL_USERNAME = "username";
    /**Represents the employee's password column **/
    public static final String COL_PASSWORD = "password";

    /**Represents the employee's function column **/
    private static final String COL_FUNCTION = "type";
    /**Represents the employee's add date column **/
    private static final String COL_ADD_DATE = "date_ajout";
    /**Represents the employee's planning id ref column **/
    public static final String COL_ID_PLANNING_REF = "id_planning_ref";

    /**Represents the service's id ref column **/
    private static final String COL_ID_SERVICE_REF = "id_service_ref";

   // private static final String COL_ID_SERVICE = "id_service";
    /**Represents the employee's current attendaance status column **/
    private static final String COL_STATUS = "statut";

    /**Represents the employee's admin column **/
    private static final String COL_IS_ADMIN = "est_admin";
    /**Represents the variable to store last update **/
    private static final String TABLE_VARIABLE = "variable";

    /**Represents the super user insertion query **/
    public static final String super_user = "INSERT INTO employe(matricule," +
            "username,password,type,sexe,couriel,id_service_ref," +
            "id_planning_ref,nom,prenom,birthdate,date_ajout,est_admin)" +


            " VALUES (?,?,?,?,?,?,?,?,?,?,?,DATE('NOW','LOCALTIME'),?)";

    /**Represents  variable's table  creation query**/
    public static final String CREATE_VARIABLE = "CREATE  TABLE IF NOT EXISTS variable" +
            " AS SELECT '1970-01-01' AS last_update ";
    /**Represents  employee's table  rename query**/
    public static final String ALTER_EMPLOYEE_TO_EMPLOYEE_TEMP = "ALTER TABLE EMPLOYE" +
            " RENAME TO " + EMPLOYEE_TEMP;
    /**Represents  employee's table  creation query**/
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
            COL_FUNCTION + " TEXT DEFAULT 'Simple', " +
            COL_ID_PLANNING_REF + " INTEGER  , " +
            COL_ID_SERVICE_REF + " INTEGER   ," +
            COL_STATUS + " TEXT DEFAULT 'Hors Service', " +
            COL_ADD_DATE + " TEXT ," +
            " FOREIGN KEY(" + COL_ID_SERVICE_REF +
            " ) REFERENCES service(" + COL_ID_SERVICE + " )," +
            " FOREIGN KEY(" + COL_ID_PLANNING_REF +
            " ) REFERENCES planning(" + COL_ID_PLANNING + " )" +
            ")";

    /**Represents  employee's table  copy query**/
    public static final String COPY_EMPLOYE_TEMP_TO_EMPLOYE = "INSERT INTO " +
            TABLE_EMPLOYE + " SELECT * FROM  " + EMPLOYEE_TEMP;
    /**Represents  employee temp's table drop query**/
    public static final String DROP_EMPLOYEE_TEMP = "DROP TABLE IF EXISTS " + EMPLOYEE_TEMP;
    //public  static final String DROP_TEMP="DROP TABLE IF EXISTS "+ TABLE_VARIABLE;

    /**Represents  variable's table temp  **/
    private static final String TABLE_VARIABLE_TEMP = TABLE_VARIABLE + "TEMP";
    /**Represents  variable's table drop query**/
    public static final String DROP_VARIABLE_TEMP="DROP TABLE  IF EXISTS "+TABLE_VARIABLE_TEMP;
    /**Represents  variable's table  rename query**/
    public static final String ALTER_VARIABLE_TO_VARIABLE_TEMP = "ALTER TABLE " + TABLE_VARIABLE +
            " RENAME TO " + TABLE_VARIABLE_TEMP;
    /**Represents  variable's table  creation query**/
    public static final String COPY_VARIABLE_TEMP_TO_VARIABLE = "INSERT INTO " + TABLE_VARIABLE + " SELECT * FROM  " + TABLE_VARIABLE_TEMP;

    /** A constructor of employee sqlite: it allow to configure database and
     * employee table initial data at the first run
     *  with the given  service name
     * @param context is the application's context
     */
    public EmployeeSQLite(Context context) {


        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    /**Called on the creation of the database
     * @param db represents the database object**/
    @Override
    public void onCreate(SQLiteDatabase db) {


        createDatabase(db);

    }
    /**Contains instructions to upgrade database
     *  without losting the old daa within

     * @param db represents the database object**/
    public  static void upgradeDatabase(@NonNull SQLiteDatabase db) {

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


    /**Contains instructions to create each table of the
     *database and to insert initial data
     * @param db represents the database object**/
    public static void createDatabase(@NonNull SQLiteDatabase db) {
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
    /**Called on the upgrade of the database
     * @param db represents the database object**/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        upgradeDatabase(db);


    }
    /**md5 algorithm: used to encrypt password
     *  before inserting into database
     * @param password represents the password to be encrypted**/
    @NonNull
    public  static String getMd5(@NonNull String password) {

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
}


