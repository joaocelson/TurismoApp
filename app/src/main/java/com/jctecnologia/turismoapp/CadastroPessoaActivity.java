package com.jctecnologia.turismoapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jctecnologia.turismoapp.model.Pessoa;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.spec.ECField;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class CadastroPessoaActivity extends AppCompatActivity {

    Button btnSalvar;
    EditText txtNome, txtPassword, txtEmail;
    int TipoPessoa;
    Realm realm;
    RealmResults<Pessoa> realResultPessoa;
    RealmChangeListener realChangeListner;
    Pessoa pessoa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pessoa);

        txtNome = (EditText) findViewById(R.id.txtNome);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtEmail = (EditText) findViewById(R.id.txtEmail);

        pessoa = new Pessoa();

        realm = Realm.getInstance(this);
        realChangeListner = new RealmChangeListener() {
            @Override
            public void onChange() {

            }
        };

        realm.addChangeListener(realChangeListner);
        realResultPessoa = realm.where(Pessoa.class).findAll();

        btnSalvar = (Button) findViewById(R.id.btnCadastrar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarPessoa();
                CadastroPessoaBancoLocal();

                Toast.makeText(CadastroPessoaActivity.this, "Cadastro realizado com sucesso.", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(CadastroPessoaActivity.this, "Cadastro não realizado, tento novamente.", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }

    private void CadastroPessoaBancoLocal() {
        final String nome = txtNome.getText().toString();
        final String email = txtEmail.getText().toString();
        final String password = txtPassword.getText().toString();

        if(pessoa.getId()==0){
            realResultPessoa.sort("id", Sort.DESCENDING);
            long id= realResultPessoa.size() == 0 ? 1 :realResultPessoa.get(0).getId() + 1;
            pessoa.setId(id);
        }

        try {
            pessoa.setNome(nome);
            pessoa.setEmail(email);
            pessoa.setPassword(password);
            pessoa.setTipoPessoa(TipoPessoa);
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(pessoa);
            realm.commitTransaction();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.rbTurista:
                if (checked)
                    Toast.makeText(CadastroPessoaActivity.this, "Turista", Toast.LENGTH_SHORT).show();
                TipoPessoa = 1;
                break;
            case R.id.rbComerciante:
                if (checked)
                    Toast.makeText(CadastroPessoaActivity.this, "Comerciante", Toast.LENGTH_SHORT).show();
                TipoPessoa = 2;
                break;
        }
    }

    private void salvarPessoa() {

        final String nome = txtNome.getText().toString();
        final String email = txtEmail.getText().toString();
        final String password = txtPassword.getText().toString();

        class TheTask extends AsyncTask<String, Void, String> {
            String resultado = "false";

            @Override
            protected String doInBackground(String... arg0) {
                String text = null;
                try {
                    URL url = new URL("http://turismo.somee.com/Pessoas/Cadastro");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        urlConnection.setDoOutput(true);
                        urlConnection.setChunkedStreamingMode(0);
                        urlConnection.setRequestMethod("POST");
                        urlConnection.setDoOutput(true);
                        String urlParamenter = "nome=" + nome + "&email=" + email + "&password=" + password + "&tipoPessoa=" + TipoPessoa;

                        OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                        out.write(urlParamenter.getBytes());
                        out.flush();
                        out.close();
                        int responseCode = urlConnection.getResponseCode();
                        if (responseCode == 200) {
                            resultado = "true";
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return resultado;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
            }
        }

        new TheTask().execute("http://turismo.somee.com/Pessoas/Cadastro");
    }

    @Override
    protected void onDestroy(){
        realm.removeAllChangeListeners();
        realm.close();
        super.onDestroy();
    }
}
