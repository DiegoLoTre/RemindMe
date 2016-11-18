package app.dlt.com.remindme.medical;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import app.dlt.com.remindme.R;

public class MedicalIndex extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_index);

        constructorMedications();
    }

    private void constructorMedications() {
        ImageButton bAnalgesico = (ImageButton) findViewById(R.id.analgesico);
        ImageButton bAntiacidos = (ImageButton) findViewById(R.id.antiacidos);
        ImageButton bAntimicoticos = (ImageButton) findViewById(R.id.antimicoticos);
        ImageButton bUrologo = (ImageButton) findViewById(R.id.urologo);
        ImageButton bAntiparasitarios = (ImageButton) findViewById(R.id.antiparasitarios);
        ImageButton bAntitusivos = (ImageButton) findViewById(R.id.antitusivos);
        ImageButton bAnestesicos = (ImageButton) findViewById(R.id.anestesicos);
        ImageButton bDescongestivos = (ImageButton) findViewById(R.id.descongestivos);
        ImageButton bOftalmico = (ImageButton) findViewById(R.id.oftalmico);
        ImageButton bDesmatologico = (ImageButton) findViewById(R.id.dermatologico);
        ImageButton bAceites = (ImageButton) findViewById(R.id.aceites);
        ImageButton bOtros = (ImageButton) findViewById(R.id.otros);
        ImageButton bVitaminas = (ImageButton) findViewById(R.id.vitaminas);

        bAnalgesico.setOnClickListener(this);
        bAntiacidos.setOnClickListener(this);
        bAntimicoticos.setOnClickListener(this);
        bUrologo.setOnClickListener(this);
        bAntiparasitarios.setOnClickListener(this);
        bAntitusivos.setOnClickListener(this);
        bAnestesicos.setOnClickListener(this);
        bDescongestivos.setOnClickListener(this);
        bOftalmico.setOnClickListener(this);
        bDesmatologico.setOnClickListener(this);
        bAceites.setOnClickListener(this);
        bOtros.setOnClickListener(this);
        bVitaminas.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        Intent newActivity = new Intent(this, MedicalInformation.class);
        newActivity.putExtra("Type",v.getResources().getResourceName(v.getId()));
        startActivity(newActivity);
    }
}