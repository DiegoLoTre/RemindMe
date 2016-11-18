package app.dlt.com.remindme.alarm.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by diego on 16/11/16.
 */
public class Alarm extends AbstractModel {
    public static final String TABLE_NAME = "alarm";
    public static final String COL_ID = "_id";
    public static final String COL_MSG = "message";
    public static final String COL_FROMDATE = "from_date";
    public static final String COL_INTERVAL = "interval";

    static String getCrear () {
        return  "CREATE TABLE "+ TABLE_NAME+ " ("                     +
                COL_ID       + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_MSG      + " TEXT, "                              +
                COL_FROMDATE + " DATETIME, "                          +
                COL_INTERVAL + " TEXT);";
    }
    long save(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(COL_MSG, msg==null ? "" : msg);
        cv.put(COL_FROMDATE, fromDate);
        cv.put(COL_INTERVAL, interval);

        Log.e("SQL","COL_MSG:"      + msg+
                "COL_FROMDATE:" + fromDate+
                "COL_INTERVAL:" + interval);

        return db.insert(TABLE_NAME, null, cv);
    }
    @Override
    boolean update(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        super.update(cv);
        if (fromDate != null)
            cv.put(COL_FROMDATE, fromDate);
        if (interval != null)
            cv.put(COL_INTERVAL, interval);

        return db.update(TABLE_NAME, cv, COL_ID + " = ?", new String[]{String.valueOf(id)})
                == 1;
    }
    public static Cursor list(SQLiteDatabase db) {
        String[] columns = {COL_ID, COL_MSG};

        return db.query(TABLE_NAME, columns, null, null, null, null, null);
    }
    public boolean load(SQLiteDatabase db) {
        try (Cursor cursor = db.query(TABLE_NAME, null, COL_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null)) {
            if (cursor.moveToFirst()) {
                reset();
                super.load(cursor);
                fromDate = cursor.getString(cursor.getColumnIndex(COL_FROMDATE));
                interval = cursor.getString(cursor.getColumnIndex(COL_INTERVAL));
                return true;
            }
            return false;
        }
    }
    /*******************************************
     *                 DP PART                 *
     *******************************************/
    private long id;
    private String msg;
    private String fromDate;
    private String interval;
    public void reset() {
        super.reset();
        fromDate = null;
        interval = null;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String name) {
        this.msg = name;
    }
    public String getFromDate() {
        return fromDate;
    }
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }
    public String transformToDate(Calendar c){
        String date = c.get(Calendar.YEAR)+"-";
        int month = c.get(Calendar.MONTH)+1;
        if(month<10)
            date = date + "0" + month+ "-";
        else
            date = date + month+ "-";
        int day = c.get(Calendar.DAY_OF_MONTH);
        if(day<10)
            date = date + "0" + day + " ";
        else
            date = date + day + " ";


        int hour = c.get(Calendar.HOUR_OF_DAY);
        if(hour<10)
            date = date + "0" + hour + ":";
        else
            date = date + hour + ":";
        int minute = c.get(Calendar.MINUTE);
        if(minute<10)
            date = date + "0" + minute + ":00";
        else
            date = date + minute + ":00";
        return date;
    }
    public String getInterval() {
        return interval;
    }
    public void setInterval(String interval) {
        this.interval = interval;
    }
    public Alarm() {}
    public Alarm(long id) {
        this.id = id;
    }
    public static void showList(SQLiteDatabase db) {
        // 1. build the query
        String query = "SELECT  * FROM alarm";

        // 2. get reference to writable DB
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        if (cursor.moveToFirst()) {
            do {
                Log.e("id",cursor.getString(0));
                Log.e("message",cursor.getString(1));
                Log.e("from_date",cursor.getString(2));
                Log.e("interval",cursor.getString(3));

                long act = System.currentTimeMillis();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dt = new Date();
                Date dt1 = new Date(act);
                try {
                    dt = dateFormat.parse(cursor.getString(cursor.getColumnIndex(Alarm.COL_FROMDATE)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Log.e("FechaActual", dt1.toString());
                Log.e("FechaSonar",  dt.toString());
                Log.e("Actual",""+act);
                Log.e("Sonar", "" + dt.getTime());
                //Log.e("interval",cursor.getString(3));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
    public String searchNext(SQLiteDatabase db){
        String query = "SELECT  * FROM alarm";

        String id = null;
        String msg = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = dateFormat.parse("9999-12-31 23:59");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 2. get reference to writable DB
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        if (cursor.moveToFirst()) {
            do {
                Log.e("id",cursor.getString(0));
                Log.e("message",cursor.getString(1));
                Log.e("from_date",cursor.getString(2));

                Date dateTemp = null;
                try {
                    dateTemp = dateFormat.parse(cursor.getString(2));
                    if(dateTemp.before(date)) {
                        id  = cursor.getString(0);
                        msg = cursor.getString(1);
                        date = dateTemp;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        Log.e("asdas",id+"*"+msg+"*"+date);
        return id+"*"+msg+"*"+"*"+date;
    }
    public void changeToNextDate(SQLiteDatabase db, long id) {
        String interval;
        String query = "SELECT  * FROM alarm";/* WHERE "+COL_ID+" = "+id;*/

        Log.e("changeToNext",query);

        // 2. get reference to writable DB
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Log.e("changeToNextID",cursor.getString(cursor.getColumnIndex(COL_ID)));
                Log.e("changeToNextMSG",cursor.getString(cursor.getColumnIndex(COL_MSG)));
                Log.e("changeToNextDTE",cursor.getString(cursor.getColumnIndex(COL_FROMDATE)));
                Log.e("changeToNextINT",cursor.getString(cursor.getColumnIndex(COL_INTERVAL)));

                interval = cursor.getString(cursor.getColumnIndex(COL_INTERVAL));
                if(interval.equals("No repetir"))
                    db.delete(Alarm.TABLE_NAME, Alarm.COL_ID+" = ?", new String[]{String.valueOf(id)});
                else
                {
                    String recurrence = handleRecurrence(interval,cursor.getString(cursor.getColumnIndex(COL_FROMDATE)));
                    if(recurrence.equals("No repetir"))
                        db.delete(Alarm.TABLE_NAME, Alarm.COL_ID+" = ?", new String[]{String.valueOf(id)});
                    else {
                        ContentValues values = new ContentValues();
                        values.put(COL_FROMDATE, recurrence);
                        int i = db.update(TABLE_NAME, //table
                                values, // column/value
                                COL_ID+" = ?", // selections
                                new String[] { cursor.getString(cursor.getColumnIndex(COL_ID)) }); //selection args
                    }
                    Log.e("asda", recurrence);
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    public String handleRecurrence(String mRrule, String date) {

        String freq="",count="",until="",interval="",byday="";
        if(mRrule == null)
            return "No repetir";
           /*return null;*/
        else
        {
            StringTokenizer st = new StringTokenizer(mRrule,";");

            while (st.hasMoreTokens())
            {
                StringTokenizer st1 = new StringTokenizer(st.nextToken(),"=");
                String opcion = st1.nextToken();
                Log.i("Opcion",opcion);
                switch (opcion)
                {
                    case "FREQ":
                        freq = st1.nextToken();
                        break;
                    case "COUNT":
                        count = st1.nextToken();
                        int countInt = Integer.parseInt(count)-1;
                        if(countInt == -1)
                            return "No repetir";
                        else
                            count = Integer.toString(countInt);

                        break;
                    case "UNTIL":
                        until = st1.nextToken();

                        Date today = new Date();
                        Date toDate = null;
                        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

                        try {
                            toDate = format.parse(until);
                            Log.e("Fecha",toDate.toString());
                            Log.e("Fecha",today.toString());
                            if(toDate.before(today))
                                return "No repetir";
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "INTERVAL":
                        interval = st1.nextToken();
                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        try {
                            cal.setTime(sdf.parse(date));// all done
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        switch (freq)
                        {
                            case "DAILY":
                                cal.set(Calendar.DAY_OF_MONTH, Calendar.DAY_OF_MONTH +Integer.parseInt(interval));
                                break;
                            case "WEEKLY":
                                break;
                            case "MONTHLY":
                                break;
                            case "YEARLY":
                                cal.set(Calendar.YEAR, Calendar.YEAR +Integer.parseInt(interval));
                                break;
                        }

                        break;
                    case "BYDAY":
                        byday = st1.nextToken();
                        break;
                }
                //st.nextToken();
            }
            String string = "FREQ=" + freq + ";COUNT=" + count + ";UNTIL=" + until + ";INTERVAL=" + interval + ";BYDAY=" + byday;

            return string;
        }
    }
}
