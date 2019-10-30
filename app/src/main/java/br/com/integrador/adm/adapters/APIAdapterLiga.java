package br.com.integrador.adm.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.integrador.adm.Activitys.CadastroLActivity;
import br.com.integrador.adm.Activitys.CadastroMActivity;
import br.com.integrador.adm.R;
import br.com.integrador.adm.model.Liga;
import br.com.integrador.adm.model.Marca;


/**
 * Created by bruno on 07/04/18.
 */

public class APIAdapterLiga extends BaseAdapter {


    Context context;
    List<Liga> colecao;
    private Liga Items;

    public APIAdapterLiga(final Context applicationContext,
                          final List<Liga> colecao) {
        this.context = applicationContext;
        this.colecao = colecao;

    }

    @Override
    public int getCount() {
        return this.colecao != null ? this.colecao.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return this.colecao.get(i);
    }

    private Liga parsetItem(int i) {
        return this.colecao.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Liga Item = (Liga) getItem(i);


        if (view == null) {
            view = LayoutInflater.from(context).
                    inflate(R.layout.item_liga,
                            viewGroup, false);
        }

        // pega o objeto corrente da lista
        Liga liga = parsetItem(i);

        TextView campoID, campoNOME;

        view = LayoutInflater.from(context).inflate(R.layout.item_liga,null);

        campoID = view.findViewById(R.id.getIDLiga);
        campoNOME = view.findViewById(R.id.getNomeLiga);

        campoID.setText(Integer.toString(liga.getLigaId()));
        campoNOME.setText(liga.getName());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CadastroLActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("objetoDataLiga",Item);
                context.startActivity(intent);

            }
        });




        return view;
    }

}
