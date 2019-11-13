package br.com.integrador.adm.Activitys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.List;

import br.com.integrador.adm.R;
import br.com.integrador.adm.boostrap.APIClient;
import br.com.integrador.adm.model.Cargo;
import br.com.integrador.adm.model.Funcionario;
import br.com.integrador.adm.model.Liga;
import br.com.integrador.adm.model.Time;
import br.com.integrador.adm.resource.CargoResource;
import br.com.integrador.adm.resource.FuncionarioResource;
import br.com.integrador.adm.resource.LigaResource;
import br.com.integrador.adm.resource.TimeResource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CadastroFActivity extends AppCompatActivity {
    private Funcionario ItemFuncionario;
    private TextView setIDFuncionario;
    private EditText setNomeFuncionario, setCPFFuncionario, setEmailFuncionario, setTelefoneFuncionario, setConfirmaSFuncionario, setSenhaFuncionario, setCelularFuncionario, setNascFuncionario;
    Button btnSalvarFuncionario, btnExcluirFuncionario;
    TextView txtFuncionario;

    Spinner setSexoFuncionario, setCargoFuncionario, SexoS;

    String[] SpinnerSexo = {"M", "F"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_f);

        TextView cpf = (TextView) findViewById(R.id.setCPFFuncionario);
        //Criar mascara pra colocar numero telefonico
        SimpleMaskFormatter smf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtw = new MaskTextWatcher(cpf, smf);
        cpf.addTextChangedListener(mtw);
        //Fim da mascara


        TextView telefone = (TextView) findViewById(R.id.setTelefoneFuncionario);
        //Criar mascara pra colocar numero telefonico
        SimpleMaskFormatter smf2 = new SimpleMaskFormatter("(NN)NNNN-NNNN");
        MaskTextWatcher mtw2 = new MaskTextWatcher(telefone, smf2);
        telefone.addTextChangedListener(mtw2);
        //Fim da mascara

        TextView celular = (TextView) findViewById(R.id.setCelularFuncionario);
        //Criar mascara pra colocar numero telefonico
        SimpleMaskFormatter formato = new SimpleMaskFormatter("(NN)N-NNNN-NNNN");
        MaskTextWatcher watchee = new MaskTextWatcher(celular, formato);
        celular.addTextChangedListener(watchee);
        //Fim da mascara

        TextView nascimento = (TextView) findViewById(R.id.setNascFuncionario);
        //Criar mascara pra colocar numero telefonico
        SimpleMaskFormatter formato2 = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mascara = new MaskTextWatcher(nascimento, formato2);
        nascimento.addTextChangedListener(mascara);
        //Fim da mascara

        setSexoFuncionario = (Spinner) findViewById(R.id.setSexoFuncionario);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SpinnerSexo);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setSexoFuncionario.setAdapter(arrayAdapter);

        setCargoFuncionario = (Spinner) findViewById(R.id.setCargoFuncionario);

        ItemFuncionario = (Funcionario) getIntent().getSerializableExtra("objetoDataFuncionario");

        setIDFuncionario = (TextView) findViewById(R.id.setIDFuncionario);
        setNomeFuncionario = (EditText) findViewById(R.id.setNomeFuncionario);
        setCPFFuncionario = (EditText) findViewById(R.id.setCPFFuncionario);
        setEmailFuncionario = (EditText) findViewById(R.id.setEmailFuncionario);
        setTelefoneFuncionario = (EditText) findViewById(R.id.setTelefoneFuncionario);
        setCelularFuncionario = (EditText) findViewById(R.id.setCelularFuncionario);
        setNascFuncionario = (EditText) findViewById(R.id.setNascFuncionario);

        btnSalvarFuncionario = (Button) findViewById(R.id.btnSalvarFuncionario);
        btnExcluirFuncionario = (Button) findViewById(R.id.btnExcluirFuncionario);
        txtFuncionario = (TextView) findViewById(R.id.txtFuncionario);


        if (ItemFuncionario != null) {

            txtFuncionario.setText("Atualizar Funcionario");
            setIDFuncionario.setText(Integer.toString(ItemFuncionario.getMatricula()));
            setNomeFuncionario.setText(ItemFuncionario.getNameFuncionario());
            setCPFFuncionario.setText(ItemFuncionario.getCpf());

            String[] Sexo = {ItemFuncionario.getSexo(), "M", "F"};
            SexoS = (Spinner) findViewById(R.id.setSexoFuncionario);
            ArrayAdapter<String> sexoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Sexo);
            sexoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            SexoS.setAdapter(sexoAdapter);

            setEmailFuncionario.setText(ItemFuncionario.getEmail());
            setTelefoneFuncionario.setText(ItemFuncionario.getTelefone());
            setCelularFuncionario.setText(ItemFuncionario.getCelular());
            setNascFuncionario.setText(ItemFuncionario.getDataNascimento());

            AtualizarCargos();

            btnSalvarFuncionario.setText("Alterar");


        } else {
            AtualizarCargos();
            btnExcluirFuncionario.setVisibility(View.INVISIBLE);
            btnSalvarFuncionario.setText("Salvar");

        }

        btnSalvarFuncionario.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (btnSalvarFuncionario.getText().toString().equals("Salvar")) {


                    setNomeFuncionario = (EditText) findViewById(R.id.setNomeFuncionario);
                    setCPFFuncionario = (EditText) findViewById(R.id.setCPFFuncionario);
                    setEmailFuncionario = (EditText) findViewById(R.id.setEmailFuncionario);
                    setTelefoneFuncionario = (EditText) findViewById(R.id.setTelefoneFuncionario);
                    setCelularFuncionario = (EditText) findViewById(R.id.setCelularFuncionario);
                    setSenhaFuncionario = (EditText) findViewById(R.id.setSenhaFuncionario);
                    setConfirmaSFuncionario = (EditText) findViewById(R.id.setConfirmaSFuncionario);
                    setNascFuncionario = (EditText) findViewById(R.id.setNascFuncionario);


                    String NomeFuncionario, cpffuncionario, emailfuncionario, telefuncionario, celularfuncionario, nascfuncionario, senha, confirmaSenha;


                    NomeFuncionario = setNomeFuncionario.getText().toString();

                    cpffuncionario = setCPFFuncionario.getText().toString();

                    emailfuncionario = setEmailFuncionario.getText().toString();

                    telefuncionario = setTelefoneFuncionario.getText().toString();

                    celularfuncionario = setCelularFuncionario.getText().toString();

                    senha = setSenhaFuncionario.getText().toString();

                    confirmaSenha = setConfirmaSFuncionario.getText().toString();

                    nascfuncionario = setNascFuncionario.getText().toString();

                    if (NomeFuncionario.isEmpty()) {


                        setNomeFuncionario.setError("Nome está vazio!");


                    } else if (cpffuncionario.isEmpty()) {


                        setCPFFuncionario.setError("CPF está vazio!");


                    } else if (emailfuncionario.isEmpty()) {

                        setEmailFuncionario.setError("Email está vazio!");


                    } else if (telefuncionario.isEmpty()) {

                        setTelefoneFuncionario.setError("Telefone está vazio!");

                    } else if (celularfuncionario.isEmpty()) {

                        setCelularFuncionario.setError("Celular está vazio!");

                    } else if (nascfuncionario.isEmpty()) {

                        setNascFuncionario.setError("Data de nascimento está vazio!");


                    } else {


                        String SexoFuncionario = String.valueOf(setSexoFuncionario.getSelectedItem());

                        Cargo cargoSelecionada = (Cargo) setCargoFuncionario.getSelectedItem();
                        int idSelecionado = cargoSelecionada.getId();

                        Funcionario funcionario = new Funcionario(NomeFuncionario, cpffuncionario, SexoFuncionario, emailfuncionario,
                                telefuncionario, celularfuncionario, senha, confirmaSenha, nascfuncionario, idSelecionado);
                        Retrofit retrofit = APIClient.getClient();
                        FuncionarioResource funcionarioResource = retrofit.create(FuncionarioResource.class);
                        Call<Funcionario> call = funcionarioResource.post(funcionario);

                        call.enqueue(new Callback<Funcionario>() {
                            @Override
                            public void onResponse(Call<Funcionario> call, Response<Funcionario> response) {

                                Toast.makeText(getApplicationContext(), "Funcionario cadastrado com sucesso !", Toast.LENGTH_LONG).show();


                            }

                            @Override
                            public void onFailure(Call<Funcionario> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

                        finish();


                    }


                } else {


                    String NomeFuncionario, cpffuncionario, emailfuncionario, telefuncionario, celularfuncionario, nascfuncionario, senha, confirmaSenha;
                    Integer ID = ItemFuncionario.getMatricula();
                    setNomeFuncionario = (EditText) findViewById(R.id.setNomeFuncionario);
                    setCPFFuncionario = (EditText) findViewById(R.id.setCPFFuncionario);
                    setEmailFuncionario = (EditText) findViewById(R.id.setEmailFuncionario);
                    setTelefoneFuncionario = (EditText) findViewById(R.id.setTelefoneFuncionario);
                    setCelularFuncionario = (EditText) findViewById(R.id.setCelularFuncionario);
                    setSenhaFuncionario = (EditText) findViewById(R.id.setSenhaFuncionario);
                    setConfirmaSFuncionario = (EditText) findViewById(R.id.setConfirmaSFuncionario);
                    setNascFuncionario = (EditText) findViewById(R.id.setNascFuncionario);


                    NomeFuncionario = setNomeFuncionario.getText().toString();

                    cpffuncionario = setCPFFuncionario.getText().toString();

                    emailfuncionario = setEmailFuncionario.getText().toString();

                    telefuncionario = setTelefoneFuncionario.getText().toString();

                    celularfuncionario = setCelularFuncionario.getText().toString();

                    senha = setSenhaFuncionario.getText().toString();

                    confirmaSenha = setConfirmaSFuncionario.getText().toString();

                    nascfuncionario = setNascFuncionario.getText().toString();

                    if (NomeFuncionario.isEmpty()) {


                        setNomeFuncionario.setError("Nome está vazio!");


                    } else if (cpffuncionario.isEmpty()) {


                        setCPFFuncionario.setError("CPF está vazio!");


                    } else if (emailfuncionario.isEmpty()) {

                        setEmailFuncionario.setError("Email está vazio!");


                    } else if (telefuncionario.isEmpty()) {

                        setTelefoneFuncionario.setError("Telefone está vazio!");

                    } else if (celularfuncionario.isEmpty()) {

                        setCelularFuncionario.setError("Celular está vazio!");

                    } else if (nascfuncionario.isEmpty()) {

                        setNascFuncionario.setError("Data de nascimento está vazio!");

                    } else {

                        String SexoFuncionario = String.valueOf(setSexoFuncionario.getSelectedItem());

                        Cargo cargoSelecionada = (Cargo) setCargoFuncionario.getSelectedItem();
                        int idSelecionado = cargoSelecionada.getId();

                        Funcionario funcionario = new Funcionario(NomeFuncionario, cpffuncionario, SexoFuncionario, emailfuncionario,
                                telefuncionario, celularfuncionario, senha, confirmaSenha, nascfuncionario, idSelecionado);
                        Retrofit retrofit = APIClient.getClient();
                        FuncionarioResource funcionarioResource = retrofit.create(FuncionarioResource.class);
                        Call<Funcionario> call = funcionarioResource.put(ID, funcionario);

                        call.enqueue(new Callback<Funcionario>() {
                            @Override
                            public void onResponse(Call<Funcionario> call, Response<Funcionario> response) {


                            }

                            @Override
                            public void onFailure(Call<Funcionario> call, Throwable t) {

                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

                        Toast.makeText(getApplicationContext(), "Funcionario atualizado com sucesso !", Toast.LENGTH_LONG).show();


                        finish();


                    }


                }


            }
        });

        btnExcluirFuncionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Creating alert Dialog with two Buttons
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CadastroFActivity.this);
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

                                final Integer ID = ItemFuncionario.getMatricula();
                                Retrofit retrofit = APIClient.getClient();
                                FuncionarioResource funcionarioResource = retrofit.create(FuncionarioResource.class);
                                Call<Void> call = funcionarioResource.delete(ID);

                                call.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {


                                        Toast.makeText(getApplicationContext(), "Funcionario " + ID + " excluido com sucesso !", Toast.LENGTH_LONG).show();


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

    private void AtualizarCargos() {

        Retrofit retrofit = APIClient.getClient();

        CargoResource cargoResource = retrofit.create(CargoResource.class);

        Call<List<Cargo>> get = cargoResource.get();

        get.enqueue(new Callback<List<Cargo>>() {
            @Override
            public void onResponse(Call<List<Cargo>> call, Response<List<Cargo>> response) {

                Spinner spinnerCargos = (Spinner) findViewById(R.id.setCargoFuncionario);
                List<Cargo> cargos = response.body();

                ArrayAdapter<Cargo> adapterSpinner = new ArrayAdapter<Cargo>(CadastroFActivity.this, android.R.layout.simple_list_item_1, cargos);
                spinnerCargos.setAdapter(adapterSpinner);


            }

            @Override
            public void onFailure(Call<List<Cargo>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }


        });


    }


}



