package com.jctecnologia.turismoapp.casa;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jctecnologia.turismoapp.R;
import com.jctecnologia.turismoapp.adapter.CasaAdapter;
import com.jctecnologia.turismoapp.model.Casa;
import com.jctecnologia.turismoapp.model.Pessoa;
import com.jctecnologia.util.DaoConsulta;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CasaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casa);

        Intent intent = getIntent();
        String email = "";

        if(intent!=null)
        {
            email = intent.getStringExtra("email");
        }

        Pessoa pessoaLogada = DaoConsulta.ObterUsuarioLogado(email);
        if(pessoaLogada!= null && pessoaLogada.getTipoPessoa() != 2){
            ImageButton btnAdicionarCasa = (ImageButton)findViewById(R.id.btnAdicionarCasa);
            TextView txtTexto = (TextView)findViewById(R.id.lblAdicionarCasa);
            btnAdicionarCasa.setVisibility(View.INVISIBLE);
            txtTexto.setVisibility(View.INVISIBLE);
            LinearLayout linear = (LinearLayout)findViewById(R.id.linearBtnAdicionarFoto);
            linear.setVisibility(View.GONE);
        }

        ImageButton btnSalvar = (ImageButton) findViewById(R.id.btnAdicionarCasa);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(CasaActivity.this, CadastroCasaActivity.class);
                startActivity(intent);
            }
        });

        ArrayList<Casa> listaCasa = new ArrayList<Casa>();
        for (int i = 0; i < 5; i++) {
            Casa casa = new Casa();
            casa.setNome("Casa " + (i + 1));
            casa.setNumeroPessoas(i + 2);
            listaCasa.add(casa);
        }

        ListView listView = (ListView)findViewById(R.id.lvCasas);
        listView.setAdapter(new CasaAdapter(this,listaCasa));
    }
}
