package com.ufc.topico;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private TextView txtMessage;
    private Spinner spCitiesOri;
    private Spinner spCitiesDes;
    private Spinner spPayment;
    private Button btn1;
    private Button btn2;
    private String ponto1;
    private String ponto2;
    private Intent intent;
    private Intent intent2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtMessage = (TextView) findViewById(R.id.textMessage);
        spCitiesOri = (Spinner) findViewById(R.id.spinner);
        spCitiesDes = (Spinner) findViewById(R.id.spinner2);
        spPayment = (Spinner) findViewById(R.id.spinner3);

        //Para Primeiro Spinners
        assert spCitiesOri != null;
        spCitiesOri.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.cidade,
                android.R.layout.simple_selectable_list_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCitiesOri.setAdapter(adapter);

        //Para Segunda Spinners
        assert spCitiesDes != null;
        spCitiesDes.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.cidade,
                android.R.layout.simple_selectable_list_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCitiesDes.setAdapter(adapter2);

        //Para Terceiro Spinners
        assert spPayment != null;
        spPayment.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,R.array.pagamento,
                android.R.layout.simple_selectable_list_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPayment.setAdapter(adapter3);

        //Ação do Button
        btn1 = (Button)findViewById(R.id.button);
        intent = new Intent(this,MapsActivity.class);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Citie1",ponto1);
                intent.putExtra("Citie2",ponto2);
                startActivity(intent);

            }
        });

        //Ação para Busca Twitter
        btn2 = (Button)findViewById(R.id.button2);
        intent2 = new Intent(this,TwiiterActivity.class);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent2.putExtra("Citie2",ponto2);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       Spinner spinner = (Spinner)parent;
        if (spinner.getId() == R.id.spinner) {
            txtMessage.setText("Selcionou Origem:" + "\n" + parent.getItemAtPosition(position).toString());
            ponto1 = parent.getItemAtPosition(position).toString();// Subistituir a por um toats

        } else if (spinner.getId() == R.id.spinner2){
            txtMessage.setText("Selcionou Destino:" + "\n" + parent.getItemAtPosition(position).toString());
            ponto2 = parent.getItemAtPosition(position).toString();// Subistituir a por um toats
        } else if (spinner.getId() == R.id.spinner3){
            txtMessage.setText("Selecinou a Forma de Pagamento:" + "\n" + parent.getItemAtPosition(position).toString());
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    txtMessage.setText("");
    }
}
