package app.dlt.com.remindme.aid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import app.dlt.com.remindme.R;

public class AidIndex extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aid_index);

        constructorFirst();
    }

    private void constructorFirst() {
        ImageButton bSeizure = (ImageButton) findViewById(R.id.Seizures);
        ImageButton bFracture = (ImageButton) findViewById(R.id.Fracture);
        ImageButton bBurns = (ImageButton) findViewById(R.id.Burns);
        ImageButton bBits = (ImageButton) findViewById(R.id.Bits);
        ImageButton bExposure = (ImageButton) findViewById(R.id.Exposure);
        ImageButton bAsphyxiation = (ImageButton) findViewById(R.id.Asphyxiation);
        ImageButton bHeart = (ImageButton) findViewById(R.id.Heart);
        ImageButton bPoison = (ImageButton) findViewById(R.id.Poison);
        ImageButton bCramps = (ImageButton) findViewById(R.id.Cramps);

        bSeizure.setOnClickListener(this);
        bFracture.setOnClickListener(this);
        bBurns.setOnClickListener(this);
        bBits.setOnClickListener(this);
        bExposure.setOnClickListener(this);
        bAsphyxiation.setOnClickListener(this);
        bHeart.setOnClickListener(this);
        bPoison.setOnClickListener(this);
        bCramps.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        Intent newActivity = new Intent(this, AidInformation.class);
        String opcion = "";
        if(v.getId() == R.id.Seizures)
            opcion = "seizures";
        else if(v.getId() == R.id.Fracture)
            opcion = "fracture";
        else if(v.getId() == R.id.Burns)
            opcion = "burns";
        else if(v.getId() == R.id.Bits)
            opcion = "bites";
        else if(v.getId() == R.id.Exposure)
            opcion = "exposure";
        else if(v.getId() == R.id.Asphyxiation)
            opcion = "asphyxiation";
        else if(v.getId() == R.id.Heart)
            opcion ="heart";
        else if(v.getId() == R.id.Poison)
            opcion = "poisoning";
        else if(v.getId() == R.id.Cramps)
            opcion = "cramps";
        newActivity.putExtra("Aid",opcion);
        startActivity(newActivity);
    }
}
