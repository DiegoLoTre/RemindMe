package app.dlt.com.remindme.alarm;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.util.Date;
import java.util.StringTokenizer;

import app.dlt.com.remindme.alarm.model.Alarm;

/**
 * Created by diego on 16/11/16.
 */
public class AlarmService extends IntentService {

    private static final String TAG = "AlarmService";

    /*************************************************
     *        POPULATE = Add Alarms to the SQL       *
     *          CREATE = Add Alarm                   *
     *************************************************/
    public static final String POPULATE = "POPULATE";
    public static final String CREATE   = "CREATE";
    public static final String CANCEL   = "CANCEL";
    public static final String UPDATE   = "UPDATE";

    private IntentFilter matcher;

    public AlarmService() {
        super(TAG);
        matcher = new IntentFilter();
        matcher.addAction(POPULATE);
        matcher.addAction(CREATE);
        matcher.addAction(CANCEL);
        matcher.addAction(UPDATE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        String alarmId = intent.getStringExtra(Alarm.COL_ID);

        Log.e("Action",action);
        Log.e("Alarm Id",alarmId);

        if (matcher.matchAction(action)) {
            if (POPULATE.equals(action)) {
                changeToNewAlarm();
                execute(CREATE, alarmId);
            }
            if (CREATE.equals(action)) {
                execute(CREATE, alarmId);
            }

            if (CANCEL.equals(action)) {
                execute(CANCEL, alarmId);
                RemindMe.dbHelper.shred(RemindMe.db,alarmId);
            }
            if(UPDATE.equals(action)){
                execute(UPDATE,alarmId);
            }
        }
    }

    private void changeToNewAlarm() {
        Log.e("Borrar","Borrar");
        Context context = getApplicationContext();
        Intent intent = new Intent(context, AlarmReceiver.class);
        try{
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent,0);
            AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            am.cancel(pendingIntent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void execute(String action, String arg) {

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Alarm alarm = new Alarm();
        String newAlarm = alarm.searchNext(RemindMe.db);

        StringTokenizer st = new StringTokenizer(newAlarm,"*");
        Intent i = new Intent(this, AlarmReceiver.class);

        i.putExtra("id", st.nextToken());
        i.putExtra("msg", st.nextToken());

        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Date dt = new Date(st.nextToken());
        switch (action) {
            case CREATE:
                am.set(AlarmManager.RTC_WAKEUP, dt.getTime(), pi);
                break;
            case CANCEL:
                am.cancel(pi);
                break;
            case UPDATE:
                am.set(AlarmManager.RTC_WAKEUP, dt.getTime(), pi);
                break;
        }
    }

}
