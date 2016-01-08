package com.jctecnologia.turismoapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.appodeal.ads.Appodeal;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        String appKey = "d69fb59bbc08f36ed5a22737d26c5e5cdd3d5538793b3377";
        Appodeal.initialize(this, appKey, Appodeal.BANNER | Appodeal.INTERSTITIAL);
        Appodeal.setTesting(true);
        Appodeal.show(this, Appodeal.BANNER_BOTTOM);
    }

    private  final int DURACAO_TELA = 2000;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent novaTela = new Intent(SplashActivity.this, LoginCadastroActivity.class);
                SplashActivity.this.startActivity(novaTela);
                SplashActivity.this.finish();
            }
        },DURACAO_TELA);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        super.onResume();
        Appodeal.show(this, Appodeal.BANNER);
    }
}
