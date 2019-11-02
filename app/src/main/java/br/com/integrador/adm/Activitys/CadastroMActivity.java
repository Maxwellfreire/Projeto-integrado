package br.com.integrador.adm.Activitys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;


import br.com.integrador.adm.R;
import br.com.integrador.adm.boostrap.APIClient;
import br.com.integrador.adm.model.Marca;
import br.com.integrador.adm.resource.MarcaResource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CadastroMActivity extends AppCompatActivity {

    private Marca ItemMarca;
    private TextView tvID;
    private EditText etNome;
    Button btnSalvarMarca, btnExcluirMarca;
    TextView txtMarca;
    public ListView minhaListaMarca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_m);

        ItemMarca = (Marca) getIntent().getSerializableExtra("objetoDataMarca");

        tvID = (TextView) findViewById(R.id.setIDMarca);
        etNome = (EditText) findViewById(R.id.setNomeMarca);


        btnSalvarMarca = (Button) findViewById(R.id.btnSalvarMarca);
        btnExcluirMarca = (Button) findViewById(R.id.btnExcluirMarca);
        txtMarca = (TextView) findViewById(R.id.txtMarca);

        if (ItemMarca != null) {
            txtMarca.setText("Atualizar Marca");
            btnSalvarMarca.setText("Alterar");
            tvID.setText(Integer.toString(ItemMarca.getId()));
            etNome.setText(ItemMarca.getName());


        } else {
            btnExcluirMarca.setVisibility(View.INVISIBLE);
            btnSalvarMarca.setText("Salvar");


        }

        btnSalvarMarca.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (btnSalvarMarca.getText().toString().equals("Salvar")) {

                    etNome = (EditText) findViewById(R.id.setNomeMarca);
                    String Nome = etNome.getText().toString();

                    if (Nome.isEmpty()) {

//                        Toast.makeText(getApplicationContext(), "O campo 'Nome' esta vazio !", Toast.LENGTH_LONG).show();

                        Snackbar.make(v, "'Nome' esta vazio !", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();


                    } else {

                        String NomeMarca;
                        NomeMarca = etNome.getText().toString();
                        Marca marca = new Marca(NomeMarca);
                        Retrofit retrofit = APIClient.getClient();
                        MarcaResource marcaResource = retrofit.create(MarcaResource.class);
                        Call<Marca> call = marcaResource.post(marca);

                        call.enqueue(new Callback<Marca>() {
                            @Override
                            public void onResponse(Call<Marca> call, Response<Marca> response) {

                                Toast.makeText(getApplicationContext(), "Marca cadastrada com sucesso !", Toast.LENGTH_LONG).show();


                            }

                            @Override
                            public void onFailure(Call<Marca> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });


                        finish();

                    }


                } else {

                    String NomeMarca;
                    Integer ID = ItemMarca.getId();
                    NomeMarca = etNome.getText().toString();
                    Marca marca = new Marca(NomeMarca);
                    Retrofit retrofit = APIClient.getClient();
                    MarcaResource marcaResource = retrofit.create(MarcaResource.class);
                    Call<Marca> call = marcaResource.put(ID, marca);

                    call.enqueue(new Callback<Marca>() {
                        @Override
                        public void onResponse(Call<Marca> call, Response<Marca> response) {

//                            Toast.makeText(getApplicationContext(), "Marca atualizada com sucesso !", Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onFailure(Call<Marca> call, Throwable t) {

                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                    Toast.makeText(getApplicationContext(), "Marca atualizada com sucesso !", Toast.LENGTH_LONG).show();


                    finish();

                }


            }
        });


        btnExcluirMarca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Creating alert Dialog with two Buttons
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CadastroMActivity.this);
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

                                final Integer ID = ItemMarca.getId();
                                Retrofit retrofit = APIClient.getClient();
                                MarcaResource marcaResource = retrofit.create(MarcaResource.class);
                                Call<Void> call = marcaResource.delete(ID);

                                call.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {

                                        Toast.makeText(getApplicationContext(), "Marca " + ID + " excluida com sucesso !", Toast.LENGTH_LONG).show();

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


