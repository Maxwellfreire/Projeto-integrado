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
import br.com.integrador.adm.model.Cargo;
import br.com.integrador.adm.model.Cupomdesconto;
import br.com.integrador.adm.resource.CargoResource;
import br.com.integrador.adm.resource.CupomdescontoResource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CadastroCDActivity extends AppCompatActivity {

    private Cupomdesconto ItemCupomdesconto;
    private TextView setIDCDesconto;
    private EditText setNomeCDesconto, setValorCDesconto;
    Button btnSalvarCDesconto, btnExcluirCDesconto;
    TextView txtCDesconto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cd);

        ItemCupomdesconto = (Cupomdesconto) getIntent().getSerializableExtra("objetoDataCupomdesconto");

        setIDCDesconto = (TextView) findViewById(R.id.setIDCDesconto);
        setNomeCDesconto = (EditText) findViewById(R.id.setNomeCDesconto);
        setValorCDesconto = (EditText) findViewById(R.id.setValorCDesconto);


        btnSalvarCDesconto = (Button) findViewById(R.id.btnSalvarCDesconto);
        btnExcluirCDesconto = (Button) findViewById(R.id.btnExcluirCDesconto);
        txtCDesconto = (TextView) findViewById(R.id.txtCDesconto);

        if (ItemCupomdesconto != null) {
            txtCDesconto.setText("Atualizar Cupom desconto");
            btnSalvarCDesconto.setText("Alterar");
            setIDCDesconto.setText(Integer.toString(ItemCupomdesconto.getId()));
            setNomeCDesconto.setText(ItemCupomdesconto.getName());
            setValorCDesconto.setText(ItemCupomdesconto.getValor());


        } else {
            btnExcluirCDesconto.setVisibility(View.INVISIBLE);
            btnSalvarCDesconto.setText("Salvar");

        }

        btnSalvarCDesconto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (btnSalvarCDesconto.getText().toString().equals("Salvar")) {

                    setNomeCDesconto = (EditText) findViewById(R.id.setNomeCDesconto);
                    setValorCDesconto = (EditText) findViewById(R.id.setValorCDesconto);
                    String Nome = setNomeCDesconto.getText().toString();
                    String Valor = setValorCDesconto.getText().toString();

                    if (Nome.isEmpty() && Valor.isEmpty()) {

                        //Toast.makeText(getApplicationContext(), "O campo 'Nome e Salario' esta vazio !", Toast.LENGTH_LONG).show();

                        Snackbar.make(v, "'Nome e Salario' esta vazio !", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();


                    } else {

                        String NomeCDesconto, ValorCDesconto;

                        NomeCDesconto = setNomeCDesconto.getText().toString();
                        ValorCDesconto = setValorCDesconto.getText().toString();
                        Cupomdesconto cupomdesconto = new Cupomdesconto(NomeCDesconto, ValorCDesconto);
                        Retrofit retrofit = APIClient.getClient();
                        CupomdescontoResource cupomdescontoResource = retrofit.create(CupomdescontoResource.class);
                        Call<Cupomdesconto> call = cupomdescontoResource.post(cupomdesconto);

                        call.enqueue(new Callback<Cupomdesconto>() {
                            @Override
                            public void onResponse(Call<Cupomdesconto> call, Response<Cupomdesconto> response) {

                                Toast.makeText(getApplicationContext(), "Cupom desconto cadastrado com sucesso !", Toast.LENGTH_LONG).show();


                            }

                            @Override
                            public void onFailure(Call<Cupomdesconto> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

                        finish();

                    }


                } else {

                    String NomeCDesconto, ValorCDesconto;
                    Integer ID = ItemCupomdesconto.getId();

                    NomeCDesconto = setNomeCDesconto.getText().toString();
                    ValorCDesconto = setValorCDesconto.getText().toString();
                    Cupomdesconto cupomdesconto = new Cupomdesconto(NomeCDesconto, ValorCDesconto);
                    Retrofit retrofit = APIClient.getClient();
                    CupomdescontoResource cupomdescontoResource = retrofit.create(CupomdescontoResource.class);
                    Call<Cupomdesconto> call = cupomdescontoResource.put(ID, cupomdesconto);

                    call.enqueue(new Callback<Cupomdesconto>() {
                        @Override
                        public void onResponse(Call<Cupomdesconto> call, Response<Cupomdesconto> response) {

//                            Toast.makeText(getApplicationContext(), "Marca atualizada com sucesso !", Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onFailure(Call<Cupomdesconto> call, Throwable t) {

                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                    Toast.makeText(getApplicationContext(), "Cupom desconto atualizado com sucesso !", Toast.LENGTH_LONG).show();


                    finish();

                }


            }
        });

        btnExcluirCDesconto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Creating alert Dialog with two Buttons
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CadastroCDActivity.this);
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

                                final Integer ID = ItemCupomdesconto.getId();
                                Retrofit retrofit = APIClient.getClient();
                                CupomdescontoResource cupomdescontoResource = retrofit.create(CupomdescontoResource.class);
                                Call<Void> call = cupomdescontoResource.delete(ID);

                                call.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {

                                        Toast.makeText(getApplicationContext(), "Cupom desconto " + ID + " excluido com sucesso !", Toast.LENGTH_LONG).show();

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
}
