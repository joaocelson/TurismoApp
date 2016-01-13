package com.jctecnologia.turismoapp.casa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jctecnologia.turismoapp.R;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CadastroCasaActivity extends AppCompatActivity {

    EditText txtNomeCasa, txtNumeroPessoasCasa, txtDescricacaoCasa, txtTelefoneCasa, txtTelefoneCasa2;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_casa);

        Button btnSalvar = (Button) findViewById(R.id.btnCadastrarCasa);

        txtNomeCasa = (EditText) findViewById(R.id.txtNomeCasa);
        txtNumeroPessoasCasa = (EditText) findViewById(R.id.txtNumeroPessoasCasa);
        txtDescricacaoCasa = (EditText) findViewById(R.id.txtDescricacaoCasa);
        txtTelefoneCasa = (EditText) findViewById(R.id.txtTelefoneCasa);
        txtTelefoneCasa2 = (EditText) findViewById(R.id.txtTelefoneCasa2);
        iv = (ImageView) findViewById(R.id.ivFotoPerfilCasa);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarCasa();
                /*Intent intent = new Intent();
                intent.setClass(CadastroCasaActivity.this, CasaActivity.class);
                startActivity(intent);*/
            }
        });
    }

    private void salvarCasa() {

        final String nomeCasa = txtNomeCasa.getText().toString();
        final String numeroPessoas = txtNumeroPessoasCasa.getText().toString();
        final String telefoneCasa = txtTelefoneCasa.getText().toString();
        final String telefoneCasa2 = txtTelefoneCasa2.getText().toString();
        iv  = (ImageView)findViewById(R.id.ivFotoPerfilCasa);

        Bitmap bm=((BitmapDrawable)iv.getDrawable()).getBitmap();
        final String imgString = Base64.encodeToString(getBytesFromBitmap(bm), Base64.NO_WRAP);

        class TheTask extends AsyncTask<String, Void, String> {
            String resultado = "false";

            @Override
            protected String doInBackground(String... arg0) {
                String text = null;
                try {
                    URL url = new URL("http://turismo.somee.com/Estabelecimentos/Cadastro");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        urlConnection.setDoOutput(true);
                        urlConnection.setChunkedStreamingMode(0);
                        urlConnection.setRequestMethod("POST");
                        urlConnection.setDoOutput(true);
                        String urlParamenter = "nomeCasa=" +
                                nomeCasa + "&numeroPessoas=" +
                                numeroPessoas + "&telefoneCasa=" +
                                telefoneCasa + "&fotoPerfil=" +
                                imgString + "&idPessoa" +
                                1;

                        Log.println(0,"LOG",urlParamenter);

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

        new TheTask().execute("http://turismo.somee.com/Estabelecimentos/Cadastro");
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            return stream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

