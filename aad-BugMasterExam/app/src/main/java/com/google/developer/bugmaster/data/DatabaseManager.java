package com.google.developer.bugmaster.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Singleton that controls access to the SQLiteDatabase instance
 * for this application.
 */
public class DatabaseManager {
    SQLiteDatabase sd;
    private static DatabaseManager sInstance;
    private static final String DATABASE_TABLE="table_insects";
    private static final String COLUMN_1="friendly_name";
    private static final String COLUMN_2="scientific_name";
    private static final String COLUMN_5="danger_level";

    public static synchronized DatabaseManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseManager(context.getApplicationContext());
        }

        return sInstance;
    }

    private BugsDbHelper mBugsDbHelper;

    public DatabaseManager(Context context) {
        mBugsDbHelper = new BugsDbHelper(context);
    }

    /**
     * Return a {@link Cursor} that contains every insect in the database.
     *
     * @param sortOrder Optional sort order string for the query, can be null
     * @return {@link Cursor} containing all insect results.
     */
    public Cursor queryAllInsects(String sortOrder) {
        //TODO: Implement the query

        String SELECT_QUERY;
        if(sortOrder.equalsIgnoreCase("COMMON_NAME"))
        {
            SELECT_QUERY = "SELECT * FROM "+DATABASE_TABLE+" ORDER BY "+COLUMN_1+" ASC";
        }
        else
        {
            SELECT_QUERY = "SELECT * FROM "+DATABASE_TABLE+" ORDER BY "+COLUMN_5+" DESC";
        }

        return mBugsDbHelper.getReadableDatabase().rawQuery(SELECT_QUERY,null);
    }
    public Cursor queryRandomInsects()
    {
        String SELECT_QUERY;
        SELECT_QUERY = "SELECT "+COLUMN_2+" FROM "+DATABASE_TABLE+" ORDER BY RANDOM() LIMIT 5";
        return mBugsDbHelper.getReadableDatabase().rawQuery(SELECT_QUERY,null);
    }
    public Cursor queryNameOfInsects(String scientificName)
    {
        String SELECT_QUERY;
        SELECT_QUERY = "SELECT "+COLUMN_1+","+COLUMN_2+" FROM "+DATABASE_TABLE+" WHERE "+COLUMN_2+" = '"+scientificName+"'";
        return mBugsDbHelper.getReadableDatabase().rawQuery(SELECT_QUERY,null);
    }


    /**
     * Return a {@link Cursor} that contains a single insect for the given unique id.
     *
     * @param id Unique identifier for the insect record.
     * @return {@link Cursor} containing the insect result.
     */
    public Cursor queryInsectsById(int id) {
        String SELECT_QUERY;
        SELECT_QUERY = "SELECT "+COLUMN_2+" FROM "+DATABASE_TABLE+" ORDER BY RANDOM() LIMIT 5";
        return mBugsDbHelper.getReadableDatabase().rawQuery(SELECT_QUERY,null);
    }

}
