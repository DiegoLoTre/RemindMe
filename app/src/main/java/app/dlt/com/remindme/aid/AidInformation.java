package app.dlt.com.remindme.aid;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.dlt.com.remindme.R;

public class AidInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aid_information);

        TextView tvTitle = (TextView)findViewById(R.id.Title);

        String aid = getIntent().getStringExtra("Aid");
        String aidTitle = getIntent().getStringExtra("Aid")+"Title";

        Log.e("Enfermedad", aid);

        tvTitle.setText(getResources().getText(getResources().getIdentifier(aidTitle,"string","app.dlt.com.remindme")));

        addSteps(aid);
    }

    private void addSteps(String aid) {
        int numSteps = 0;

        switch (aid) {
            case "seizures":
                numSteps = 3;
                break;
            case "fracture":
                numSteps = 5;
                break;
            case "burns":
                numSteps = 3;
                break;
            case "bites":
                numSteps = 4;
                break;
            case "exposure":
                numSteps = 2;
                break;
            case "asphyxiation":
                numSteps = 5;
                break;
            case "heart":
                numSteps = 5;
                break;
            case "poisoning":
                numSteps = 10;
                break;
            case "cramps":
                numSteps = 8;
                break;
        }

        LinearLayout ll = (LinearLayout)findViewById(R.id.llAid);
        for (int i = 1; i <= numSteps; i++)
        {
            TextView tv = new TextView(getApplicationContext());
            tv.setText(getResources().getText(getResources().getIdentifier(aid+i,"string","app.dlt.com.remindme")));
            tv.setTextSize(20);
            tv.setPadding(0, 10, 0, 0);
            tv.setTextColor(Color.parseColor("#ff104e8b"));
            ll.addView(tv);
        }
    }
}