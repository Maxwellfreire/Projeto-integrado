package br.com.integrador.adm.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.integrador.adm.R;
import br.com.integrador.adm.boostrap.APIClient;
import br.com.integrador.adm.model.Liga;
import br.com.integrador.adm.model.Marca;
import br.com.integrador.adm.model.Produto;
import br.com.integrador.adm.model.Time;
import br.com.integrador.adm.model.Tipoproduto;
import br.com.integrador.adm.resource.LigaResource;
import br.com.integrador.adm.resource.MarcaResource;
import br.com.integrador.adm.resource.TimeResource;
import br.com.integrador.adm.resource.TipoprodutoResource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CadastroPActivity extends AppCompatActivity {
    private Produto ItemProduto;
    private TextView setIDProduto;
    private EditText setNomeProduto, setDescProduto, setPrecoProduto;
    Button btnSalvarProduto, btnExcluirProduto;
    TextView txtProduto;

    Spinner setTipogolaSpinner, setTipomangaSpinner, setTamanhoSpinner, tipogolaS, tipomangaS, tamanhoS;

    String[] SpinnerTipoGola = {"Gola Redonda", "V"};

    String[] SpinnerTipoManga = {"Curta", "Longa"};

    String[] SpinnerTamanho = {"P", "M", "G"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_p);

        setTipogolaSpinner = (Spinner) findViewById(R.id.setTipogolaSpinner);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SpinnerTipoGola);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setTipogolaSpinner.setAdapter(arrayAdapter);


        setTipomangaSpinner = (Spinner) findViewById(R.id.setTipomangaSpinner);

        ArrayAdapter<String> arrayAdap = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SpinnerTipoManga);
        arrayAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setTipomangaSpinner.setAdapter(arrayAdap);


        setTamanhoSpinner = (Spinner) findViewById(R.id.setTamanhoSpinner);

        ArrayAdapter<String> array = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SpinnerTamanho);
        array.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setTamanhoSpinner.setAdapter(array);

        ItemProduto = (Produto) getIntent().getSerializableExtra("objetoDataProduto");

        setIDProduto = (TextView) findViewById(R.id.setIDProduto);
        setNomeProduto = (EditText) findViewById(R.id.setNomeProduto);
        setDescProduto = (EditText) findViewById(R.id.setDescProduto);
        setPrecoProduto = (EditText) findViewById(R.id.setPrecoProduto);

        btnSalvarProduto = (Button) findViewById(R.id.btnSalvarProduto);
        btnExcluirProduto = (Button) findViewById(R.id.btnExcluirProduto);
        txtProduto = (TextView) findViewById(R.id.txtProduto);

        if (ItemProduto != null) {

            txtProduto.setText("Atualizar Produto");
            setIDProduto.setText(Integer.toString(ItemProduto.getIdProduto()));
            setNomeProduto.setText(ItemProduto.getNameProduto());
            setDescProduto.setText(ItemProduto.getDesc());
            setPrecoProduto.setText(Integer.toString(ItemProduto.getPreco()));

            String[] tipogola = {ItemProduto.getTipoGola(), "Gola Redonda", "V"};
            tipogolaS = (Spinner) findViewById(R.id.setTipogolaSpinner);
            ArrayAdapter<String> regiaoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipogola);
            regiaoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            tipogolaS.setAdapter(regiaoAdapter);

            String[] tipomanga = {ItemProduto.getTipoManga(), "Curta", "Longa"};
            tipomangaS = (Spinner) findViewById(R.id.setTipomangaSpinner);
            ArrayAdapter<String> estadoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipomanga);
            estadoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            tipomangaS.setAdapter(estadoAdapter);


            String[] tamanho = {ItemProduto.getTamanho(), "P", "M", "G"};
            tamanhoS = (Spinner) findViewById(R.id.setTamanhoSpinner);
            ArrayAdapter<String> tipotimeoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tamanho);
            tipotimeoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            tamanhoS.setAdapter(tipotimeoAdapter);

            AtualizarTime();

            AtualizarTipoProduto();

            AtualizarMarca();


            btnSalvarProduto.setText("Alterar");


        } else {
            AtualizarTime();
            AtualizarTipoProduto();
            AtualizarMarca();
            btnExcluirProduto.setVisibility(View.INVISIBLE);
            btnSalvarProduto.setText("Salvar");

        }
    }

    private void AtualizarTime() {

        Retrofit retrofit = APIClient.getClient();

        TimeResource timeResource = retrofit.create(TimeResource.class);

        Call<List<Time>> get = timeResource.get();

        get.enqueue(new Callback<List<Time>>() {
            @Override
            public void onResponse(Call<List<Time>> call, Response<List<Time>> response) {

                Spinner spinnerTimes = (Spinner) findViewById(R.id.setTimeSpinnerProduto);
                List<Time> times = response.body();

                ArrayAdapter<Time> adapterSpinner = new ArrayAdapter<Time>(CadastroPActivity.this, android.R.layout.simple_list_item_1, times);
                spinnerTimes.setAdapter(adapterSpinner);


            }

            @Override
            public void onFailure(Call<List<Time>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }


        });


    }

    private void AtualizarTipoProduto() {

        Retrofit retrofit = APIClient.getClient();

        TipoprodutoResource tipoprodutoResource = retrofit.create(TipoprodutoResource.class);

        Call<List<Tipoproduto>> get = tipoprodutoResource.get();

        get.enqueue(new Callback<List<Tipoproduto>>() {
            @Override
            public void onResponse(Call<List<Tipoproduto>> call, Response<List<Tipoproduto>> response) {

                Spinner spinnerTipoProduto = (Spinner) findViewById(R.id.setTPSpinnerProduto);
                List<Tipoproduto> tipoprodutos = response.body();

                ArrayAdapter<Tipoproduto> adapterSpinner = new ArrayAdapter<Tipoproduto>(CadastroPActivity.this, android.R.layout.simple_list_item_1, tipoprodutos);
                spinnerTipoProduto.setAdapter(adapterSpinner);


            }

            @Override
            public void onFailure(Call<List<Tipoproduto>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }


        });


    }

    private void AtualizarMarca() {

        Retrofit retrofit = APIClient.getClient();

        MarcaResource marcaResource = retrofit.create(MarcaResource.class);

        Call<List<Marca>> get = marcaResource.get();

        get.enqueue(new Callback<List<Marca>>() {
            @Override
            public void onResponse(Call<List<Marca>> call, Response<List<Marca>> response) {

                Spinner spinnerMarca = (Spinner) findViewById(R.id.setMarcaSpinnerProduto);
                List<Marca> Marcas = response.body();

                ArrayAdapter<Marca> adapterSpinner = new ArrayAdapter<Marca>(CadastroPActivity.this, android.R.layout.simple_list_item_1, Marcas);
                spinnerMarca.setAdapter(adapterSpinner);


            }

            @Override
            public void onFailure(Call<List<Marca>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }


        });


    }
}
