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
public class PlanningSQLite extends SQLiteOpenHelper {
    /**Represents the planning's  table **/
    public static final String TABLE_PLANNING = "planning";

    /**Represents the planning's  id column **/
    public static final String COL_ID_PLANNING = "id_planning";

    /**Represents the attended entry time's  column **/
    private static final String COL_HEURE_DEBUT_OFFICIELLE="heure_debut_officielle";
    /**Represents the attended exit time's  column **/
    private static final String COL_HEURE_FIN_OFFICIELLE="heure_fin_officielle";
    /**Represents the workdays's  column **/
    private static final String COL_JOURS_DE_TRAVAIL="jours_de_travail";
    /**Represent planning's initial data insertion query**/
    public static final String planning="INSERT INTO planning(" +
            "heure_debut_officielle,heure_fin_officielle,jours_de_travail)" +

           " VALUES (?,?,?)";


    public static final String CREATE_PLANNING = "CREATE TABLE IF NOT EXISTS " + TABLE_PLANNING + " (" +
            COL_ID_PLANNING + " INTEGER NOT NULL  PRIMARY KEY AUTOINCREMENT, " +
            COL_HEURE_DEBUT_OFFICIELLE + " TEXT NOT NULL ," +
            COL_HEURE_FIN_OFFICIELLE + " TEXT NOT NULL,"+
            COL_JOURS_DE_TRAVAIL+" BLOB NOT NULL)" ;
        private static final String TABLE_PLANNING_TEMP =TABLE_PLANNING+"TEMP";
    /**Represents  temp planning's table drop query**/
    public static final String DROP_PLANNING_TEMP="DROP TABLE  IF EXISTS "+TABLE_PLANNING_TEMP;
    /**Represents day's table  rename query**/
    public static final String ALTER_PLANNING_TO_PLANNING_TEMP="ALTER TABLE "+TABLE_PLANNING+
            " RENAME TO "+TABLE_PLANNING_TEMP;
    /**Represents  day's table  copy query**/
    public static final String COPY_PLANNING_TEMP_TO_PLANNING ="INSERT INTO "+TABLE_PLANNING+" SELECT * FROM  "+TABLE_PLANNING_TEMP;



    /** A constructor of day sqlite : it allows to
     * configure database at first run
     * @param context is the application's context
     */
    public PlanningSQLite(Context context) {


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

