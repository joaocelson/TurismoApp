/*
package com.jctecnologia.turismoapp.gcm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.iid.InstanceIDListenerService;

*/
/**
 * Created by JOAOCELSON on 11/01/2016.
 *//*

public class MyInstanceIDListenerService extends InstanceIDListenerService {
    private static final String TAG = "LOG";

    @Override
    public void onTokenRefresh() {
        //super.onTokenRefresh();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putBoolean("status", false ).apply();

        Intent it = new Intent(this, RegistrationIntentService.class);
        startService(it);
    }
}
*/
