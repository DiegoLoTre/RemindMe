package app.dlt.com.remindme.alarm;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import app.dlt.com.remindme.R;

/**
 * Created by diego
 */

public class AddAlarm extends BroadcastReceiver {

    private SQLiteDatabase db;

    final public static String ONE_TIME = "onetime";

    @Override
    public void onReceive(Context context, Intent intent) {

        Notification(context, intent.getStringExtra("message"));

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR");
        //Acquire the lock
        wl.acquire();
        //You can do the processing here.
        Bundle extras = intent.getExtras();
        StringBuilder msgStr = new StringBuilder();
        if(extras != null && extras.getBoolean(ONE_TIME, Boolean.FALSE)){
            //Make sure this intent has been sent by the one-time timer button.
            msgStr.append("One time Timer : ");
        }
        Format formatter = new SimpleDateFormat("hh:mm:ss a");
        msgStr.append(formatter.format(new Date()));

        Toast.makeText(context, msgStr, Toast.LENGTH_LONG).show();

        Notification(context, intent.getStringExtra("message"));

        //Release the lock
        wl.release();
    }

    public void Notification(Context context, String message) {

        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(5000);
        // Set Notification Title
        String strtitle = context.getString(R.string.app_name);
        // Open NotificationView Class on Notification Click
        Intent intent = new Intent(context, StopAlarm.class);
        // Send data to NotificationView Class
        intent.putExtra("title", strtitle);
        intent.putExtra("text", message);
        // Open NotificationView.java Activity
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Create Notification using NotificationCompat.Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context)
                // Set Icon
                .setSmallIcon(R.drawable.ic_stat_name)
                // Set Ticker Message
                .setTicker(message)
                // Set Title
                .setContentTitle(strtitle)
                // Set Text
                .setContentText(message)
                // Add an Action Button below Notification
                .addAction(R.mipmap.ic_launcher, "Action Button", pIntent)
                // Set PendingIntent into Notification
                .setContentIntent(pIntent)
                // Dismiss Notification
                .setAutoCancel(true);

        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(0, builder.build());
    }

    public void SetAlarm(Context context, String text, Calendar c, String recurrence)
    {
        /*if(recurrence != null) {
            StringTokenizer st = new StringTokenizer(recurrence, ";");
            StringTokenizer st1 = new StringTokenizer(st.nextToken(), "=");
            st1.nextToken();
            switch (recurrence) {
                case "DAILY":
                    break;
                case "WEEKLY":
                    break;
                case "MONTHLY":
                    break;
                case "YEARLY":
                    break;
            }
        }

        Log.e("dsfdfs", "sfdds");
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AddAlarm.class);

        if(text.equals(""))
            intent.putExtra("message", "Alarma");
        else
            intent.putExtra("message", text);
        intent.putExtra(ONE_TIME, Boolean.FALSE);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        //After after 5 seconds
        am.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 1000 * 5 , pi);

        db = ShowAlarms.db;

        /*Alarm alarm = new Alarm(text, c, recurrence);
        long i = alarm.save(db);*/

        /*dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();*/

        //Alarm a = new Alarm(text, c, recurrence);
        //a.save(db);

    }

    public void CancelAlarm(Context context)
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, StopAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager.cancel(sender);
    }
}