package com.jctecnologia.turismoapp.model;

import android.media.Image;

import com.jctecnologia.turismoapp.R;

/**
 * Created by ct002572 on 07/01/2016.
 */
public class Casa {

    private String nome;
    private int numeroPessoas;
    private Image imagemCapa;
    //private List<String> telefone;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumeroPessoas() {
        return numeroPessoas;
    }

    public void setNumeroPessoas(int numeroPessoas) {
        this.numeroPessoas = numeroPessoas;
    }

    public Image getImagemCapa() {
        return imagemCapa;
    }

    public void setImagemCapa(Image imagemCapa) {
        this.imagemCapa = imagemCapa;
    }

    public int getCarrosImage(int position) {
        switch (position) {
            case 0:
                return (R.drawable.casa_1);
            case 1:
                return (R.drawable.casa_1);
            case 2:
                return (R.drawable.casa_1);
            case 3:
                return (R.drawable.casa_1);
            default:
                return (R.drawable.casa_1);
        }
    }

}
