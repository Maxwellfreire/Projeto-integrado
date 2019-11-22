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
import br.com.integrador.adm.resource.CargoResource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CadastroCActivity extends AppCompatActivity {

    private Cargo ItemCargo;
    private TextView setIDCargo;
    private EditText setNomeCargo, setSalarioCargo;
    Button btnSalvarCargo, btnExcluirCargo;
    TextView txtCargo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_c);


        ItemCargo = (Cargo) getIntent().getSerializableExtra("objetoDataCargo");

        setIDCargo = (TextView) findViewById(R.id.setIDCargo);
        setNomeCargo = (EditText) findViewById(R.id.setNomeCargo);
        setSalarioCargo = (EditText) findViewById(R.id.setSalarioCargo);


        btnSalvarCargo = (Button) findViewById(R.id.btnSalvarCargo);
        btnExcluirCargo = (Button) findViewById(R.id.btnExcluirCargo);
        txtCargo = (TextView) findViewById(R.id.txtCargo);

        if (ItemCargo != null) {
            txtCargo.setText("Atualizar Cargo");
            btnSalvarCargo.setText("Alterar");
            setIDCargo.setText(Integer.toString(ItemCargo.getId()));
            setNomeCargo.setText(ItemCargo.getName());
            setSalarioCargo.setText(ItemCargo.getSalary());


        } else {
            btnExcluirCargo.setVisibility(View.INVISIBLE);
            btnSalvarCargo.setText("Salvar");

        }

        btnSalvarCargo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (btnSalvarCargo.getText().toString().equals("Salvar")) {

                    setNomeCargo = (EditText) findViewById(R.id.setNomeCargo);
                    setSalarioCargo = (EditText) findViewById(R.id.setSalarioCargo);
                    String Nome = setNomeCargo.getText().toString();
                    String Salario = setSalarioCargo.getText().toString();

                    if (Nome.isEmpty()) {


                        setNomeCargo.setError("O campo Nome está vazio!");


                    } else if (Salario.isEmpty()) {


                        setSalarioCargo.setError("O campo Salario está vazio!");


                    } else {

                        String NomeCargo, SalarioCargo;

                        NomeCargo = setNomeCargo.getText().toString();
                        SalarioCargo = setSalarioCargo.getText().toString();
                        Cargo cargo = new Cargo(NomeCargo, SalarioCargo);
                        Retrofit retrofit = APIClient.getClient();
                        CargoResource cargoResource = retrofit.create(CargoResource.class);
                        Call<Cargo> call = cargoResource.post(cargo);

                        call.enqueue(new Callback<Cargo>() {
                            @Override
                            public void onResponse(Call<Cargo> call, Response<Cargo> response) {

                                Toast.makeText(getApplicationContext(), "Cargo cadastrado com sucesso !", Toast.LENGTH_LONG).show();


                            }

                            @Override
                            public void onFailure(Call<Cargo> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

                        finish();

                    }


                } else {

                    String NomeCargo, SalarioCargo;
                    Integer ID = ItemCargo.getId();
                    setNomeCargo = (EditText) findViewById(R.id.setNomeCargo);
                    setSalarioCargo = (EditText) findViewById(R.id.setSalarioCargo);
                    NomeCargo = setNomeCargo.getText().toString();
                    SalarioCargo = setSalarioCargo.getText().toString();

                    if (NomeCargo.isEmpty()) {

                        setNomeCargo.setError("O campo Nome está vazio!");


                    } else if (SalarioCargo.isEmpty()) {

                        setSalarioCargo.setError("O campo Salario está vazio!");


                    } else {

                        Cargo cargo = new Cargo(NomeCargo, SalarioCargo);
                        Retrofit retrofit = APIClient.getClient();
                        CargoResource cargoResource = retrofit.create(CargoResource.class);
                        Call<Cargo> call = cargoResource.put(ID, cargo);

                        call.enqueue(new Callback<Cargo>() {
                            @Override
                            public void onResponse(Call<Cargo> call, Response<Cargo> response) {

//                            Toast.makeText(getApplicationContext(), "Marca atualizada com sucesso !", Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onFailure(Call<Cargo> call, Throwable t) {

                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

                        Toast.makeText(getApplicationContext(), "Cargo atualizado com sucesso !", Toast.LENGTH_LONG).show();


                        finish();

                    }


                }


            }
        });

        btnExcluirCargo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Creating alert Dialog with two Buttons
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CadastroCActivity.this);
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

                                final Integer ID = ItemCargo.getId();
                                Retrofit retrofit = APIClient.getClient();
                                CargoResource cargoResource = retrofit.create(CargoResource.class);
                                Call<Void> call = cargoResource.delete(ID);

                                call.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {

                                        if (response.code() == 204) {

                                            finish();

                                            Toast.makeText(getApplicationContext(), "Cargo " + ID + " excluido com sucesso !", Toast.LENGTH_LONG).show();


                                        } else {

                                            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(
                                                    CadastroCActivity.this).create();

                                            alertDialog.setTitle(" ");

                                            alertDialog.setMessage("Cargo " + ID + " possui funcionario vinculado !");

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
