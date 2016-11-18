package app.dlt.com.remindme.alarm;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import app.dlt.com.remindme.alarm.model.DbHelper;

/**
 * Created by diego on 16/11/16.
 */
public class RemindMe  extends Application {

    public static DbHelper dbHelper;
    public static SQLiteDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();

        dbHelper = new DbHelper(this);
        db = dbHelper.getWritableDatabase();

        Log.e("RemindeMe", "Constructor");
    }
}