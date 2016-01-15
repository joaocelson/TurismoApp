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

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

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

        Bitmap bm =((BitmapDrawable)iv.getDrawable()).getBitmap();
        //bm.setHasAlpha(true);

        final String imgString = Base64.encodeToString(getBytesFromBitmap(bm), Base64.NO_WRAP);

        class TheTask extends AsyncTask<String, Void, String> {
            String resultado = "false";

            @Override
            protected String doInBackground(String... arg0) {
                String text = null;
                try {
                    URL url = new URL("http://turismo.somee.com/Estabelecimentos/Create");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        urlConnection.setDoOutput(true);
                        urlConnection.setDoInput(true);
                        urlConnection.setRequestMethod("POST");
                        //urlConnection.setFixedLengthStreamingMode(data.getBytes().length);
                        urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

                        JSONObject jsonObject = new JSONObject();

                        jsonObject.put("EstabelecimentoId", 0);
                        jsonObject.put("Nome", nomeCasa);
                        jsonObject.put("NomeFotoPerfil", imgString);
                        jsonObject.put("DataCadastro",new Date().getDate());
                        jsonObject.put("PessoaId", 1);
                        jsonObject.put("Valor", 1);
                        jsonObject.put("Disponivel", true);

                        String data = jsonObject.toString();

                        OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                        writer.write(data);

                        writer.flush();
                        writer.close();
                        out.close();
                        urlConnection.connect();

                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(
                                in, "UTF-8"));
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                        }
                        in.close();
                        String result = sb.toString();
                        //Log.d("Vicky", "Response from php = " + result);
                        //Response = new JSONObject(result);
                        urlConnection.disconnect();

/*
                        String urlParamenter = "nomeCasa=" +
                                nomeCasa + "&numeroPessoas=" +
                                numeroPessoas + "&telefoneCasa=" +
                                telefoneCasa + "&fotoPerfil=" +
                                imgString + "&idPessoa" +
                                1;
*/

//                        Log.println(0,"LOG",urlParamenter);

                       /* OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                        out.write(urlParamenter.getBytes());
                        out.flush();
                        out.close();
                        int responseCode = urlConnection.getResponseCode();
                        if (responseCode == 200) {
                            resultado = "true";
                        }*/
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

        new TheTask().execute("http://turismo.somee.com/Estabelecimentos/Create");
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

