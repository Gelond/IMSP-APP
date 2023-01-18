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
public class DaySQLite extends SQLiteOpenHelper {
    /**Represents the name of day's table **/
    public static final String TABLE_JOUR = "jour";
    /**Represents the date column **/
    public static final String COL_DATE_JOUR= "date_jour";
    /**Represents the id of the date column **/
    public static final String COL_ID_JOUR="id_jour";
    /**Represents holiday column **/
    private static final String COL_FERIE="ferie";




    /**Represents  day's table  creation query**/
    public static final String CREATE_DAY = "CREATE TABLE IF NOT EXISTS " + TABLE_JOUR + " (" +

            COL_ID_JOUR + " INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT , " +
            COL_DATE_JOUR+" TEXT ," +
            COL_FERIE+" TEXT DEFAULT 'false')" ;
    /**Represents  temp day's table**/
    private static final String TABLE_DAY_TEMP =TABLE_JOUR+"TEMP";
    /**Represents  temp day's table drop query**/
    public static final String DROP_DAY_TEMP="DROP TABLE  IF EXISTS "+TABLE_DAY_TEMP;

    /**Represents day's table  rename query**/
    public static final String ALTER_DAY_TO_DAY_TEMP="ALTER TABLE "+TABLE_JOUR+
            " RENAME TO "+TABLE_DAY_TEMP;

    /**Represents  day's table  copy query**/
    public static final String COPY_DAY_TEMP_TO_DAY ="INSERT INTO "+TABLE_JOUR+" SELECT * FROM  "+TABLE_DAY_TEMP;



    /** A constructor of day sqlite : it allows to
     * configure database at first run
     * @param context is the application's context
     */
    public DaySQLite(Context context) {


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

