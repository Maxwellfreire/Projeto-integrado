package br.com.integrador.adm.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.integrador.adm.Activitys.CadastroPActivity;
import br.com.integrador.adm.Activitys.CadastroTActivity;
import br.com.integrador.adm.R;
import br.com.integrador.adm.boostrap.APIClient;
import br.com.integrador.adm.model.Cargo;
import br.com.integrador.adm.model.Marca;
import br.com.integrador.adm.model.Produto;
import br.com.integrador.adm.model.Time;
import br.com.integrador.adm.model.Tipoproduto;
import br.com.integrador.adm.resource.MarcaResource;
import br.com.integrador.adm.resource.TimeResource;
import br.com.integrador.adm.resource.TipoprodutoResource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


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
        final Produto produto = parsetItem(i);

        final TextView campoID, campoNOME, campoDESC, campoPRECO, campoTGProduto,
                campoTMProduto, campoTAMANHO, campoTProduto, campoTPProduto, campoMProduto;

        ImageView campoImagem;

        view = LayoutInflater.from(context).inflate(R.layout.item_produto, null);

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
        campoImagem = view.findViewById(R.id.getImagemProduto);

        String stringImagem = produto.getImagem();


        byte[] decodeSring = Base64.decode(stringImagem, Base64.DEFAULT);
        Bitmap decoded = BitmapFactory.decodeByteArray(decodeSring, 0, decodeSring.length);


        campoImagem.setImageBitmap(decoded);
        campoID.setText(Integer.toString(produto.getIdProduto()));
        campoNOME.setText(produto.getNameProduto());
        campoDESC.setText(produto.getDesc());
        campoPRECO.setText(produto.getPreco());
        campoTGProduto.setText(produto.getTipoGola());
        campoTMProduto.setText(produto.getTipoManga());
        campoTAMANHO.setText(produto.getTamanho());

        Retrofit retrofit = APIClient.getClient();

        TimeResource timeResource = retrofit.create(TimeResource.class);

        Call<List<Time>> get = timeResource.get();

        get.enqueue(new Callback<List<Time>>() {
            @Override
            public void onResponse(Call<List<Time>> call, Response<List<Time>> response) {


                Time t = new Time();


                List<Time> times = response.body();

                for (Time time : times) {
                    if (time.getIdTime() == produto.getIdTime()) {
                        t = time;
                    }
                }


                campoTProduto.setText(t.getNameTime());

            }

            @Override
            public void onFailure(Call<List<Time>> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }


        });

        Retrofit retrofitt = APIClient.getClient();

        TipoprodutoResource tipoprodutoResource = retrofitt.create(TipoprodutoResource.class);

        Call<List<Tipoproduto>> gett = tipoprodutoResource.get();

        gett.enqueue(new Callback<List<Tipoproduto>>() {
            @Override
            public void onResponse(Call<List<Tipoproduto>> call, Response<List<Tipoproduto>> response) {

                Tipoproduto tp = new Tipoproduto();


                List<Tipoproduto> tipoprodutos = response.body();

                for (Tipoproduto tipoproduto : tipoprodutos) {
                    if (tipoproduto.getId() == produto.getIdTipoProduto()) {
                        tp = tipoproduto;
                    }
                }


                campoTPProduto.setText(tp.getName());


            }

            @Override
            public void onFailure(Call<List<Tipoproduto>> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }


        });


        Retrofit retrofittt = APIClient.getClient();

        MarcaResource marcaResource = retrofittt.create(MarcaResource.class);

        Call<List<Marca>> gettt = marcaResource.get();

        gettt.enqueue(new Callback<List<Marca>>() {
            @Override
            public void onResponse(Call<List<Marca>> call, Response<List<Marca>> response) {

                Marca m = new Marca();


                List<Marca> marcas = response.body();

                for (Marca marca : marcas) {
                    if (marca.getId() == produto.getIdMarca()) {
                        m = marca;
                    }
                }


                campoMProduto.setText(m.getName());


            }

            @Override
            public void onFailure(Call<List<Marca>> call, Throwable t) {
                Toast.makeText(context.getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }


        });


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CadastroPActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("objetoDataProduto", Item);
                context.startActivity(intent);

            }
        });


        return view;
    }

}
