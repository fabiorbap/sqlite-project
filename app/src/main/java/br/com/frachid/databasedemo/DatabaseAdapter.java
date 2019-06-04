package br.com.frachid.databasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseAdapter {

    private DatabaseHelper helper;
    private SQLiteDatabase db;

    DatabaseAdapter(Context context){
        helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public long insertData(String name, String email){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.KEY_NAME, name);
        contentValues.put(DatabaseHelper.KEY_EMAIL, email);
        return db.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
    }

    public String getAllData(){
        String columns[] = {DatabaseHelper.KEY_ROW_ID, DatabaseHelper.KEY_NAME, DatabaseHelper.KEY_EMAIL};
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null, null);
        StringBuffer stringBuffer = new StringBuffer();
        while (cursor.moveToNext()){
            int index1 = cursor.getColumnIndex(DatabaseHelper.KEY_ROW_ID);
            int rowId = cursor.getInt(index1);
            int index2 = cursor.getColumnIndex(DatabaseHelper.KEY_NAME);
            String name = cursor.getString(index2);
            int index3 = cursor.getColumnIndex(DatabaseHelper.KEY_EMAIL);
            String email = cursor.getString(index3);
            stringBuffer.append(rowId + " " + name + " " + email + "\n");
        }
        return stringBuffer.toString();
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "mydb.db";
        private static final String TABLE_NAME = "contacts";
        private static final int DATABASE_VERSION = 12;
        private static final String KEY_ROW_ID = "_id";
        private static final String KEY_NAME = "name";
        private static final String KEY_EMAIL = "email";
        private static final String CREATE_TABLE = "create table " + TABLE_NAME + " (" + KEY_ROW_ID + " integer primary key autoincrement, " + KEY_NAME + " text," + KEY_EMAIL + " text)";
        private static final String DROP_TABLE = "drop table if exists " + TABLE_NAME;
        private Context context;


        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
            Toast.makeText(context, "constructor called", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
                Toast.makeText(context, "onCreate called", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Toast.makeText(context, "onUpgrade called", Toast.LENGTH_SHORT).show();
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (SQLException e){
                Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
