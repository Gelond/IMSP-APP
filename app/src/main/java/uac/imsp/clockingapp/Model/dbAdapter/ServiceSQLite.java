package dbAdapter;


import static dbAdapter.EmployeeSQLite.DATABASE_NAME;
import static dbAdapter.EmployeeSQLite.DATABASE_VERSION;
import static dbAdapter.EmployeeSQLite.createDatabase;
import static dbAdapter.EmployeeSQLite.upgradeDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/** Represents a database adapter for day's table.
 *<br/>
 * Allows to configure day's table<br/>
 * Proposes methods to create,delete and upgrage database<br/>
 * @author Ezéchiel G. ADEDE
 * @version 1.0
 * @since 2023*/
public class ServiceSQLite  extends SQLiteOpenHelper {
    /**Represents the service's  table**/
        private static final String TABLE_SERVICE = "service";
    /**Represents the service's  id  column **/
    public static final String COL_ID_SERVICE = "id_service";
    /**Represents the service's  name  column **/
    private static final String COL_NOM_SERVICE="nom";

    /**Represents the service's  table creation query**/
    public static final String CREATE_SERVICE = "CREATE TABLE IF NOT EXISTS  " +
            TABLE_SERVICE + " (" +
            COL_ID_SERVICE + " INTEGER NOT NULL  PRIMARY KEY AUTOINCREMENT, " +
            COL_NOM_SERVICE + " TEXT UNIQUE NOT NULL  "+")" ;
    /**Represent temp service table**/
    private static final String TABLE_SERVICE_TEMP =TABLE_SERVICE+"TEMP";
    /**Represent temp service table drop's query**/
    public static final String DROP_SERVICE_TEMP="DROP TABLE  IF EXISTS "+
            TABLE_SERVICE_TEMP;
    /**Represent  service table rename's query**/
    public static final String ALTER_SERVICE_TO_PLANNING_TEMP="ALTER TABLE "+
            TABLE_SERVICE+
            " RENAME TO "+TABLE_SERVICE_TEMP;
    /**Represent  service table initial data insertion**/
    public static final String COPY_SERVICE_TEMP_TO_SERVICE ="INSERT INTO "+TABLE_SERVICE+
            " SELECT * FROM  "+TABLE_SERVICE_TEMP;
    /** A constructor of service sqlite : it allows to
     * configure database at first run
     * @param context is the application's context
     */
    public ServiceSQLite(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**Called on the creation of the database
     * @param db represents the database object**/
    @Override
    public void onCreate(SQLiteDatabase db) {
            createDatabase(db);

    }

/**Called on the upgrade of the database
 * @param db represents the database object**/
    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       upgradeDatabase(db);



    }
}
