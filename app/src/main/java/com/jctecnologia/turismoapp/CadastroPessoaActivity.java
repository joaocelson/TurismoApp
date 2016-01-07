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

public class CadastroPessoaActivity extends AppCompatActivity {

    Button btnSalvar;
    EditText txtNome, txtPassword, txtEmail;
    int TipoPessoa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pessoa);

        txtNome = (EditText)findViewById(R.id.txtNome);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        txtEmail = (EditText)findViewById(R.id.txtEmail);

        btnSalvar = (Button) findViewById(R.id.btnCadastrar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               salvarPessoa();
                Toast.makeText(CadastroPessoaActivity.this, "Cadastro realizado com sucesso.", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(CadastroPessoaActivity.this, "Cadastro não realizado, tento novamente.", Toast.LENGTH_SHORT).show();
//                }
            }
        });

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rbTurista:
                if (checked)
                    Toast.makeText(CadastroPessoaActivity.this, "Turista", Toast.LENGTH_SHORT).show();
                TipoPessoa =1;
                break;
            case R.id.rbComerciante:
                if (checked)
                    Toast.makeText(CadastroPessoaActivity.this, "Comerciante", Toast.LENGTH_SHORT).show();
                TipoPessoa = 2;
                break;
        }
    }

    private void salvarPessoa(){

        final String nome = txtNome.getText().toString();
        final String email = txtEmail.getText().toString();
        final String password = txtPassword.getText().toString();

        class TheTask extends AsyncTask<String,Void,String>
        {
            String resultado = "false";
            @Override
            protected String doInBackground(String... arg0) {
                String text =null;
                try {
                    URL url = new URL("http://turismo.somee.com/Pessoas/Cadastro");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        urlConnection.setDoOutput(true);
                        urlConnection.setChunkedStreamingMode(0);
                        urlConnection.setRequestMethod("POST");
                        urlConnection.setDoOutput(true);
                        String urlParamenter = "nome=" +nome  + "&email="+email+"&password="+password+"&tipoPessoa="+TipoPessoa;

                        OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                        out.write(urlParamenter.getBytes());
                        out.flush();
                        out.close();
                        int responseCode = urlConnection.getResponseCode();
                        if (responseCode==200)
                        {
                            resultado = "true";
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    finally {
                        urlConnection.disconnect();
                    }
                }
                catch (Exception e)
                {
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
}
