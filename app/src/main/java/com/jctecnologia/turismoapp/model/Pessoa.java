package com.jctecnologia.turismoapp.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ct002572 on 07/01/2016.
 */
public class Pessoa extends RealmObject{

    @PrimaryKey
    private long id;

    private String nome;
    private String email;
    private String password;
    private Boolean manterLogado;

    private  int tipoPessoa;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getManterLogado() {
        return manterLogado;
    }

    public void setManterLogado(Boolean manterLogado) {
        this.manterLogado = manterLogado;
    }

    public int getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(int tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }
}
