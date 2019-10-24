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
import br.com.integrador.adm.Activitys.CadastroMActivity;
import br.com.integrador.adm.R;
import br.com.integrador.adm.model.Cargo;


/**
 * Created by bruno on 07/04/18.
 */

public class APIAdapterCargo extends BaseAdapter {


    Context context;
    List<Cargo> colecao;
    private Cargo Items;

    public APIAdapterCargo(final Context applicationContext,
                           final List<Cargo> colecao) {
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

    private Cargo parsetItem(int i) {
        return this.colecao.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Cargo Item = (Cargo) getItem(i);


        if (view == null) {
            view = LayoutInflater.from(context).
                    inflate(R.layout.item_cargo,
                            viewGroup, false);
        }

        // pega o objeto corrente da lista
        Cargo cargo = parsetItem(i);

        TextView campoID, campoNOME,campoSALARIO;

        view = LayoutInflater.from(context).inflate(R.layout.item_cargo,null);

        campoID = view.findViewById(R.id.getIDCargo);
        campoNOME = view.findViewById(R.id.getNomeCargo);
        campoSALARIO = view.findViewById(R.id.getSalarioCargo);

        campoID.setText(Integer.toString(cargo.getId()));
        campoNOME.setText(cargo.getName());
        campoSALARIO.setText(cargo.getSalary());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CadastroCActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("objetoDataCargo",Item);
                context.startActivity(intent);

            }
        });




        return view;
    }

}
