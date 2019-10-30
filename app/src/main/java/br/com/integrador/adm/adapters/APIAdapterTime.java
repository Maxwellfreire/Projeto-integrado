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
import br.com.integrador.adm.Activitys.CadastroTActivity;
import br.com.integrador.adm.R;
import br.com.integrador.adm.model.Cargo;
import br.com.integrador.adm.model.Time;


/**
 * Created by bruno on 07/04/18.
 */

public class APIAdapterTime extends BaseAdapter {


    Context context;
    List<Time> colecao;
    private Time Items;

    public APIAdapterTime(final Context applicationContext,
                          final List<Time> colecao) {
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

    private Time parsetItem(int i) {
        return this.colecao.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Time Item = (Time) getItem(i);


        if (view == null) {
            view = LayoutInflater.from(context).
                    inflate(R.layout.item_time,
                            viewGroup, false);
        }

        // pega o objeto corrente da lista
        Time time = parsetItem(i);

        TextView campoID, campoNOME,campoREGIAO,campoESTADO,campoPAIS,campoTIPOTIME,campoLIGA;

        view = LayoutInflater.from(context).inflate(R.layout.item_time,null);

        campoID = view.findViewById(R.id.getIDTime);
        campoNOME = view.findViewById(R.id.getNomeTime);
        campoREGIAO = view.findViewById(R.id.getRegiaoTime);
        campoESTADO = view.findViewById(R.id.getEstadoTime);
        campoPAIS = view.findViewById(R.id.getPaisTime);
        campoTIPOTIME = view.findViewById(R.id.getTipoTime);
        campoLIGA = view.findViewById(R.id.getNomeLigaTime);

        campoID.setText(Integer.toString(time.getIdTime()));
        campoNOME.setText(time.getNameTime());
        campoREGIAO.setText(time.getRegiao());
        campoESTADO.setText(time.getEstado());
        campoPAIS.setText(time.getPais());
        campoTIPOTIME.setText(time.getTipoTime());
        campoLIGA.setText(Integer.toString(time.getLigaId()));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CadastroTActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("objetoDataTime",Item);
                context.startActivity(intent);

            }
        });




        return view;
    }

}
