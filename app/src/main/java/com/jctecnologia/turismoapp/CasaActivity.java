package com.jctecnologia.turismoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.jctecnologia.turismoapp.adapter.CasaAdapter;
import com.jctecnologia.turismoapp.model.Casa;

import java.util.ArrayList;

public class CasaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casa);

        ArrayList<Casa> listaCasa = new ArrayList<Casa>();
        for (int i = 0; i < 5; i++) {
            Casa casa = new Casa();
            casa.setNome("Casa " + i + 1);
            casa.setNumeroPessoas(i + 2);
            listaCasa.add(casa);
        }
        ListView listView = (ListView)findViewById(R.id.lvCasas);
        listView.setAdapter(new CasaAdapter(this,listaCasa));
    }
}
