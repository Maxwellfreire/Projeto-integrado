package br.com.integrador.adm.Activitys;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
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
            setPrecoProduto.setText(ItemProduto.getPreco());

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

                    try {

                        ImageView minhaImageView = (ImageView) findViewById(R.id.ivImageC);
                        Bitmap imagembitmap = ((BitmapDrawable) minhaImageView.getDrawable()).getBitmap();

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

                        if (NomeProduto.isEmpty()) {

                            setNomeProduto.setError("O campo Nome está vazio !");

                        } else if (DescProduto.isEmpty()) {

                            setDescProduto.setError("O campo Descrição está vazio !");

                        } else if (PrecoProduto.isEmpty()) {

                            setPrecoProduto.setError("O campo Preço está vazio !");

                        } else {

//                            int numeroPreco = Integer.parseInt(PrecoProduto);

                            String TipoGola = String.valueOf(setTipogolaSpinner.getSelectedItem());

                            String TipoManga = String.valueOf(setTipomangaSpinner.getSelectedItem());

                            String Tamanho = String.valueOf(setTamanhoSpinner.getSelectedItem());


                            Time timeSelecionado = (Time) setTimeSpinnerProduto.getSelectedItem();
                            int idTSelecionado = timeSelecionado.getIdTime();

                            Tipoproduto tpSelecionado = (Tipoproduto) setTPSpinnerProduto.getSelectedItem();
                            int idTPSelecionado = tpSelecionado.getId();

                            Marca marcaSelecionado = (Marca) setMarcaSpinnerProduto.getSelectedItem();
                            int idMSelecionado = marcaSelecionado.getId();

                            Produto produto = new Produto(code, NomeProduto, DescProduto, PrecoProduto,
                                    TipoGola, TipoManga, Tamanho, idTSelecionado, idTPSelecionado, idMSelecionado);
                            Retrofit retrofit = APIClient.getClient();
                            ProdutoResource produtoResource = retrofit.create(ProdutoResource.class);
                            Call<Produto> call = produtoResource.post(produto);

                            call.enqueue(new Callback<Produto>() {
                                @Override
                                public void onResponse(Call<Produto> call, Response<Produto> response) {

                                    Toast.makeText(getApplicationContext(), "Produto cadastrado com sucesso !", Toast.LENGTH_LONG).show();


                                }

                                @Override
                                public void onFailure(Call<Produto> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });

                            finish();

                        }


                    } catch (Exception e) {


                        String code = SemImagem();

                        setNomeProduto = (EditText) findViewById(R.id.setNomeProduto);
                        setDescProduto = (EditText) findViewById(R.id.setDescProduto);
                        setPrecoProduto = (EditText) findViewById(R.id.setPrecoProduto);


                        String NomeProduto, DescProduto, PrecoProduto;


                        NomeProduto = setNomeProduto.getText().toString();

                        DescProduto = setDescProduto.getText().toString();

                        PrecoProduto = setPrecoProduto.getText().toString();

                        if (NomeProduto.isEmpty()) {

                            setNomeProduto.setError("O campo Nome está vazio !");

                        } else if (DescProduto.isEmpty()) {

                            setDescProduto.setError("O campo Descrição está vazio !");

                        } else if (PrecoProduto.isEmpty()) {

                            setPrecoProduto.setError("O campo Preço está vazio !");

                        } else {

//                            int numeroPreco = Integer.parseInt(PrecoProduto);

                            String TipoGola = String.valueOf(setTipogolaSpinner.getSelectedItem());

                            String TipoManga = String.valueOf(setTipomangaSpinner.getSelectedItem());

                            String Tamanho = String.valueOf(setTamanhoSpinner.getSelectedItem());


                            Time timeSelecionado = (Time) setTimeSpinnerProduto.getSelectedItem();
                            int idTSelecionado = timeSelecionado.getIdTime();

                            Tipoproduto tpSelecionado = (Tipoproduto) setTPSpinnerProduto.getSelectedItem();
                            int idTPSelecionado = tpSelecionado.getId();

                            Marca marcaSelecionado = (Marca) setMarcaSpinnerProduto.getSelectedItem();
                            int idMSelecionado = marcaSelecionado.getId();

                            Produto produto = new Produto(code, NomeProduto, DescProduto, PrecoProduto,
                                    TipoGola, TipoManga, Tamanho, idTSelecionado, idTPSelecionado, idMSelecionado);
                            Retrofit retrofit = APIClient.getClient();
                            ProdutoResource produtoResource = retrofit.create(ProdutoResource.class);
                            Call<Produto> call = produtoResource.post(produto);

                            call.enqueue(new Callback<Produto>() {
                                @Override
                                public void onResponse(Call<Produto> call, Response<Produto> response) {

                                    Toast.makeText(getApplicationContext(), "Produto cadastrado com sucesso !", Toast.LENGTH_LONG).show();


                                }

                                @Override
                                public void onFailure(Call<Produto> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });

                            finish();
                        }


                    }


                } else {

                    ImageView minhaImageView = (ImageView) findViewById(R.id.ivImageC);
                    Bitmap imagembitmap = ((BitmapDrawable) minhaImageView.getDrawable()).getBitmap();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagembitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                    final byte[] dadosdaimagem = baos.toByteArray();


                    Bitmap bitmap = BitmapFactory.decodeByteArray(dadosdaimagem, 0, dadosdaimagem.length);
                    Bitmap b = Bitmap.createScaledBitmap(bitmap, 500, 500, false);


                    String code = codeImg(b);


                    Integer ID = ItemProduto.getIdProduto();

                    setNomeProduto = (EditText) findViewById(R.id.setNomeProduto);
                    setDescProduto = (EditText) findViewById(R.id.setDescProduto);
                    setPrecoProduto = (EditText) findViewById(R.id.setPrecoProduto);


                    String NomeProduto, DescProduto, PrecoProduto;


                    NomeProduto = setNomeProduto.getText().toString();

                    DescProduto = setDescProduto.getText().toString();

                    PrecoProduto = setPrecoProduto.getText().toString();

                    if (NomeProduto.isEmpty()) {

                        setNomeProduto.setError("O campo Nome está vazio !");

                    } else if (DescProduto.isEmpty()) {

                        setDescProduto.setError("O campo Descrição está vazio !");

                    } else if (PrecoProduto.isEmpty()) {

                        setPrecoProduto.setError("O campo Preço está vazio !");

                    } else {

//                        int numeroPreco = Integer.parseInt(PrecoProduto);

                        String TipoGola = String.valueOf(setTipogolaSpinner.getSelectedItem());

                        String TipoManga = String.valueOf(setTipomangaSpinner.getSelectedItem());

                        String Tamanho = String.valueOf(setTamanhoSpinner.getSelectedItem());


                        Time timeSelecionado = (Time) setTimeSpinnerProduto.getSelectedItem();
                        int idTSelecionado = timeSelecionado.getIdTime();

                        Tipoproduto tpSelecionado = (Tipoproduto) setTPSpinnerProduto.getSelectedItem();
                        int idTPSelecionado = tpSelecionado.getId();

                        Marca marcaSelecionado = (Marca) setMarcaSpinnerProduto.getSelectedItem();
                        int idMSelecionado = marcaSelecionado.getId();

                        Produto produto = new Produto(code, NomeProduto, DescProduto, PrecoProduto,
                                TipoGola, TipoManga, Tamanho, idTSelecionado, idTPSelecionado, idMSelecionado);
                        Retrofit retrofit = APIClient.getClient();
                        ProdutoResource produtoResource = retrofit.create(ProdutoResource.class);
                        Call<Produto> call = produtoResource.put(ID, produto);

                        call.enqueue(new Callback<Produto>() {
                            @Override
                            public void onResponse(Call<Produto> call, Response<Produto> response) {

//                            Toast.makeText(getApplicationContext(), "Marca atualizada com sucesso !", Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onFailure(Call<Produto> call, Throwable t) {

                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

                        Toast.makeText(getApplicationContext(), "Produto atualizado com sucesso !", Toast.LENGTH_LONG).show();


                        finish();


                    }


                }


            }
        });

        btnExcluirProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Creating alert Dialog with two Buttons
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CadastroPActivity.this);
                // Setting Dialog Title
                alertDialog.setTitle("Confirmar exclusão...");
                // Setting Dialog Message
                alertDialog.setMessage("Tem certeza de que deseja excluir isso?");
                // Setting Icon to Dialog
                alertDialog.setIcon(R.drawable.tick);
                // Setting Positive "Yes" Button
                alertDialog.setNegativeButton("SIM",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
//                                Toast.makeText(getApplicationContext(), "Você clicou em SIM", Toast.LENGTH_SHORT).show();

                                final Integer ID = ItemProduto.getIdProduto();
                                Retrofit retrofit = APIClient.getClient();
                                ProdutoResource produtoResource = retrofit.create(ProdutoResource.class);
                                Call<Void> call = produtoResource.delete(ID);

                                call.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {

                                        Toast.makeText(getApplicationContext(), "Produto " + ID + " excluido com sucesso !", Toast.LENGTH_LONG).show();

                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                    }
                                });

                                finish();
                            }
                        });
                // Setting Negative "NO" Button
                alertDialog.setPositiveButton("NÂO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
//                                Toast.makeText(getApplicationContext(), "Você clicou em NÃO", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });
                // Showing Alert Message
                alertDialog.show();


            }
        });
    }

    public String SemImagem() {

        return "/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAoHBwgHBgoICAgLCgoLDhgQDg0NDh0VFhEYIx8lJCIf\n" +
                "IiEmKzcvJik0KSEiMEExNDk7Pj4+JS5ESUM8SDc9Pjv/2wBDAQoLCw4NDhwQEBw7KCIoOzs7Ozs7\n" +
                "Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozv/wAARCAH0AfQDASIA\n" +
                "AhEBAxEB/8QAGwABAAIDAQEAAAAAAAAAAAAAAAQFAQMGAgf/xABJEAACAQMCAwQFCAUKBgIDAAAA\n" +
                "AQIDBBEFIRIxQRNRYXEGIjKBkRQ1QlJyobHRFRYjc8EkM1NUYmSSk6PwQ0RjgqKyJeGDhPH/xAAU\n" +
                "AQEAAAAAAAAAAAAAAAAAAAAA/8QAFBEBAAAAAAAAAAAAAAAAAAAAAP/aAAwDAQACEQMRAD8A+yAA\n" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABrqXVCjl\n" +
                "TqxTXTO4GwFZV1aGX2EG3nnPkQqt7c1H7aXguSAu51qVNrtJxi+7iNVXU7an9JyfdFblCALl6xb4\n" +
                "2p1X7kv4mFrFH+iqr4fmU4bS57AXH6YofUq/+P5mVrFD+jq478J/xKZNPk8jKTxkC7jq1rJ+014S\n" +
                "W5vp3dCrvCcX4NnOgDqAc7Tua1FYp1ZLwJtLWZParDC71zAtQR6N5bVUmqqT7pvDJGV3gAOYAAAA\n" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGJNJZbxgDOVnGdzVcXFO3j\n" +
                "mpJJd2d35EC81POadKWH1eCrcm5POX4vqBOuNRr1cqE1GDWMdWQd3zz73kJNjrjm/ABld4wS6GnV\n" +
                "6+OP1YvkTqWlUaa3XE+oFM9nh7PxNlOjWm/2dKcn4L+J0MKVOisUqaiu6J6Wc835AUUdPu5v+Za+\n" +
                "3JHtaXdP6FNe8uwBSPS7pfRpy955lplyt+ybf9maL0Ac5O2uIP1qNReaz95raa5po6g8ySkt0n5r\n" +
                "IHMPbnsMrvL6rp9vV3cOF/2Niur6XXoxbpvjiufkBCxJ7RSb8XgkW97WtmlGfFH6UZLPwZoacZYa\n" +
                "w/ExzAv7e8o3DypcMsey2ScrODl1jrn3E601KrR9Sp61OOEmBdA806katNVIPKZ6AAAAAAAAAAAA\n" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAarivC3pcc+eNl3geqtWNGDnJ8ui5lJeXtS4\n" +
                "qNJuMUtkjxWu6lxVlOT2XJdxpctuXXmBiMW098vmHssvke6UJVqihTWWy3ttNp08SqrjkuS6ICDa\n" +
                "2FWq1KaUYN8nzZa07K3pezTjn62NzesJYwAG2OQAAAAAAAAAAAAAAANdW3o1lwzpRkn1a3Ky60yc\n" +
                "IynSxOK3w+a8i3AHLpbPPqtfRfMLlutmdBXsaNxu4qM/rFNc21WhNxksrPq7cwMULipbzThOXD3N\n" +
                "l1Z3UbqnlZTTw0zn08+a5myjXq0JqVOWO9d4HSAjWd2rqnlLEo+0s8iSnlZAAAAAAAAAAAAAAAAA\n" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAysZ7gPNSpClTlOT2jzKK8uZ3FZ5Xqp7LPI9395280oP1F\n" +
                "z8fMicT3xjL7wMLr4jGU/I9RpVJpuMeLHPB53zyxjvAtdKnSSUeBKp35LNnMxnKMuKLaa5FxZXyq\n" +
                "8NOo/XfJgTgM55AAAAAAAAAAAAAAAAAAAAB4q0o14OE1lP7j2AKC9spWtTZfssbSIx01SlGtTcKi\n" +
                "4ovvKO8sZ20+FS4lLeLfJeAGq3ryt6inHp0zjJeWt1C6p8UdmuaOeynyJFpXdvXjJP1eqA6AHmM4\n" +
                "zjxReUegAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAEHUbvs6fZQlib57dCXVqKlSnN\n" +
                "9Dna1V1a0puTeW8ZfQDWopLCPUIudTgistvCXeYLXTLRJfKJRTbfq5XTvAk2Vs7W34ces92/4EHU\n" +
                "bGan21BZj9KK/EuDGFywtwOZ3xnGDMJOEuNSw0TdRsXTfa0t9949CA1sm+oF3Z38KqUKksS6eJNO\n" +
                "Zi8Ykn60WXVlfQrx4ZvEl94EwAAAAAAAAAAAAAAAAAAAAANVehG4oypyjnPLc2gDnbi2lb1HTnt3\n" +
                "M0tYzHOS71O27ehxR9uG68Sky3u1hvmgLHS7rsqnYTeYy9hlucxGcqbjOPOG6OhtayuaEaqe+MNe\n" +
                "IG4AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAxKSjFybS4V1ArtYr8NNUPr7/AAKnOdzZ\n" +
                "cVe2rzqPqzWBst7b5RcdmdJCPBBLuIGl23Y0O0lvKfMngAABiXLfkVGo2Sp/ymj/AN38C4bS5vBh\n" +
                "qMo4e8WBzLecMypKGGvaJ19Y9nGVajFxiuaS+8r4+ssrcC8sb6FaKhN+vHYlz9g5qMuGSl1RcWF/\n" +
                "CrBQm8TXeBT1PSx283Tqae4VI7Y7Q8/rn/cv9Qstc0WnqlJ1IRULmEcQl1kvqvwOKr0a1vVnRqwc\n" +
                "Zw2mmsYXTAHR/rn/AHL/AFB+uf8Acv8AUOX5LL5DmB1H65/3L/UH65/3L/UOWbUebSz3mQOo/XP+\n" +
                "5f6hj9c4dbPf94cwAPoOj6nT1W2daEeFxlhrPInnD+jWpQsr/sqjxTr+rJ52i1ybO34l3oDIAAAA\n" +
                "AAABSarbulW7WP8ANyef+4uyPf0e1spwSbfNJLqBz63ZN0y57Ov2f1nghYe+3LmOJx9ZbtboDqAe\n" +
                "KVRVaammmmuh7AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABE1KbhayxzlsSyr1iulwUt+fE\n" +
                "/vAq1sbKcOOrCPNS2PD5k3SaPaXDlL2YrKAuoxjCPCuQAAAADRe3SsrSpcSi5KCy0igXprR2/kVR\n" +
                "Z68S5FtrvzLc/YPnyb4Ett4oD6NZX9vqds503xJvhcWuT/2yDe6e6a7WjHGecc8jk9O1GtpleNWj\n" +
                "JuLaU4vlJHc2F/Q1O37alnD5xljMfMCkSct0ZjN05cUfaRNvNOdPirUe/MokFLi35PufMC5sL91V\n" +
                "2dXaa+8j61olPU6LqJcNxBPhlHr4PvIEas6b4oe0W9jfO4j2dXEZLk0Bxtto+o3EnTp201OG0nL1\n" +
                "V95Y0/RC8qRzVr0aflls65JrfmaK2oWltNQr3FOnLnicksfEDmZeh13Ffs7uk/BporrrQdStVxVK\n" +
                "DnH60Hxf/Z2sdV0+bShfW8m+XDUTJMW5LKyvgB8xlGUXhxeVzWN15owd5f8Ao/ZX+ZtSpVW8udN4\n" +
                "b8zktX0uppdzwTeaUvZq4wn4eYECPqT4l7zpbb0v7O2pxr206taMUpTTST9xzXLnsAOs/XOh/U6n\n" +
                "+JB+mVFf8nU/xI53TbCrqdyqNBrxk+UfMtLv0VvKNvKqqlKfAs8Mc5f3ATv1zoZWbOrjPSSLnTNQ\n" +
                "jqVp8ojTlBcTWG+4+d92++d11Xmdv6LfMy/eSAuAAAAAFDf0uxu2orEZxyiLv059C41ai5UVUWMw\n" +
                "eH5FPyAt9IrcdOVJv2d0vAsSi0yr2d7FfRlHhZegAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                "AGUUOpzVS6wvorBenNVpOdacn1kwPD5l1pUMW3ElhtlMo5aWeZ0NpBU7Wml9VP7gNwAAAACv135l\n" +
                "ufsHz6PKP2UfQdd+Zbn7B8+jyj9lAZe6wTNO1Ktpdyq1Ntwl7UejIYeXhN7LoB9Fsb2hqNuqtvLi\n" +
                "j1UuafiQr7T5xrSrUVmLeWlzycnp2pVtLuVUo7xl7UM7NHc2F5C+sqdzT9WMovMeeGtms+aAo08P\n" +
                "K6GYTnGfFH2uhN1CwlGXbUIerj1l3EFPqnuvAC6sr7t0oT2mvvI2u6FHVKTq03wXMV6r6S8HsV9O\n" +
                "UoS4oyxJb5Liyvu3ioVHib2XiB8/uKNWjdTp1oqFSHOMorbywSbLVb2wadC4bg3lwfI7DW9Fp6lS\n" +
                "7SPDC4gnwyxz8GcNVp1KVV060Ozq/SjLv8O8DttK9IrXUHGjNOlcNey+UvJljd2lG/tZUK8OKMu9\n" +
                "cn3nzfLi04yalF5Uls0zu9C1N6jYRc5KVxDaa5ddn8AOQ1TTKul3PZTfFCWXTl3pfx5EJ8jt/SWw\n" +
                "d1psqkFmpQTlFJc0+a9+Dh3lppPD7wOz9EqVJaR2sIxUpyak1z95fS3OF0PWlpVaUaqk7efNLfhf\n" +
                "f4l3c+l1jGjL5MqlSq16qcXFfECg1+lTpa3dKnFRTlFtLlnB03ot8zL95I4qrWqXFWVapLMpyyzt\n" +
                "fRb5mX7yQFwAAAAA0Xy4rKrt9Fs546arDtaM6ecccWs9xzTWM+DaA9Um41YtPDTOkjLihF96ycxj\n" +
                "PJ4OjtZcVrTl4AbQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGJPCb7kzmW8vPe3+J0lXajNr\n" +
                "mkzm5LDwATxKP++p0tOKjTjFckkjm4pOSz3r8TpsY2AAAAAAK/XfmW5+wfPo8o/ZR9B135lufsHz\n" +
                "6PKP2UBkAAMNyfhhLzPoej2yt9KtqeGsQzjxe7+9nzxN5h55/wDLB9PguGnGK5JJIDxmLlwuUXt6\n" +
                "0Vz3KvULGVOfa0otxftIqtY1GvpvpHKrRk8dnBTjz4opN4Oisr2hqFpCvSnl8mu59wFG2s7Zyu8z\n" +
                "FyUuKMsOO6JupWSpNVqUdm8NIg7SWUsYYFzZ3lOvBQqzSmujfMj61odLU6LksRuI+xLv8GVyWfWT\n" +
                "xJci7sryNdKMniaA+fVaNShUqUqy7OrCWHGSxv193cWXo7dK11iDUsU6i4ZcT5M6bW9EpanRcoNU\n" +
                "7hbxn3nFxp1qGoQpSo8E41I8az/vzA+kNJpxe6ezTPm95b/Jb2tbvPqSws8z6SnlZXU4H0iilrld\n" +
                "rzArQtgAC2wvE7j0W+Zl+8kcP1R3Hot8zL95IC4AAAAADmqvq1qkFyUmdKc5cpK6q4+sBrXP3F9p\n" +
                "8nKxot9U/wAShXP3F9py/kFLwT/ECSAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA8z3i10wzm\n" +
                "f9/edQcxPacl4sDCfrL3fidPHeC8jm0liOx0NBt29Nt5bgvwA2AAAAAK/XfmW5+wfPo8o/ZR9B13\n" +
                "5lufsHz6PKP2UBkAAE1n7LTPpdnVVeyo1PrQT+4+ad/jzO49Grl3Gi04t+vSbhLyzt92AKL0rp8O\n" +
                "txm3tUoxf3tFdp2oV9Nu+1oyk4/SjnaS8TqfSfTFdWTuqaXaUFnPfDqcYtgPo1hfW+o2ka1LHrL1\n" +
                "o9V5kHULDsm6tLfviuWDktN1O4024dWlJtS2nH6y/M7q0vKGo2yrUsOLWJeHgBRtqSTwkwumNnnm\n" +
                "T76wVNdtbrb6S6EDOcNAXVje060FSlL1orG75mnVNEoX9WjXaUatGSeUvaXcVkGoyUobTXPBcWN7\n" +
                "CvFRqP8AaICcuR891er8o1m5mnlKTR3tw6qozdLHHj1PM+c1YVIVZRrP9rzqPxA1AAB1R3Hot8zL\n" +
                "95I4fqjuPRb5mX7yQFwAAAAAHNV23dVcv6R0pzVTeq31blkDzHn7mXum/N9Hyf4lEuZ0FkkrOkks\n" +
                "bAbwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADGTnr2n2V1NdG8nQplNrFKSrRml6rXMCAXum\n" +
                "VHOyWduF4RRvmWej1lLjoPpugLQAAAABX678y3P2D59HlH7KPoOu/Mtz9g+fR5R+ygMgAAWOjatU\n" +
                "0y5baUqM/bj1ZXAD6cpQq08xcZxktmnlM5PXtA7Ju7s4VJRl7cMZ4X3o16D6QOyhG0ulxUnLKnne\n" +
                "H5nXU69OtSVSlLjjLk0B8z5cSfNbPwJOm6hU025jXpNd0k/pRzuvuOx1D0dtNQ9eLlb1M5c6T5+D\n" +
                "K39TMN4v1jH9Dv8AFSAvbLULfUaKrUJxlhLiinus96IV5pzpN1bdSw93HG+TTp/o9c6fcxrU9QTS\n" +
                "5xlSbUv/ACLtqKTeEs9GwOb3lul5mYS4JqS2lHvJ17p8oTlWoPKe7iuhAWZ7oC5sdQVZcFXEZrk+\n" +
                "8j65o0NUoucPUuIJ8D+sscn4MgQqOnLiXNbFvZah2z7KpHE148wOBuISt68qFZyhVhs4NYWPA14z\n" +
                "nHTn4Hc61ocdUpdpBRhcw3jLHPwZxdelVo3FSjVjw1IPEl3/AJgauqO49FvmZfvJHEcL59zxzO39\n" +
                "FvmZfvJAXAAAAADVdvFlVx9R/gc4Xmo1uzs5LG8/VXkUYGYxU5KLeE2dJSjwUoR7kUFnS7a7hT9/\n" +
                "uOi6YAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABB1aObaMvqyy/InGq4pOtQnBYy1tkDnM5\n" +
                "efgSLOo6FzBrrz8jQ4uDcZLDTCeN+oHT4Bqtayq0YyznPTqjaAAAFfrvzLc/YPn0eUfso+h61CdT\n" +
                "SbiFOEpylHCjGLbfwOEjp1/wrNhdLbH8xL8gNAJH6Ov/AOo3X+RP8h+jr/8AqN1/kT/ICOCR+jr/\n" +
                "APqN1/kT/Ifo6/8A6jdf5E/yAjrnuTtO1e60ub+TtOnJ+tCe+TT+jr/+o3X+RP8AIfo6/wD6jdf5\n" +
                "E/yA6e39LrSSXbW9WEurhhr8SdS9ItKq/wDNxi+6SaOLWn3y52F2/KjNfwHyC/e0rG64e7sJP+AH\n" +
                "bT1/SoLLvafuyyFV9LtPg8whXq+UUl97OVenXy9nTrl//hmv4CWnXzWFY3fvoz/IDvNPv6GpW6rU\n" +
                "c4ftRnjMfPBD1GwlCo69Jeq+cUc5pi1XTLmNWnZXTjLaUXQnh/cdrQqu5tlPs5wcucaseFr3Ac8v\n" +
                "Wz0fcz1CpKE+KPtEy/sZU6iq0ot53kl3kFcsxaygLqx1BV8UpRxNdX1I2t6JDU6KlFKNzD2J9H4M\n" +
                "r4zmpKUNplxZX6uKfBN8NTksgcBVpyoV5UZ05U5we8H18V4Ha+i2+jL95IzrehU9Uo8UcQuIr1Z9\n" +
                "/gPRqhWt9K7KvTlTqRqSTUlh+YFuAAAB4q1Y0qbnL2YrLYFVq1birKks+rzK/ON2bKlXtJylLOWa\n" +
                "28LOMgWOj0s1p1X9GKii3I9jQdC2Sljie7wSAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAO\n" +
                "oAFJqtDsrntFvGf3EIv7+l21nUjjdYaKDDW0ua5gTtLr9nXlTk9nyfcXXJbnLNb5y0dBZV+3toyT\n" +
                "y1tLzAkgABjI58wAG3j8Rt4/EABt4/EbePxAAbePxG3j8QAG3j8Rt4/EABt4/EbePxAAdMfxC2WE\n" +
                "ABjhbTTeSn1Gx7F9rSj6vVdyLkxKKksPkBzKxjMZb+RmMpKSlHaS3J2pWPZN16afD1RA9Vxym00B\n" +
                "dWV78ogo1Hif4kxLHNnNLibzF4a3LmwvFWpxpzfrpATQAAK3Vq6VH5On60nlrwJ9arGhSlUk9kjn\n" +
                "alV1qrqvm5OS9/QDwsZ3eF3my2pSrXEaeMPiXw7zW3hZxkudKt3Stu0qe1J7eQE/qAAAAAAAAAAA\n" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABTanadlW7aC/Zy3a8S5PFShCtSdOa2bzzA5ok21eVrcZT\n" +
                "fDn1scmjTWoyoT4JPc8qTWd9nzA6WFSNSEZR5SPRT6ffqjJUqkvUfXuLhNSSaeUwAAAAAAAAAAAA\n" +
                "AAAAAAAAADEoxmsSWV3Mp9QsOybqUo7c2kXLWTzOKlHDWegHNZTWUse8zFyWZRk4yXLBOvtOVOPa\n" +
                "UFtneJX7bPGGBd6feKtTVOcvXXV9Sa8LfOEjmYtr1oyxNMlz1KrKiodeTYGdTrqvWVOEnwRe/iQn\n" +
                "s3gS5tHqNPilGEd20Bts6HbXUIYyovM33l+klhLZJYNFpZxtIPCzKXNkgAAAAAAAAAAAAAAAAAAA\n" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAiXtirlcSaVReztz8ykqJQnKM1v3HTFfqFg6qdal7XNrqwKdRy\n" +
                "0kkyystRVNKlWW2cJ5K5xak4tNSXNPmjDzjkB1CaaWOoKWy1CVvinV3p+Bb061OrDihJNefID2AA\n" +
                "AAAAAAAAAAAAAAAAAAA8yisYwsdSovdPdOTr0XmL3ccbIuG1FZk0l4lZealF8VOj02b7wKvOcPGG\n" +
                "MvvMybby3lsx1x1YAtdNseHhr1MZazHbozzp9i8qrWi8fRWN895arON+YAAAAAAAAAAAAAAAAAAA\n" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAQLzT1Vq9tT9rqu8qakZU5OEljG50pFvLGF3vykl8QKKO\n" +
                "Hnc3ULqpbyUoPlzXRnmtaVbd4lBqOdmzV1wBeW+pUarUW+GT6NdSYuRzCeFs8PJJo6hWpSw5ccFy\n" +
                "QF9gEShqVCp7VRR8H0JKqQksxkmn3AegOQyAAAAAAADGV128wMgZWMkerfW1GLbqxb6JPOQJHIj1\n" +
                "7yhRTUp5kt0kitudTq1dqT4EQpScnxZce9PqBJrahXr54morokRm1LGFv1Me5nqFOVRuMIuUvADH\n" +
                "C+LGNyysNPee0rxx3I3WWmxoxVSplz7mT8JcgCSSwuQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                "AAAAAAAAAAAAAAAAAAAAAAPNSEasXCcU4sqrrS3BcVB8S+r19xbhrIHMuDjz2xz8Dzt0eToqtrRq\n" +
                "pqVKG/Xh3+JX19IlFt0JZj0i+fxArsp84o9Qqzp+zUkl3GJ0qtNtTpyi1s8rY8+bQE2lqlemsSxU\n" +
                "XiSYaxQW9WnKPitypC2YF7DU7Saz2vD9pNGxX1q3hV4nPuTax08zDz0x70B0LvrWPOvE8S1K0j/x\n" +
                "c+SbKFZ649yGZJ7SaXcBcT1igs8EJS8cYI1XV6snmnCMH38yA3l5AG2rd3Fb+cqOXgtsGqOy3SY8\n" +
                "me4Up1HiEXJ/2QPGUuZjKfLcsKGk1KjXbNwj1w9yxo2NvRSxShKS5SlFNgVltp1at60vUi+/mW9C\n" +
                "3p28FGEUtt31Z7SwZAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                "AAAAAAxKMZrEop+ayRqmnWtR5dLDfc8EoAV09Gh/w6kv+4jVdJrw3hia7updDIHPTsrmHOhPHhue\n" +
                "fk1x/QVf8B0fMY8/iBznya4/oKv+A9RsrmayqE/fsdDjz+IAooaZcy5qMfB8yRT0j+mq48Ei12zn\n" +
                "AyBEp6ZawXscXi3zJMKcKfsxS8lg9AB1AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                "AAAAAAADx7isvNc0+zup21xcuFSCTcezk+figLMFTD0l0hyUflqWfrU5Jfeb7zVbKzVOderOMKm8\n" +
                "ZRi5J48gJ4Kj9aNJbWbmUU+sqM8fHBY293QuqPa0KiqQxniS2QG4FRP0n0qD2rTqeMKbwSbPWLDU\n" +
                "JONvWUpr6DTi0BOBXXeu6fZ3UratVqKpTSbjGnJ8/JHiHpHpVWShG74ZPpKnJfigLQEW71C2sbaN\n" +
                "evUapykoqSi3uzFnqNtf0ZVLapKai2suDXLz5gSwVcfSHTpXCt1cTdTi4Wuyl+RrfpJpTk/5W1ht\n" +
                "b05AXAKmPpNpEpKPyio34UZ4/A3XGtafaxjOpcNccVJR4W2091t0AsAVFP0m0urPh7adNvb1oNIt\n" +
                "KVWNampxkpRfJrqB7BpurqhaU4zrzcVKXDHCy2+77iPPVKVFJ1KVzThnHHOk2l8AJwI1zd07Sj21\n" +
                "zPgp7bxjJ+WyR4/SFLrSu1/+tP8AICYCLVv6FvaSvK0pxpR58VNprzXM3W9elc0I1qM+OnNZjLGM\n" +
                "gbARbnULa0rUaNafBUuG400k3nH/APSUABHrXNOnXpUXKSnWy4rDw8LL36HipqNtRv4WdWq41ake\n" +
                "KMOB7+8CWA1ySz55I1rf215UrU6FVzlRlwz9VpJ7/HkwJINdatC3pTrVHiEI5bPFne29/bqvbT44\n" +
                "N4z4gbwari4pW1N1K9RQgupHjqtvOn2sIXE6fScaMmsfDcCaCPb3tC6t+3oT46WPawzTT1W2r0o1\n" +
                "KUbhqTaTjbzfL3ATgR6V1CtUcYxqwf8A1KUo/iKN7QuLitQpzbqUXicWmsASAa69anbUZVq0+CnB\n" +
                "Zk8ZPFpeUL+3VxbT46UuTxgDeAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAcB6Q767cxXtPgxt04I5\n" +
                "O/OC1+Mv1guFh5koLHV+pED1rNrp1vCzqWc4OcoYqcNTixt9x4u+3/RelKompJz4E+ePVOksfR3T\n" +
                "4QpV5U+2nwLaT2TK/wBL+GnXsOBKKhxLC6Zx+QECdvpEfR2nXpXCV24Q9TtM45Z9U82da4tfRzUJ\n" +
                "04uMJyjGm2sfaF7pEbfR7K+pRblwLtI45vkW+nVKWtaFK0cY06tNcMVnHE11Ahej2jW99bVLq6TU\n" +
                "VPhhB8ku8rtQpfonVasbfnRalSX9nmerW91DRJ1FGLpZ+jNbGy00681y8lVrLijKfHKpy68l4Aed\n" +
                "blKprdSeHmo6fElz3iiVrmh0NOtaV1QqS4J4jKMnzym8/cR/SFOnrld03w4cOF+UEa51dV1mpTg4\n" +
                "SrSpr1Y8LUUu9/EDMrqdXQFRk8wo3ceBrk04SLz0P+arh/8AWf8A6o11/R+pS9HpW9JuVftFWcVH\n" +
                "bKzsviUVvqd9pcatOlmnx7zg49eW3xYGbJOevUYreM7mKeO7O5b67ollp2nxr0YtSjNEf0c0utX1\n" +
                "CN1Pi4KUuJScWk3zwXHpbFy0dxim3xp4QFdoGh2V/p/ymvGo5qbW0+4rI0ZX/pB8mcpRjOpwSb5q\n" +
                "MVhL4I6P0Qz+h3GSx+0k9+4pdYsrnTtSnc0oy7OVTjpyis4b3efDOQN/pDodnY20Li348cSj/ObE\n" +
                "70PuZVNPnRqY4aEsRfmUN1qGoazOlDKquEklGMcLizs8HW6FpctMseCbTq1HxVH08gPWrKvTja16\n" +
                "FB1+xrccoKOXjDW3juYt9Vt6kYupTq2jzhRuaThu30bNt/eRsI06s6E505PEpR3cX4+HMg3+r2F7\n" +
                "ZVaVC4jVrVYuFOEVlxk9k/cwNnpPwvQ6vGspOLyvNE75dTef2dxtzXYy3+4q9ehKl6NOFWKlUjGk\n" +
                "ntltqUc7feSY+kOlZf8AKVLxVN9wGv0ikpej9eWGoyUMprDS4kare5jo1WpaXOKNrhTt6stk5Nbx\n" +
                "z35ye9duKV76M16ls+0VRJRSW7xJdPcWVzbUrylw1qaqRi1KHXfvA5+4VWpe2V7c0nB1bynCjBrD\n" +
                "pxTePuwdQU+uJutprSyo3kG8dEnuy4ArtQbWsaXj69T/ANGV2qW7ufSKSg2qyseKnj66mnH70ixv\n" +
                "vW1nTcb8MqnFjp6jI8s/rbGolmPyNxT6Z4s48wPFXVq13Yyt7VcN/JcDhj1qTxvJrovzRj0eoK2v\n" +
                "NRoJ5dN0033vheX8S1jaUadzWuIU0qlRYckuZW6U+DVtWlJ8K7Sm23tthge9XlO7u7fTaLzxyU6+\n" +
                "PowRttnTtNVrWy2hc4q0/NLDX3Ii0dNjqVStf1K1xT7ebUOyquDUV6q2Xk37zxf6WrK3hdW1WvUr\n" +
                "2zUl2s3Nyj1Sz+AEq/t4XeqWdGo8R4ak2l9KS4cFlUqwik5Z36KLl+BV3061SFnqdnDj7LLnBLLc\n" +
                "ZYyku/Y9/p/S+yy7hU8LDpTg1NeGAJSrUatvWhbuLUU4tRWHF46or9Ar06eiW8ezr5SeWqUn9LyG\n" +
                "mqcaWpXVanKnGtVclGomsRxzfgR9G1exs9Io06tXgkk8xVNvqBf0q0ayzGFSP24OP4lDcW9ahcVN\n" +
                "Ss4OdSjXnGpFc5QeM/gi1s9Us7uSjRqpyfJcLQsHn5Q44adxL4bAV9eX6bu6NGi1O0pJTqzX18+z\n" +
                "5r+Jt9GF/wDCU3/bqf8AvIsKNvRtIzjTioRlNzfwIPo1Fx0aCkmnxz2fmBagAAAAAAAAAAAAAAAA\n" +
                "AAAAAAAAAAAAAAAw84294isdZeTZkAP98zHkZAGN15ee43fR++RkAYxh7L4th5cUsL3syAC5f/Yx\n" +
                "4v4gADzvl7Np97PQALPXBhLfP8TIAx62d2sdwxlYy4+Qz4P4DiS57AN85SW/PcyYbxzTR5VROMXh\n" +
                "pSxzQHp56LPvwHxPbkvAN+Gf4ji3wk337cgCznde/PMyljq35sxxLON892BxIDDTbaeWn9x69+TH\n" +
                "F4P4DiTbS5oDGH0bWNz0+TMOSSz0ePvPCr03WnRz68Em15ge309r3BZzzePEy3hJ95rp14VXNRe8\n" +
                "JcL8wPbz0WTD4ktnJvxwegBhLGeYfv8AcZAGIrZp5fmOFbc9uW5kAMJckMvu+8ADGNnu/iFnO7bM\n" +
                "gDDyt1l+AjHhzhvHczIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACt1CTV\n" +
                "/ZwlWqUoScs8MsZI/a3FCjdug5yoU3H9pnik0/aa8lv7yzrWtOvWpVJ+t2WcJ75ye1RjFJRSilFp\n" +
                "JLC3AraSjSvaUbWvOopxk5Jycopd/gzZo9OrGwpVq1WpUqTWGpPKSy8YRPhShTT4Yxi3jPCsZEaU\n" +
                "YxjFezFJYAqbudSpfTp1KtKEYb006zg8/AzwVK91b061zJyVtKT7GTSe6w/MtZ04VGnOKljdZSPS\n" +
                "gsptLKWOXQCllcVXb0PlleVOhKUo1Zp47uHf4mt1akFqMKVWpONKlDhbecPLyl4YwXnZR4HDhTi+\n" +
                "aayma4WkIXU7jLbnTVPhfLCbf8QK+V2qmp0+CqpQVtKTjxtb5NVrOpG/spQqKUaylxR4m+H7/wCB\n" +
                "cRoU4S4qcIxeGtl45EaFOMoy4I8UeTxuBU04VIWdKv2tVy+VOPC5bKPaNfgYrU1Q1K9rQ4u37BTW\n" +
                "Jvkljbx3LdUYcPDjbOceJlUo9pxuMcrZPG6QFZSdGFSlK3uXUuJySlCU28p88rpgl2UIxnc8Moyb\n" +
                "rNvHQ3xoQhLjjGCqPnLh3ZmFONPPDGK4t5YXNgewAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n" +
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAf/9k=";


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
