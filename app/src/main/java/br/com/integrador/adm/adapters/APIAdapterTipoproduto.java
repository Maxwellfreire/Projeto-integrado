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
import br.com.integrador.adm.Activitys.CadastroTPActivity;
import br.com.integrador.adm.R;
import br.com.integrador.adm.model.Cargo;
import br.com.integrador.adm.model.Tipoproduto;


/**
 * Created by bruno on 07/04/18.
 */

public class APIAdapterTipoproduto extends BaseAdapter {


    Context context;
    List<Tipoproduto> colecao;
    private Tipoproduto Items;

    public APIAdapterTipoproduto(final Context applicationContext,
                                 final List<Tipoproduto> colecao) {
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

    private Tipoproduto parsetItem(int i) {
        return this.colecao.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Tipoproduto Item = (Tipoproduto) getItem(i);


        if (view == null) {
            view = LayoutInflater.from(context).
                    inflate(R.layout.item_tipoproduto,
                            viewGroup, false);
        }

        // pega o objeto corrente da lista
        Tipoproduto tipoproduto = parsetItem(i);

        TextView campoID, campoNOME;

        view = LayoutInflater.from(context).inflate(R.layout.item_tipoproduto,null);

        campoID = view.findViewById(R.id.getIDTipoproduto);
        campoNOME = view.findViewById(R.id.getNomeTipoproduto);

        campoID.setText(Integer.toString(tipoproduto.getId()));
        campoNOME.setText(tipoproduto.getName());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CadastroTPActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("objetoDataTipoproduto",Item);
                context.startActivity(intent);

            }
        });




        return view;
    }

}
