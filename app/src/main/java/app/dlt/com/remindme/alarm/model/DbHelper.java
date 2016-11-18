package app.dlt.com.remindme.alarm.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "remindme.db";
    private static final int DB_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.e("SQL","Paso 2");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("SQL","Paso 3");
        Log.e("SQL",Alarm.getCrear());
        db.execSQL(Alarm.getCrear());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Alarm.TABLE_NAME);

        onCreate(db);
    }
    public void shred(SQLiteDatabase db, String id) {
        db.delete(Alarm.TABLE_NAME, Alarm.COL_ID+" = ?", new String[]{id});
    }
    public Cursor listNotifications(SQLiteDatabase db) {
        Log.e("a","ad");
        String sql = "SELECT * FROM " + Alarm.TABLE_NAME;

        return db.rawQuery(sql, null);
    }
}
