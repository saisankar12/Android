package com.google.developer.bugmaster.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.developer.bugmaster.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static com.google.developer.bugmaster.R.id.scientific_name;

/**
 * Database helper class to facilitate creating and updating
 * the database from the chosen schema.
 */
public class BugsDbHelper extends SQLiteOpenHelper {
    private static final String TAG = BugsDbHelper.class.getSimpleName();
    SQLiteDatabase sqLiteDatabase;
    private static final String DATABASE_NAME = "insects.db";
    private static final String DATABASE_TABLE="table_insects";
    private static final String COLUMN_1="friendly_name";
    private static final String COLUMN_2="scientific_name";
    private static final String COLUMN_3="classification";
    private static final String COLUMN_4="image_asset";
    private static final String COLUMN_5="danger_level";
    private static final String CREATE_QUERY = "CREATE TABLE "+DATABASE_TABLE+
            "("+
            COLUMN_1+" TEXT,"+
            COLUMN_2+" TEXT,"+
            COLUMN_3+" TEXT,"+
            COLUMN_4+" TEXT,"+
            COLUMN_5+" INTEGER);";

    private static final int DATABASE_VERSION = 1;


    //Used to read data from res/ and assets/
    private Resources mResources;
    Context context;
    public ArrayList sciti_name;
    public BugsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
        mResources = context.getResources();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO: Create and fill the database

        Log.i(TAG,"BEFORE TABLE CREATION");
        db.execSQL(CREATE_QUERY);
        try {
            Cursor cursor=db.rawQuery("select * from "+DATABASE_TABLE,null);
            cursor.moveToNext();
            readInsectsFromResources(db);
            cursor.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"AFTER TABLE CREATION");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO: Handle database version upgrades
        Log.i(TAG,"database version upgrades");
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }

    /**
     * Streams the JSON data from insect.json, parses it, and inserts it into the
     * provided {@link SQLiteDatabase}.
     *
     * @param db Database where objects should be inserted.
     * @throws IOException
     * @throws JSONException
     */
    private void readInsectsFromResources(SQLiteDatabase db) throws IOException, JSONException {
        StringBuilder builder = new StringBuilder();
        InputStream in = mResources.openRawResource(R.raw.insects);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        //Parse resource into key/values
        final String rawJson = builder.toString();
        //TODO: Parse JSON data and insert into the provided database instance
        JSONObject jsonObject=new JSONObject(rawJson);
        JSONArray jsonArray=jsonObject.getJSONArray("insects");
        String friendly_Name=null;
        String scientific_Name=null;
        String classification=null;
        String image_Asset=null;
        int danger_Level=0;

        for (int i=0;i<jsonArray.length();i++){
            JSONObject object=jsonArray.getJSONObject(i);
            friendly_Name=object.getString("friendlyName");
            scientific_Name=object.getString("scientificName");
            classification=object.getString("classification");
            image_Asset=object.getString("imageAsset");
            danger_Level=object.getInt("dangerLevel");
            ContentValues contentValues=new ContentValues();
            contentValues.put(COLUMN_1,friendly_Name);
            contentValues.put(COLUMN_2,scientific_Name);
            contentValues.put(COLUMN_3,classification);
            contentValues.put(COLUMN_4,image_Asset);
            contentValues.put(COLUMN_5,danger_Level);
            db.insert(DATABASE_TABLE,null,contentValues);
        }

    }

    public void getData(){
        sciti_name=new ArrayList();
        sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.query(DATABASE_TABLE,new String[]{COLUMN_1,COLUMN_2,COLUMN_3,COLUMN_4,COLUMN_5},null,null,null,null,null);
        while (cursor.moveToNext()){
            sciti_name.add(cursor.getString(1));
        }
    }
}
