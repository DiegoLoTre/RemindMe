package app.dlt.com.remindme.alarm;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import app.dlt.com.remindme.R;
import app.dlt.com.remindme.alarm.model.Alarm;

public class ShowAlarms extends ListActivity implements View.OnClickListener {

    public static SQLiteDatabase db;
    Alarm alarm = new Alarm();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminders);

        db = RemindMe.db;

        registerForContextMenu(getListView());

        Button add = (Button) findViewById(R.id.setAlarm);
        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        Intent newActivity = new Intent(this,SetAlarm.class);
        startActivity(newActivity);
    }

    private Cursor createCursor() {
        Cursor c = RemindMe.dbHelper.listNotifications(db);
        startManagingCursor(c);
        return c;
    }

    protected void onResume() {
        super.onResume();

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.format,
                createCursor(),
                new String[]{Alarm.COL_MSG,Alarm.COL_FROMDATE, Alarm.COL_FROMDATE, Alarm.COL_FROMDATE, Alarm.COL_FROMDATE},
                new int[]{R.id.msg_tv, R.id.year_tv, R.id.month_tv, R.id.date_tv, R.id.time_tv});

        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (view.getId() == R.id.msg_tv) return false;

                TextView tv = (TextView)view;
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                Date dt = new Date();
                try {
                    dt = dateFormat.parse(cursor.getString(columnIndex));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                switch(view.getId()) {
                    case R.id.year_tv:
                        tv.setText(String.valueOf(dt.getYear()+1900));
                        break;
                    case R.id.month_tv:
                        tv.setText(android.text.format.DateFormat.format("MMM", dt));
                        break;
                    case R.id.date_tv:
                        tv.setText(String.valueOf(dt.getDate()));
                        break;
                    case R.id.time_tv:
                        tv.setText(dt.getHours()+":"+dt.getMinutes());
                }
                return true;
            }
        });
        setListAdapter(adapter);

        Alarm.showList(db);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        Log.e("asdas","sadas");
        if (v.getId() == android.R.id.list) {
            getMenuInflater().inflate(R.menu.context_menu, menu);
            menu.setHeaderTitle("Choose an Option");
            //menu.setHeaderIcon(R.drawable.ic_dialog_menu_generic);

            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            /*alarmMsg.setId(info.id);
            alarmMsg.load(db);
            if (alarmMsg.getDateTime() < System.currentTimeMillis())
                menu.removeItem(R.id.menu_edit);*/
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.menu_delete:
                Log.e("Opcion","Borrar");

                Intent cancelThis = new Intent(this, AlarmService.class);
                cancelThis.putExtra(Alarm.COL_ID, String.valueOf(info.id));
                cancelThis.setAction(AlarmService.CANCEL);
                startService(cancelThis);
                break;
        }

        SimpleCursorAdapter adapter = (SimpleCursorAdapter) getListAdapter();
        adapter.getCursor().requery();
        adapter.notifyDataSetChanged();

        return true;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Log.e("List",position+" "+id);
        openContextMenu(v);
    }
}