package com.jctecnologia.turismoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jctecnologia.turismoapp.model.Casa;
import com.jctecnologia.turismoapp.R;

import java.util.ArrayList;

/**
 * Created by ct002572 on 07/01/2016.
 */
public class CasaAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Casa> listaCasa;

    public CasaAdapter(Context context, ArrayList<Casa> listaCasa){
        this.context = context;
        this.listaCasa = listaCasa;
    }

    @Override
    public int getCount() {
        return listaCasa.size();
    }

    @Override
    public Object getItem(int position) {
        return listaCasa.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Casa casa = listaCasa.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.casa_adapter, null);

        TextView nome = (TextView) layout.findViewById(R.id.txtNomeCasa);
        nome.setText(casa.getNome());

        TextView numeroPessoasCasa = (TextView) layout.findViewById(R.id.txtNumeroPessoasCasa);
        numeroPessoasCasa.setText("Número Pessoas Casa: " + casa.getNumeroPessoas());

        ImageView ivCasa = (ImageView) layout.findViewById(R.id.ivCasa);
        ivCasa.setImageResource(casa.getCarrosImage(position));

        return  layout;
    }
}
