package app.dlt.com.remindme.alarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.util.Date;
import java.util.StringTokenizer;

import app.dlt.com.remindme.alarm.model.Alarm;

/**
 * Created by diego on 16/11/16.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        long id = Long.parseLong(intent.getStringExtra("id"));
        String msg = intent.getStringExtra("msg");

        Alarm alarm = new Alarm(id);
        alarm.load(RemindMe.db);
        Notification.Builder n = new Notification.Builder(context);
        PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(), 0);

        n.setContentTitle("Remind Me");
        n.setContentText(msg);
        n.setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+ "://app.dlt.com.recordatorio//raw/alarm"));
        n.setAutoCancel(true);
        n.setContentIntent(pi);

        NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification myNotication = n.getNotification();
        nm.notify((int)id, myNotication);

        Log.e("Reciever", "Play");
        Log.i("ID",id+"");

        changeToNextDate(context, id);
        addNewAlarm(context);
    }

    private void changeToNextDate(Context context, long id) {
        Alarm alarm = new Alarm();
        alarm.changeToNextDate(RemindMe.db, id);
    }

    private void addNewAlarm(Context context) {
        Alarm alarm = new Alarm();

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        String newAlarm = alarm.searchNext(RemindMe.db);

        StringTokenizer st = new StringTokenizer(newAlarm,"*");
        Intent i = new Intent(context, AlarmReceiver.class);

        String id = st.nextToken();
        if(id != null) {
            i.putExtra("id", id);
            i.putExtra("msg", st.nextToken());

            PendingIntent pi = PendingIntent.getBroadcast(context, 0, i,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            Date dt = new Date(st.nextToken());
            am.set(AlarmManager.RTC_WAKEUP, dt.getTime(), pi);
        }
    }
}