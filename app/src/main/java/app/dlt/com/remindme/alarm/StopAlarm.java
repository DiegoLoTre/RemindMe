package app.dlt.com.remindme.alarm;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import app.dlt.com.remindme.R;

public class StopAlarm extends Activity implements View.OnClickListener {

    AddAlarm aa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_alarm);
        Button cancel = (Button)findViewById(R.id.cancelAlarm);
        cancel.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.cancelAlarm) {
            aa = new AddAlarm();
            Context context = this.getApplicationContext();
            aa.CancelAlarm(context);
            finish();
        }
    }
}