package com.jctecnologia.turismoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.appodeal.ads.Appodeal;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.jctecnologia.turismoapp.casa.CasaActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String nome = "",email ="";
    private static final int  PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Appodeal.show(this, Appodeal.BANNER_BOTTOM);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();

        if(intent!=null)
        {
            nome = intent.getStringExtra("nome");
            email = intent.getStringExtra("email");
        }

        NavigationView nav_view= (NavigationView)findViewById(R.id.nav_view);
        View header = nav_view.getHeaderView(0);

        TextView txtNomeNav = (TextView)header.findViewById(R.id.lblNomeNavegacao);
        txtNomeNav.setText(nome.toString());

        TextView txtEmailNav = (TextView)header.findViewById(R.id.lblEmailNavegacao);
        txtEmailNav.setText(email.toString());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_casa) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("email", email);
            intent.putExtras(bundle);
            intent.setClass(MainActivity.this,CasaActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_chale) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,ChaleActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_hotel_pousada) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,PusadaHotelActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_guia) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,GuiaActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_parque) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,ParqueActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_prefeitura) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,ParqueActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //INTEGRACAO GCM GOOGLE
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("LOG", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
}
