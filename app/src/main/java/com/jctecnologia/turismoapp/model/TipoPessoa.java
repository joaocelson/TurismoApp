package com.jctecnologia.turismoapp.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ct002572 on 07/01/2016.
 */
public class TipoPessoa  extends RealmObject {
    public  static final String ID = "com.tecnologia.turismoapp.RealmObject.ID";

    @PrimaryKey
    private long id;

    private String descricao;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
