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
import br.com.integrador.adm.Activitys.CadastroPTActivity;
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

        if (stringImagem == null) {

            String code = SemImagem();

            byte[] decodeSring = Base64.decode(code, Base64.DEFAULT);
            Bitmap decoded = BitmapFactory.decodeByteArray(decodeSring, 0, decodeSring.length);


            campoImagem.setImageBitmap(decoded);
            campoID.setText(Integer.toString(produto.getIdProduto()));
            campoNOME.setText(produto.getNameProduto());
            campoDESC.setText(produto.getDesc());
            campoPRECO.setText(produto.getPreco());

            if (produto.getIdTipoProduto() != 5) {


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

                        Intent intent = new Intent(context, CadastroPTActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("objetoDataProduto", Item);
                        context.startActivity(intent);

                    }
                });


                return view;


            } else {

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


        } else {

            byte[] decodeSring = Base64.decode(stringImagem, Base64.DEFAULT);
            Bitmap decoded = BitmapFactory.decodeByteArray(decodeSring, 0, decodeSring.length);


            campoImagem.setImageBitmap(decoded);
            campoID.setText(Integer.toString(produto.getIdProduto()));
            campoNOME.setText(produto.getNameProduto());
            campoDESC.setText(produto.getDesc());
            campoPRECO.setText(produto.getPreco());

            if (produto.getIdTipoProduto() != 5) {


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

                        Intent intent = new Intent(context, CadastroPTActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("objetoDataProduto", Item);
                        context.startActivity(intent);

                    }
                });


                return view;


            } else {

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


    }

    public String SemImagem() {

        return "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";


    }

}
