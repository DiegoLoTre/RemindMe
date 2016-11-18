package app.dlt.com.remindme.alarm;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.codetroopers.betterpickers.recurrencepicker.EventRecurrence;
import com.codetroopers.betterpickers.recurrencepicker.RecurrencePickerDialogFragment;

import java.util.Calendar;
import java.util.StringTokenizer;

import app.dlt.com.remindme.R;
import app.dlt.com.remindme.alarm.model.Alarm;

/**
 * Created by diego
 */
public class SetAlarm  extends FragmentActivity
        implements View.OnClickListener, RadialTimePickerDialogFragment.OnTimeSetListener, CalendarDatePickerDialogFragment.OnDateSetListener, RecurrencePickerDialogFragment.OnRecurrenceSetListener {

    private String mRrule;
    private EventRecurrence mEventRecurrence = new EventRecurrence();

    private TextView eTime, eDate, eRecurence;
    private String today, tomorow;

    private TextView eMessage;

    /*  Date and Time choosen */
    String date,timeGlobal;

    /* Actual Date and Time */
    Calendar c = null; // This will also use to the choose date

    /* App */
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        db = RemindMe.db;

        c = Calendar.getInstance();

        today = c.get(Calendar.DAY_OF_MONTH)
                +"-"+ (c.get(Calendar.MONTH)+1)
                +"-"+ c.get(Calendar.YEAR);

        tomorow = c.get(Calendar.DAY_OF_MONTH)+1
                +"-"+ (c.get(Calendar.MONTH)+1)
                +"-"+ c.get(Calendar.YEAR);

        date = today;
        findViews();

        setHour();

        eMessage = (TextView)findViewById(R.id.msg);
    }

    private void findViews() {
        eMessage = (TextView) findViewById(R.id.msg);

        eTime = (TextView) findViewById(R.id.hour);
        eDate = (TextView) findViewById(R.id.date);
        eRecurence = (TextView) findViewById(R.id.recurence);

        eTime.setOnClickListener(this);
        eDate.setOnClickListener(this);
        eRecurence.setOnClickListener(this);

        Button bAccept = (Button) findViewById(R.id.accept);
        Button bCancel = (Button) findViewById(R.id.cancel);

        bAccept.setOnClickListener(this);
        bCancel.setOnClickListener(this);

        eDate.setText(R.string.Today);
        eTime.setText(c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE));
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.hour) {
            setHour();
        } else if (v.getId() == R.id.date) {
            setDate();
        } else if (v.getId() == R.id.recurence) {
            setRecurrence();
        } else if (v.getId() == R.id.accept) {
            create();
        } else if (v.getId() == R.id.cancel) {
            finish();
        }
    }

    private void create() {
        String msg;
        long alarmId;
        if (TextUtils.isEmpty(eMessage.getText()))
            msg = "Alarma";
        else
            msg = eMessage.getText().toString();

        Log.i("Msg Data",msg);

        Alarm alarm = new Alarm();
        alarm.setFromDate(alarm.transformToDate(c));
        alarm.setMsg(msg);
        if(mRrule == null)
            alarm.setInterval("No repetir");
        else
            alarm.setInterval(mRrule);
        alarmId = alarm.persist(db);
        /* Pruebas */
        Log.e("Date",alarm.transformToDate(c));
        Log.e("Rows",alarmId+"");

        Intent service = new Intent(this, AlarmService.class);
        service.putExtra(Alarm.COL_ID, String.valueOf(alarmId));
        service.setAction(AlarmService.POPULATE);
        startService(service);

        finish();
    }


    /*********************************************************************************************************
     *                                           Picker result handle                                        *
     *********************************************************************************************************/
    @Override
    public void onTimeSet(RadialTimePickerDialogFragment radialTimePickerDialog, int hourOfDay, int minuteOfDay) {
        String time = hourOfDay +":"+ minuteOfDay;
        timeGlobal = time;
        eTime.setText(time);

            /* Check if the hour has pass */
        Boolean hourLower = hourOfDay < c.get(Calendar.HOUR_OF_DAY);
            /* Check if the minute has pass and the hour is the same*/
        Boolean minuteLower = ((minuteOfDay < c.get(Calendar.MINUTE)) && (hourOfDay == c.get(Calendar.HOUR_OF_DAY)));
            /* Check that the day choosen is today if not it doesn't do anything */
        Boolean day = today.equals(date);

        if((hourLower || minuteLower) && day)
        {
            date = tomorow;
            eDate.setText(R.string.Tomorrow);
            c.set(Calendar.DAY_OF_MONTH,c.get(Calendar.DAY_OF_MONTH)+1);
        }


        Log.e("Fechas", "HoraRecibida:" + hourOfDay + "     MinutosRecibidos:" + minuteOfDay + "   Fecha:" + date);
        Log.e("Fechas","Hora:"+c.get(Calendar.HOUR_OF_DAY)+"     Minutos:"+c.get(Calendar.MINUTE)+ "    Fecha:"+today);
        Log.e("Fechas","Today:"+hourLower);
        Log.e("Fechas","Today:"+minuteLower);
        Log.e("Fechas","Today:"+day);

        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
        c.set(Calendar.MINUTE,minuteOfDay);
    }
    @Override
    public void onDateSet(CalendarDatePickerDialogFragment calendarDatePickerDialog, int year, int month, int day) {

        Calendar todayCal = Calendar.getInstance();
        if(day < todayCal.get(Calendar.DAY_OF_MONTH) && month <= todayCal.get(Calendar.MONTH) && year <= todayCal.get(Calendar.YEAR))
        {
            eDate.setText(R.string.Today);

            c.set(Calendar.DAY_OF_MONTH,todayCal.get(Calendar.DAY_OF_MONTH));
            c.set(Calendar.MONTH, todayCal.get(Calendar.MONTH));
            c.set(Calendar.YEAR, todayCal.get(Calendar.YEAR));
        }
        else {
            int newMonth = month + 1;
            String monthText = "" + newMonth;
            if (newMonth < 10)
                monthText = "0" + newMonth;

            date = day + "-" + newMonth + "-" + year;

            if (today.equals(date))
                eDate.setText(R.string.Today);
            else if (tomorow.equals(date))
                eDate.setText(R.string.Tomorrow);
            else
                eDate.setText(day + "-" + monthText + "-" + year);

            c.set(Calendar.DAY_OF_MONTH, day);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.YEAR, year);

            Log.e("Fechas", "Today:" + today + "Tommorrow:" + tomorow + "Actual:" + date);
        }
    }
    @Override
    public void onRecurrenceSet(String rrule) {
        if(rrule != null) {
            Log.e("ASA",rrule);
            mRrule = rrule;
            mEventRecurrence.parse(mRrule);
            StringTokenizer st = new StringTokenizer(rrule,";");
            StringTokenizer st1 = new StringTokenizer(st.nextToken(),"=");
            st1.nextToken();
            String Frecuence = null;
            switch (st1.nextToken())
            {
                case "DAILY":
                    Frecuence = "Cada Día";
                    break;
                case "WEEKLY":
                    Frecuence = "Cada Semana";
                    break;
                case "MONTHLY":
                    Frecuence = "Cada Mes";
                    break;
                case "YEARLY":
                    Frecuence = "Cada Año";
                    break;
            }

            eRecurence.setText("Frecuencia:" +Frecuence);
        }
        else
            eRecurence.setText("No recurrence");

        Alarm a = new Alarm();
        Log.i("Resultado",a.handleRecurrence(rrule,"2015-05-30 23:30"));
    }
    /*********************************************************************************************************
     *                                          Show Picker Fragment                                         *
     *********************************************************************************************************/
    private void setHour() {

        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        RadialTimePickerDialogFragment timePickerDialog = new RadialTimePickerDialogFragment()
                .setOnTimeSetListener(SetAlarm.this)
                .setStartTime(hour, minute)
                .setForced24hFormat();

        timePickerDialog.show(getSupportFragmentManager(), "timePickerDialogFragment");
    }
    private void setDate() {

        int year = c.get(Calendar.YEAR);

        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        CalendarDatePickerDialogFragment calendarDatePickerDialog = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(SetAlarm.this)
                .setPreselectedDate(year,month,day);
        calendarDatePickerDialog.show(getSupportFragmentManager(), "fragment_date_picker_name");
    }
    private void setRecurrence() {
        FragmentManager fm = getSupportFragmentManager();
        Bundle b = new Bundle();
        Time t = new Time();
        t.setToNow();
        b.putLong(RecurrencePickerDialogFragment.BUNDLE_START_TIME_MILLIS, t.toMillis(false));
        b.putString(RecurrencePickerDialogFragment.BUNDLE_TIME_ZONE, t.timezone);

        // may be more efficient to serialize and pass in EventRecurrence
        b.putString(RecurrencePickerDialogFragment.BUNDLE_RRULE, mRrule);

        RecurrencePickerDialogFragment rpd = (RecurrencePickerDialogFragment) fm.findFragmentByTag(
                "recurrencePickerDialogFragment");
        if (rpd != null) {
            rpd.dismiss();
        }
        rpd = new RecurrencePickerDialogFragment();
        rpd.setArguments(b);
        rpd.setOnRecurrenceSetListener(SetAlarm.this);
        rpd.show(fm, "recurrencePickerDialogFragment");
    }
}