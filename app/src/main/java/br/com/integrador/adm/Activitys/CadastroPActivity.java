package br.com.integrador.adm.Activitys;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.util.List;

import br.com.integrador.adm.Permissao;
import br.com.integrador.adm.R;
import br.com.integrador.adm.boostrap.APIClient;
import br.com.integrador.adm.model.Liga;
import br.com.integrador.adm.model.Marca;
import br.com.integrador.adm.model.Produto;
import br.com.integrador.adm.model.Time;
import br.com.integrador.adm.model.Tipoproduto;
import br.com.integrador.adm.resource.LigaResource;
import br.com.integrador.adm.resource.MarcaResource;
import br.com.integrador.adm.resource.ProdutoResource;
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
    ImageView ivImageC;
    final int SELECAO_GALERIA = 22131;


    private String[] permissoesNecessarias = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,

    };

    Spinner setTipogolaSpinner, setTipomangaSpinner, setTamanhoSpinner, setTimeSpinnerProduto, setTPSpinnerProduto, setMarcaSpinnerProduto, tipogolaS, tipomangaS, tamanhoS;

    String[] SpinnerTipoGola = {"Gola Redonda", "V"};

    String[] SpinnerTipoManga = {"Curta", "Longa"};

    String[] SpinnerTamanho = {"P", "M", "G"};

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_p);

        //Validar permissões
        Permissao.validarPermissoes(permissoesNecessarias, this, 1);

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

        setTimeSpinnerProduto = (Spinner) findViewById(R.id.setTimeSpinnerProduto);

        setTPSpinnerProduto = (Spinner) findViewById(R.id.setTPSpinnerProduto);

        setMarcaSpinnerProduto = (Spinner) findViewById(R.id.setMarcaSpinnerProduto);

        ItemProduto = (Produto) getIntent().getSerializableExtra("objetoDataProduto");

        setIDProduto = (TextView) findViewById(R.id.setIDProduto);
        ivImageC = (ImageView) findViewById(R.id.ivImageC);
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

            try {

                byte[] decodeString = Base64.decode(ItemProduto.getImagem(), Base64.DEFAULT);
                Bitmap decoded = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);

                ivImageC.setImageBitmap(decoded);


                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                decoded.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                final byte[] dadosdaimagem = baos.toByteArray();

                ivImageC.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view1) {
                        Intent i = new Intent(CadastroPActivity.this, VisualActivity.class);
                        i.putExtra("fotoescolhida", dadosdaimagem);
                        startActivity(i);
                    }
                });

            } catch (Exception e) {

                ivImageC.setImageBitmap(null);


            }


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

        btnSalvarProduto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (btnSalvarProduto.getText().toString().equals("Salvar")) {

                    ImageView minhaImageView = (ImageView) findViewById(R.id.ivImageC);
                    Bitmap imagembitmap = ((BitmapDrawable)minhaImageView.getDrawable()).getBitmap();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagembitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                    final byte[] dadosdaimagem = baos.toByteArray();


                    Bitmap bitmap = BitmapFactory.decodeByteArray(dadosdaimagem, 0, dadosdaimagem.length);
                    Bitmap b = Bitmap.createScaledBitmap(bitmap, 500, 500, false);


                    String code = codeImg(b);

                    setNomeProduto = (EditText) findViewById(R.id.setNomeProduto);
                    setDescProduto = (EditText) findViewById(R.id.setDescProduto);
                    setPrecoProduto = (EditText) findViewById(R.id.setPrecoProduto);


                    String NomeProduto, DescProduto, PrecoProduto;


                    NomeProduto = setNomeProduto.getText().toString();

                    DescProduto = setDescProduto.getText().toString();

                    PrecoProduto = setPrecoProduto.getText().toString();

                    int numeroPreco = Integer.parseInt(PrecoProduto);

                    String TipoGola = String.valueOf(setTipogolaSpinner.getSelectedItem());

                    String TipoManga = String.valueOf(setTipomangaSpinner.getSelectedItem());

                    String Tamanho = String.valueOf(setTamanhoSpinner.getSelectedItem());


                    Time timeSelecionado = (Time) setTimeSpinnerProduto.getSelectedItem();
                    int idTSelecionado = timeSelecionado.getIdTime();

                    Tipoproduto tpSelecionado = (Tipoproduto) setTPSpinnerProduto.getSelectedItem();
                    int idTPSelecionado = tpSelecionado.getId();

                    Marca marcaSelecionado = (Marca) setMarcaSpinnerProduto.getSelectedItem();
                    int idMSelecionado = marcaSelecionado.getId();

                    Produto produto = new Produto(code, NomeProduto, DescProduto, numeroPreco,
                            TipoGola, TipoManga, Tamanho, idTSelecionado, idTPSelecionado, idMSelecionado);
                    Retrofit retrofit = APIClient.getClient();
                    ProdutoResource produtoResource = retrofit.create(ProdutoResource.class);
                    Call<Produto> call = produtoResource.post(produto);

                    call.enqueue(new Callback<Produto>() {
                        @Override
                        public void onResponse(Call<Produto> call, Response<Produto> response) {

                            Toast.makeText(getApplicationContext(), "Produto cadastrada com sucesso !", Toast.LENGTH_LONG).show();


                        }

                        @Override
                        public void onFailure(Call<Produto> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                    finish();


                } else {


//                    String NomeTime, PaisTime;
//                    Integer ID = ItemTime.getIdTime();
//
//                    NomeTime = setNomeTime.getText().toString();
//
//                    String Regiao = String.valueOf(setRegiaoTime.getSelectedItem());
//
//                    String Estado = String.valueOf(setEstadoTime.getSelectedItem());
//
//                    PaisTime = setPaisesTime.getText().toString();
//
//                    String tipotime = String.valueOf(setTipoTimeTime.getSelectedItem());
//
//                    Liga ligaSelecionada = (Liga) setLigasTime.getSelectedItem();
//                    int idSelecionado = ligaSelecionada.getLigaId();
//
//                    Time time = new Time(NomeTime, Regiao, Estado, PaisTime, tipotime, idSelecionado);
//                    Retrofit retrofit = APIClient.getClient();
//                    TimeResource timeResource = retrofit.create(TimeResource.class);
//                    Call<Time> call = timeResource.put(ID, time);
//
//                    call.enqueue(new Callback<Time>() {
//                        @Override
//                        public void onResponse(Call<Time> call, Response<Time> response) {
//
////                            Toast.makeText(getApplicationContext(), "Marca atualizada com sucesso !", Toast.LENGTH_LONG).show();
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<Time> call, Throwable t) {
//
//                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
//
//                    Toast.makeText(getApplicationContext(), "Time atualizada com sucesso !", Toast.LENGTH_LONG).show();
//
//
//                    finish();


                }


            }
        });
    }

    public String codeImg(Bitmap b) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] b1 = baos.toByteArray();
        return Base64.encodeToString(b1, Base64.DEFAULT);


    }

    public void btnFoto(View view) {

        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (i.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(i, SELECAO_GALERIA);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ivImageC = (ImageView) findViewById(R.id.ivImageC);
        if (resultCode == RESULT_OK) {
            Bitmap imagem = null;

            try {

                switch (requestCode) {
                    case SELECAO_GALERIA:
                        Uri localfoto = data.getData();
                        imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localfoto);
                        break;


                }
                if (imagem != null) {


                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                    final byte[] dadosdaimagem = baos.toByteArray();


                    Bitmap bitmap = BitmapFactory.decodeByteArray(dadosdaimagem, 0, dadosdaimagem.length);
                    Bitmap b = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
                    ivImageC.setImageBitmap(b);


                    String code = codeImg(b);


                    ivImageC.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view1) {

                            try {

                                Intent i = new Intent(CadastroPActivity.this, VisualActivity.class);
                                i.putExtra("fotoescolhida", dadosdaimagem);
                                startActivity(i);


                            } catch (Exception e) {


                                Snackbar.make(view1, "Imagem muito grande para visualizar !", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();


                            }

                        }
                    });


                }

            } catch (Exception e) {


            }


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
