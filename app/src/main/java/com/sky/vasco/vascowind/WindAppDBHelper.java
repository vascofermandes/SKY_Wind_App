package com.sky.vasco.vascowind;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vasco on 11/04/2016.
 */
public class WindAppDBHelper extends SQLiteOpenHelper {
        // Database Info
        private static final String DATABASE_NAME = "windDB";
        private static final int DATABASE_VERSION = 1;

        // Table Names
        private static final String TABLE_FAVOURITES = "favourites";

        // Favourites Table Columns
        private static final String KEY_FAVOURITE_CITY_ID = "city";
        private static final String KEY_FAVOURITE_CITY_NAME = "name";
        private static final String KEY_FAVOURITE_LAST_DATE = "date";
        private static final String KEY_FAVOURITE_LAST_SPEED = "speed";
        private static final String KEY_FAVOURITE_LAST_DEG = "deg";

    private static WindAppDBHelper sInstance;

        public static synchronized WindAppDBHelper getInstance(Context context) {
            // Use the application context, which will ensure that you
            // don't accidentally leak an Activity's context.
            if (sInstance == null) {
                sInstance = new WindAppDBHelper(context.getApplicationContext());
            }
            return sInstance;
        }

        /**
         * Constructor should be private to prevent direct instantiation.
         * Make a call to the static method "getInstance()" instead.
         */
        private WindAppDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        // Called when the database connection is being configured.
        // Configure database settings for things like foreign key support, write-ahead logging, etc.
        @Override
        public void onConfigure(SQLiteDatabase db) {
            super.onConfigure(db);
            db.setForeignKeyConstraintsEnabled(true);
        }

        // Called when the database is created for the FIRST time.
        // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_FAVOURITE_TABLE = "CREATE TABLE " + TABLE_FAVOURITES +
                    "(" +
                    KEY_FAVOURITE_CITY_ID + " INTEGER PRIMARY KEY," + //primary key
                    KEY_FAVOURITE_CITY_NAME + " TEXT," +
                    KEY_FAVOURITE_LAST_DATE + " INTEGER," +
                    KEY_FAVOURITE_LAST_SPEED + " REAL," +
                    KEY_FAVOURITE_LAST_DEG + " REAL" +
                    ")";

            db.execSQL(CREATE_FAVOURITE_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion != newVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVOURITES);
                onCreate(db);
            }
        }

        public void addFavourite(Favourite favourite) {

            SQLiteDatabase db = getWritableDatabase();
            db.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                values.put(KEY_FAVOURITE_CITY_ID, favourite.city_id);
                values.put(KEY_FAVOURITE_CITY_NAME, favourite.city_name);
                values.put(KEY_FAVOURITE_LAST_DATE, favourite.last_wind.date);
                values.put(KEY_FAVOURITE_LAST_SPEED, favourite.last_wind.speed);
                values.put(KEY_FAVOURITE_LAST_DEG, favourite.last_wind.deg);

                db.insertOrThrow(TABLE_FAVOURITES, null, values);
                db.setTransactionSuccessful();
            } catch (Exception e) {
                Log.d("CityID:"+String.valueOf(favourite.city_id), "Error while trying to add favourite to database");
            } finally {
                db.endTransaction();
            }
        }

        // Get all favourites in the database
        public List<Favourite> getAllFavourites() {
            List<Favourite> favourites = new ArrayList<>();

            String POSTS_SELECT_QUERY =
                    String.format("SELECT * FROM %s ",
                            TABLE_FAVOURITES);

            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
            try {
                if (cursor.moveToFirst())
                    do {

                        int id = cursor.getInt(cursor.getColumnIndex(KEY_FAVOURITE_CITY_ID));
                        String name = cursor.getString(cursor.getColumnIndex(KEY_FAVOURITE_CITY_NAME));
                        int date = cursor.getInt(cursor.getColumnIndex(KEY_FAVOURITE_LAST_DATE));
                        double speed = cursor.getDouble(cursor.getColumnIndex(KEY_FAVOURITE_LAST_SPEED));
                        double deg = cursor.getDouble(cursor.getColumnIndex(KEY_FAVOURITE_LAST_DEG));

                        Wind newWind = new Wind(speed, deg, date);
                        Favourite newFavourite = new Favourite(id, name, newWind);

                        favourites.add(newFavourite);
                    } while(cursor.moveToNext());

            } catch (Exception e) {
                //TODO: find something to put in log and help to trace eventual problems
                Log.d("", "Error while trying to get All Favourites from database");
            } finally {
                if (cursor != null && !cursor.isClosed()) {
                    cursor.close();
                }
            }
            return favourites;
        }

    // Update the last wind conditions for the favourites
    public int updateFavouriteLastWind(int city_id, Wind wind) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FAVOURITE_LAST_DATE, wind.date);
        values.put(KEY_FAVOURITE_LAST_SPEED, wind.speed);
        values.put(KEY_FAVOURITE_LAST_DEG, wind.deg);

        // Updating profile picture url for user with that userName
        return db.update(TABLE_FAVOURITES, values, KEY_FAVOURITE_CITY_ID + " = ?",
                new String[] { String.valueOf(city_id) });
    }

    // Delete all posts and users in the database
    public void deleteCityFavourite(int city_id, Wind wind) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            // Order of deletions is important when foreign key relationships exist.
            db.delete(TABLE_FAVOURITES, KEY_FAVOURITE_CITY_ID + " = ?",
                    new String[] { String.valueOf(city_id) });
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("CityID:"+String.valueOf(city_id), "Error while trying to delete favourite from database");
        } finally {
            db.endTransaction();
        }
    }



}

