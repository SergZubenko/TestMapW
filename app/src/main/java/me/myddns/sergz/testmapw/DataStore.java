package me.myddns.sergz.testmapw;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DataStore {


public final class CoordinatesLog{
    public CoordinatesLog(Context context){
        FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(context);
    };




    public abstract class CoordinatesLogEntry implements BaseColumns {
        public static final String TABLE_NAME = "coordinateshistory";
        public static final String COLUMN_NAME_TIMESTAMP = "coordinatestime";
        public static final String COLUMN_NAME_LONGTITUDE = "long";
        public static final String COLUMN_NAME_LATITUDE = "lat";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String NUMBER_TYPE = " REAL";
    private static final String DATE_TYPE = " REAL";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " +
                    CoordinatesLogEntry.TABLE_NAME + " (" +
                    CoordinatesLogEntry._ID + " INTEGER PRIMARY KEY," +
                    CoordinatesLogEntry.COLUMN_NAME_TIMESTAMP + DATE_TYPE + COMMA_SEP +
                    CoordinatesLogEntry.COLUMN_NAME_LONGTITUDE + NUMBER_TYPE + COMMA_SEP +
                    CoordinatesLogEntry.COLUMN_NAME_LATITUDE + NUMBER_TYPE + COMMA_SEP +
                    CoordinatesLogEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP+
            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CoordinatesLogEntry.TABLE_NAME;


    public class FeedReaderDbHelper extends SQLiteOpenHelper {
        // If you change the database schema, you must increment the database version.
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "FeedReader.db";

        public FeedReaderDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
            // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }

}





}
