package dbAdapter;

import static dbAdapter.DaySQLite.COL_DATE_JOUR;
import static dbAdapter.DaySQLite.COL_ID_JOUR;
import static dbAdapter.EmployeeSQLite.COL_MATRICULE;
import static dbAdapter.EmployeeSQLite.DATABASE_NAME;
import static dbAdapter.EmployeeSQLite.DATABASE_VERSION;
import static dbAdapter.EmployeeSQLite.createDatabase;
import static dbAdapter.EmployeeSQLite.upgradeDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/** Represents a database adapter for clocking's table.
 *<br/>
 * Allows to configure clocking's table<br/>
 * Proposes methods to create,delete and upgrage database<br/>
 * @author Ezéchiel G. ADEDE
 * @version 1.0
 * @since 2023*/

public class ClockingSQLite  extends SQLiteOpenHelper {
    /**Represents the name of clocking's table **/
    private static final String TABLE_POINTAGE = "pointage";
    /**Represents the id of clocking*/
    private static final String COL_ID_POINTAGE = "id_pointage";
    /**Represents reference to id_jour column in date's table **/
    private static final String COL_ID_JOUR_REF = "id_jour_ref";

    /**Represents entry time's column **/
    private static final String COL_HEURE_ENTREE = "heure_entree";

    /**Represents exit time's column **/
    private static final String COL_HEURE_SORTIE= "heure_sortie";

    /**Represents reference to the registration number column in employee's table **/
    private static  final String COL_MATRICULE_REF="matricule_ref";
    /**Represents the attendance status column **/
    private static final String COL_STATUT="statut";
    /**Represents  clocking's table  creation query**/
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
    /**Represents  temp clocking's table**/
    private static final String TABLE_POINTAGE_TEMP =TABLE_POINTAGE+"TEMP";

    /**Represents  temp day's table drop's query**/
    public static final String DROP_CLOCKING_TEMP="DROP TABLE  IF EXISTS "+TABLE_POINTAGE_TEMP;
    /**Represents clocking's table  rename query**/
    public static final String ALTER_POINTAGE_TO_POINTAGE_TEMP="ALTER TABLE "+TABLE_POINTAGE+
        " RENAME TO "+TABLE_POINTAGE_TEMP;
    /**Represents  clocking's table  copy query**/
    public static final String COPY_CLOCKING_TEMP_TO_CLOCKING ="INSERT INTO "+TABLE_POINTAGE+" SELECT * FROM  "+TABLE_POINTAGE_TEMP;
    /** A constructor of clocking sqlite : it allows to
     * configure database at first run
     * @param context is the application's context
     */
    public ClockingSQLite(Context context) {

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
