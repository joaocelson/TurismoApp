package com.jctecnologia.util;

import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jctecnologia.turismoapp.R;
import com.jctecnologia.turismoapp.model.Pessoa;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;


/**
 * Created by JOAOCELSON on 08/01/2016.
 */
public class DaoConsulta {

    public static Pessoa ObterUsuarioLogado(String emailLogado) {
        try {
            Realm realm = Realm.getDefaultInstance();
            RealmResults<Pessoa> realmResults = realm.where(Pessoa.class).equalTo("email", emailLogado).findAll();

            if (realmResults.size() > 0) {
                return realmResults.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Pessoa ObterUsuarioLogado(String emailLogado, String password) {
        try {
            Realm realm = Realm.getDefaultInstance();
            RealmResults<Pessoa> realmResults = realm.where(Pessoa.class).equalTo("email", emailLogado).equalTo("password", password).findAll();

            if (realmResults.size() > 0) {
                return realmResults.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
