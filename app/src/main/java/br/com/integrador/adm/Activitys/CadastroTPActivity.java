package br.com.integrador.adm.Activitys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import br.com.integrador.adm.R;
import br.com.integrador.adm.boostrap.APIClient;
import br.com.integrador.adm.model.Tipoproduto;
import br.com.integrador.adm.resource.TipoprodutoResource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CadastroTPActivity extends AppCompatActivity {

    private Tipoproduto ItemTipoproduto;
    private TextView setIDTipoproduto;
    private EditText setNomeTipoproduto;
    Button btnSalvarTipoproduto, btnExcluirTipoproduto;
    TextView txtTipoproduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tp);

        ItemTipoproduto = (Tipoproduto) getIntent().getSerializableExtra("objetoDataTipoproduto");

        setIDTipoproduto = (TextView) findViewById(R.id.setIDTipoproduto);
        setNomeTipoproduto = (EditText) findViewById(R.id.setNomeTipoproduto);


        btnSalvarTipoproduto = (Button) findViewById(R.id.btnSalvarTipoproduto);
        btnExcluirTipoproduto = (Button) findViewById(R.id.btnExcluirTipoproduto);
        txtTipoproduto = (TextView) findViewById(R.id.txtTipoproduto);

        if (ItemTipoproduto != null) {
            txtTipoproduto.setText("Atualizar Tipo Produto");
            btnSalvarTipoproduto.setText("Alterar");
            setIDTipoproduto.setText(Integer.toString(ItemTipoproduto.getId()));
            setNomeTipoproduto.setText(ItemTipoproduto.getName());


        } else {
            btnExcluirTipoproduto.setVisibility(View.INVISIBLE);
            btnSalvarTipoproduto.setText("Salvar");


        }

        btnSalvarTipoproduto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (btnSalvarTipoproduto.getText().toString().equals("Salvar")) {

                    setNomeTipoproduto = (EditText) findViewById(R.id.setNomeTipoproduto);
                    String Nome = setNomeTipoproduto.getText().toString();

                    if (Nome.isEmpty()) {

                        setNomeTipoproduto.setError("O campo Nome está vazio!");


                    } else {

                        String NomeTipoproduto;
                        NomeTipoproduto = setNomeTipoproduto.getText().toString();
                        Tipoproduto tipoproduto = new Tipoproduto(NomeTipoproduto);
                        Retrofit retrofit = APIClient.getClient();
                        TipoprodutoResource tipoprodutoResource = retrofit.create(TipoprodutoResource.class);
                        Call<Tipoproduto> call = tipoprodutoResource.post(tipoproduto);

                        call.enqueue(new Callback<Tipoproduto>() {
                            @Override
                            public void onResponse(Call<Tipoproduto> call, Response<Tipoproduto> response) {

                                Toast.makeText(getApplicationContext(), "Tipo do produto cadastrado com sucesso !", Toast.LENGTH_LONG).show();

                                finish();


                            }

                            @Override
                            public void onFailure(Call<Tipoproduto> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });


                    }


                } else {

                    String NomeTipoproduto;
                    Integer ID = ItemTipoproduto.getId();
                    setNomeTipoproduto = (EditText) findViewById(R.id.setNomeTipoproduto);
                    NomeTipoproduto = setNomeTipoproduto.getText().toString();

                    if (NomeTipoproduto.isEmpty()) {

                        setNomeTipoproduto.setError("O campo Nome está vazio!");


                    } else {

                        Tipoproduto tipoproduto = new Tipoproduto(NomeTipoproduto);
                        Retrofit retrofit = APIClient.getClient();
                        TipoprodutoResource tipoprodutoResource = retrofit.create(TipoprodutoResource.class);
                        Call<Tipoproduto> call = tipoprodutoResource.put(ID, tipoproduto);

                        call.enqueue(new Callback<Tipoproduto>() {
                            @Override
                            public void onResponse(Call<Tipoproduto> call, Response<Tipoproduto> response) {

                                Toast.makeText(getApplicationContext(), "Tipo do produto atualizado com sucesso !", Toast.LENGTH_LONG).show();

                                finish();

                            }

                            @Override
                            public void onFailure(Call<Tipoproduto> call, Throwable t) {

                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });


                    }


                }


            }
        });


        btnExcluirTipoproduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Creating alert Dialog with two Buttons
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CadastroTPActivity.this);
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

                                final Integer ID = ItemTipoproduto.getId();
                                Retrofit retrofit = APIClient.getClient();
                                TipoprodutoResource tipoprodutoResource = retrofit.create(TipoprodutoResource.class);
                                Call<Void> call = tipoprodutoResource.delete(ID);

                                call.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {

                                        if (response.code() == 204) {

                                            finish();

                                            Toast.makeText(getApplicationContext(), "Tipo do produto " + ID + " excluido com sucesso !", Toast.LENGTH_LONG).show();

                                        } else {

                                            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                                    CadastroTPActivity.this).create();

                                            alertDialog.setTitle(" ");

                                            alertDialog.setMessage("Tipo do produto " + ID + " está relacionada a tabela Produtos");

                                            alertDialog.setIcon(R.drawable.tick);

                                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                    finish();

                                                }
                                            });

                                            alertDialog.show();


                                        }


                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                    }
                                });


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
}
