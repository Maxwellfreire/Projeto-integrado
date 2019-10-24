package br.com.integrador.adm.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import br.com.integrador.adm.R;
import br.com.integrador.adm.boostrap.APIClient;
import br.com.integrador.adm.model.Liga;
import br.com.integrador.adm.resource.LigaResource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CadastroLActivity extends AppCompatActivity {

    private Liga ItemLiga;
    private TextView setIDLiga;
    private EditText setNomeLiga;
    Button btnSalvarLiga, btnExcluirLiga;
    TextView txtLiga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_l);

        ItemLiga = (Liga) getIntent().getSerializableExtra("objetoDataLiga");

        setIDLiga = (TextView) findViewById(R.id.setIDLiga);
        setNomeLiga = (EditText) findViewById(R.id.setNomeLiga);


        btnSalvarLiga = (Button) findViewById(R.id.btnSalvarLiga);
        btnExcluirLiga = (Button) findViewById(R.id.btnExcluirLiga);
        txtLiga = (TextView) findViewById(R.id.txtLiga);

        if (ItemLiga != null) {
            txtLiga.setText("Atualizar Liga");
            btnSalvarLiga.setText("Alterar");
            setIDLiga.setText(Integer.toString(ItemLiga.getId()));
            setNomeLiga.setText(ItemLiga.getName());


        } else {
            btnExcluirLiga.setVisibility(View.INVISIBLE);
            btnSalvarLiga.setText("Salvar");

        }


        btnSalvarLiga.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (btnSalvarLiga.getText().toString().equals("Salvar")) {


                    setNomeLiga = (EditText) findViewById(R.id.setNomeLiga);
                    String Nome = setNomeLiga.getText().toString();

                    if (Nome.isEmpty()) {

//                        Toast.makeText(getApplicationContext(), "O campo 'Nome' esta vazio !", Toast.LENGTH_LONG).show();

                        Snackbar.make(v, "'Nome' esta vazio !", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();


                    } else {

                        String NomeLiga;
                        NomeLiga = setNomeLiga.getText().toString();
                        Liga liga = new Liga(NomeLiga);
                        Retrofit retrofit = APIClient.getClient();
                        LigaResource ligaResource = retrofit.create(LigaResource.class);
                        Call<Liga> call = ligaResource.post(liga);

                        call.enqueue(new Callback<Liga>() {
                            @Override
                            public void onResponse(Call<Liga> call, Response<Liga> response) {

                                Toast.makeText(getApplicationContext(), "Liga cadastrada com sucesso !", Toast.LENGTH_LONG).show();


                            }

                            @Override
                            public void onFailure(Call<Liga> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

                        finish();

                    }


                } else {

                    String NomeLiga;
                    Integer ID = ItemLiga.getId();
                    NomeLiga = setNomeLiga.getText().toString();
                    Liga liga = new Liga(NomeLiga);
                    Retrofit retrofit = APIClient.getClient();
                    LigaResource ligaResource = retrofit.create(LigaResource.class);
                    Call<Liga> call = ligaResource.put(ID, liga);

                    call.enqueue(new Callback<Liga>() {
                        @Override
                        public void onResponse(Call<Liga> call, Response<Liga> response) {

//                            Toast.makeText(getApplicationContext(), "Marca atualizada com sucesso !", Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onFailure(Call<Liga> call, Throwable t) {

                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                    Toast.makeText(getApplicationContext(), "Liga atualizada com sucesso !", Toast.LENGTH_LONG).show();


                    finish();

                }


            }
        });

        btnExcluirLiga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Integer ID = ItemLiga.getId();
                Retrofit retrofit = APIClient.getClient();
                LigaResource ligaResource = retrofit.create(LigaResource.class);
                Call<Void> call = ligaResource.delete(ID);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        Toast.makeText(getApplicationContext(), "Liga " + ID + " excluida com sucesso !", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });

                finish();


            }
        });
    }
}
