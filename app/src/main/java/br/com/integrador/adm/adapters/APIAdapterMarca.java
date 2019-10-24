package br.com.integrador.adm.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.integrador.adm.Activitys.CadastroMActivity;
import br.com.integrador.adm.R;
import br.com.integrador.adm.model.Marca;


/**
 * Created by bruno on 07/04/18.
 */

public class APIAdapterMarca extends BaseAdapter {


    Context context;
    List<Marca> colecao;
    private Marca Items;

    public APIAdapterMarca(final Context applicationContext,
                           final List<Marca> colecao) {
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

    private Marca parsetItem(int i) {
        return this.colecao.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Marca Item = (Marca) getItem(i);


        if (view == null) {
            view = LayoutInflater.from(context).
                    inflate(R.layout.item_marca,
                            viewGroup, false);
        }

        // pega o objeto corrente da lista
        Marca marca = parsetItem(i);

        TextView campoID, campoNOME;

        view = LayoutInflater.from(context).inflate(R.layout.item_marca,null);

        campoID = view.findViewById(R.id.getIDMarca);
        campoNOME = view.findViewById(R.id.getNomeMarca);

        campoID.setText(Integer.toString(marca.getId()));
        campoNOME.setText(marca.getName());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CadastroMActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("objetoDataMarca",Item);
                context.startActivity(intent);

            }
        });




        return view;
    }

}
