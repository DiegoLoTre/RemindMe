package app.dlt.com.remindme;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import app.dlt.com.remindme.aid.AidIndex;
import app.dlt.com.remindme.alarm.ShowAlarms;
import app.dlt.com.remindme.medical.MedicalIndex;

public class Index extends TabActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        TabHost tabs = getTabHost();

        TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
        spec.setIndicator(getResources().getString(R.string.FirstAid));
        Intent FirstIntent = new Intent(this, AidIndex.class);
        spec.setContent(FirstIntent);

        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab2");
        spec.setIndicator(getResources().getString(R.string.Medications));
        Intent medicalIntent = new Intent(this, MedicalIndex.class);
        spec.setContent(medicalIntent);

        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab3");
        spec.setIndicator(getResources().getString(R.string.Reminders));
        Intent alarmIntent = new Intent(this, ShowAlarms.class);
        spec.setContent(alarmIntent);

        tabs.addTab(spec);

        tabs.setCurrentTab(0);
    }
}
