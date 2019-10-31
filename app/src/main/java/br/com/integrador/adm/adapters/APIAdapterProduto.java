package br.com.integrador.adm.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.integrador.adm.Activitys.CadastroTActivity;
import br.com.integrador.adm.R;
import br.com.integrador.adm.model.Produto;
import br.com.integrador.adm.model.Time;


/**
 * Created by bruno on 07/04/18.
 */

public class APIAdapterProduto extends BaseAdapter {


    Context context;
    List<Produto> colecao;
    private Produto Items;

    public APIAdapterProduto(final Context applicationContext,
                             final List<Produto> colecao) {
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

    private Produto parsetItem(int i) {
        return this.colecao.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Produto Item = (Produto) getItem(i);


        if (view == null) {
            view = LayoutInflater.from(context).
                    inflate(R.layout.item_produto,
                            viewGroup, false);
        }

        // pega o objeto corrente da lista
        Produto produto = parsetItem(i);

        TextView campoID, campoNOME,campoDESC,campoPRECO,campoTGProduto,
                campoTMProduto,campoTAMANHO,campoTProduto,campoTPProduto,campoMProduto;

        view = LayoutInflater.from(context).inflate(R.layout.item_produto,null);

        campoID = view.findViewById(R.id.getIDProduto);
        campoNOME = view.findViewById(R.id.getNomeProduto);
        campoDESC = view.findViewById(R.id.getDescriProduto);
        campoPRECO = view.findViewById(R.id.getPrecoProduto);
        campoTGProduto = view.findViewById(R.id.getTGProduto);
        campoTMProduto = view.findViewById(R.id.getTMProduto);
        campoTAMANHO = view.findViewById(R.id.getTamanhoProduto);
        campoTProduto = view.findViewById(R.id.getTProduto);
        campoTPProduto = view.findViewById(R.id.getTPProduto);
        campoMProduto = view.findViewById(R.id.getMarcaProduto);

        campoID.setText(Integer.toString(produto.getIdProduto()));
        campoNOME.setText(produto.getNameProduto());
        campoDESC.setText(produto.getDesc());
        campoPRECO.setText(Integer.toString(produto.getPreco()));
        campoTGProduto.setText(produto.getTipoGola());
        campoTMProduto.setText(produto.getTipoManga());
        campoTAMANHO.setText(produto.getTamanho());
        campoTProduto.setText(Integer.toString(produto.getIdTime()));
        campoTPProduto.setText(Integer.toString(produto.getIdTipoProduto()));
        campoMProduto.setText(Integer.toString(produto.getIdMarca()));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CadastroTActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("objetoDataProduto",Item);
                context.startActivity(intent);

            }
        });




        return view;
    }

}
