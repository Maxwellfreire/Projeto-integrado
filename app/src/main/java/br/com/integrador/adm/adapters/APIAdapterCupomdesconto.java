package br.com.integrador.adm.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.integrador.adm.Activitys.CadastroCActivity;
import br.com.integrador.adm.Activitys.CadastroCDActivity;
import br.com.integrador.adm.R;
import br.com.integrador.adm.model.Cargo;
import br.com.integrador.adm.model.Cupomdesconto;


/**
 * Created by bruno on 07/04/18.
 */

public class APIAdapterCupomdesconto extends BaseAdapter {


    Context context;
    List<Cupomdesconto> colecao;
    private Cupomdesconto Items;

    public APIAdapterCupomdesconto(final Context applicationContext,
                                   final List<Cupomdesconto> colecao) {
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

    private Cupomdesconto parsetItem(int i) {
        return this.colecao.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Cupomdesconto Item = (Cupomdesconto) getItem(i);


        if (view == null) {
            view = LayoutInflater.from(context).
                    inflate(R.layout.item_cupomdesconto,
                            viewGroup, false);
        }

        // pega o objeto corrente da lista
        Cupomdesconto cupomdesconto = parsetItem(i);

        TextView campoID, campoNOME,campoSALARIO;

        view = LayoutInflater.from(context).inflate(R.layout.item_cupomdesconto,null);

        campoID = view.findViewById(R.id.getIDCDesconto);
        campoNOME = view.findViewById(R.id.getNomeCDesconto);
        campoSALARIO = view.findViewById(R.id.getSalarioCDesconto);

        campoID.setText(Integer.toString(cupomdesconto.getId()));
        campoNOME.setText(cupomdesconto.getName());
        campoSALARIO.setText(cupomdesconto.getValor());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CadastroCDActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("objetoDataCupomdesconto",Item);
                context.startActivity(intent);

            }
        });




        return view;
    }

}
