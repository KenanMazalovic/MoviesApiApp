package com.example.movies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SuggestionsDB {

    public static final String KEY_ID = "_id";
    public static final String KEY_SUGGESTION = "movie_name";
    private final String DATABASE_NAME = "SuggestionsDB1";
    private final String DATABASE_TABLE = "SuggestionTable";
    private final int DATABASE_VERSION = 1;

    private DBHelper ourHelper;
    private final Context ourcontext;
    private SQLiteDatabase ourDatabase;

    public SuggestionsDB(Context context) {
        ourcontext = context;
    }

    private class DBHelper extends SQLiteOpenHelper{

        public DBHelper(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            String sqlCode = "CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_SUGGESTION + " TEXT NOT NULL);";

            sqLiteDatabase.execSQL(sqlCode);

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(sqLiteDatabase);
        }
    }

    public SuggestionsDB open() throws SQLException{

        ourHelper = new DBHelper(ourcontext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        ourHelper.close();
    }

    public long createEntry (String title){

        ContentValues cv = new ContentValues();
        cv.put(KEY_SUGGESTION, title);
        return  ourDatabase.insert(DATABASE_TABLE,null,cv);

    }

    public ArrayList<String> getData()
    {
        String selectQuery = "SELECT DISTINCT " + KEY_SUGGESTION + " FROM " + DATABASE_TABLE + " ORDER BY " + KEY_ID + " DESC LIMIT 10";
        Cursor c = ourDatabase.rawQuery(selectQuery,null);
        ArrayList<String> result= new ArrayList<String>();
        int iRowTitle = c.getColumnIndex(KEY_SUGGESTION);

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            result.add(c.getString(iRowTitle));
        }

        c.close();

        return result;
    }


}
