package br.com.integrador.adm.Activitys;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.integrador.adm.R;
import br.com.integrador.adm.boostrap.APIClient;
import br.com.integrador.adm.model.Liga;
import br.com.integrador.adm.model.Time;
import br.com.integrador.adm.resource.LigaResource;
import br.com.integrador.adm.resource.TimeResource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CadastroTActivity extends AppCompatActivity {
    private Time ItemTime;
    private TextView setIDTime;
    private EditText setNomeTime, setPaisesTime;
    Button btnSalvarTime, btnExcluirTime;
    TextView txtTime;

    Spinner setEstadoTime, setRegiaoTime, setTipoTimeTime, setLigasTime, regiaoS, estadoS, tipotimeS, ligasdotime;

    String[] SpinnerEstado = {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT",
            "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};
    String[] SpinnerRegiao = {"Norte", "Nordeste", "Centro-Oeste", "Sudeste", "Sul"};

    String[] SpinnerTipodotime = {"Nacional", "Seleção", "Internacional"};

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_t);

        setEstadoTime = (Spinner) findViewById(R.id.setEstadoTime);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SpinnerEstado);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setEstadoTime.setAdapter(arrayAdapter);


        setRegiaoTime = (Spinner) findViewById(R.id.setRegiaoTime);

        ArrayAdapter<String> arrayAdap = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SpinnerRegiao);
        arrayAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setRegiaoTime.setAdapter(arrayAdap);


        setTipoTimeTime = (Spinner) findViewById(R.id.setTipoTimeTime);

        ArrayAdapter<String> array = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SpinnerTipodotime);
        array.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setTipoTimeTime.setAdapter(array);

        setLigasTime = (Spinner) findViewById(R.id.setLigasTime);


        ItemTime = (Time) getIntent().getSerializableExtra("objetoDataTime");

        setIDTime = (TextView) findViewById(R.id.setIDTime);
        setNomeTime = (EditText) findViewById(R.id.setNomeTime);
        setPaisesTime = (EditText) findViewById(R.id.setPaisesTime);

        btnSalvarTime = (Button) findViewById(R.id.btnSalvarTime);
        btnExcluirTime = (Button) findViewById(R.id.btnExcluirTime);
        txtTime = (TextView) findViewById(R.id.txtTime);

        if (ItemTime != null) {

            txtTime.setText("Atualizar Time");
            setIDTime.setText(Integer.toString(ItemTime.getIdTime()));
            setNomeTime.setText(ItemTime.getNameTime());

            String[] regiao = {ItemTime.getRegiao(), "Norte", "Nordeste", "Centro-Oeste", "Sudeste", "Sul"};
            regiaoS = (Spinner) findViewById(R.id.setRegiaoTime);
            ArrayAdapter<String> regiaoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, regiao);
            regiaoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            regiaoS.setAdapter(regiaoAdapter);

            String[] estado = {ItemTime.getEstado(), "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};
            estadoS = (Spinner) findViewById(R.id.setEstadoTime);
            ArrayAdapter<String> estadoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, estado);
            estadoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            estadoS.setAdapter(estadoAdapter);

            setPaisesTime.setText(ItemTime.getPais());

            String[] tipodotime = {ItemTime.getTipoTime(), "Nacional", "Seleção", "Internacional"};
            tipotimeS = (Spinner) findViewById(R.id.setTipoTimeTime);
            ArrayAdapter<String> tipotimeoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipodotime);
            tipotimeoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            tipotimeS.setAdapter(tipotimeoAdapter);


            AtualizarLigas();


            btnSalvarTime.setText("Alterar");


        } else {
            AtualizarLigas();
            btnExcluirTime.setVisibility(View.INVISIBLE);
            btnSalvarTime.setText("Salvar");

        }

        btnSalvarTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if (btnSalvarTime.getText().toString().equals("Salvar")) {

                    setNomeTime = (EditText) findViewById(R.id.setNomeTime);
                    setPaisesTime = (EditText) findViewById(R.id.setPaisesTime);


                    String NomeTime, PaisTime;


                    NomeTime = setNomeTime.getText().toString();

                    String Regiao = String.valueOf(setRegiaoTime.getSelectedItem());

                    String Estado = String.valueOf(setEstadoTime.getSelectedItem());

                    PaisTime = setPaisesTime.getText().toString();

                    String tipotime = String.valueOf(setTipoTimeTime.getSelectedItem());

                    Liga ligaSelecionada = (Liga) setLigasTime.getSelectedItem();
                    int idSelecionado = ligaSelecionada.getLigaId();

                    Time time = new Time(NomeTime, Regiao, Estado, PaisTime, tipotime, idSelecionado);
                    Retrofit retrofit = APIClient.getClient();
                    TimeResource timeResource = retrofit.create(TimeResource.class);
                    Call<Time> call = timeResource.post(time);

                    call.enqueue(new Callback<Time>() {
                        @Override
                        public void onResponse(Call<Time> call, Response<Time> response) {

                            Toast.makeText(getApplicationContext(), "Time cadastrada com sucesso !", Toast.LENGTH_LONG).show();


                        }

                        @Override
                        public void onFailure(Call<Time> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                    finish();


                } else {


                    String NomeTime, PaisTime;
                    Integer ID = ItemTime.getIdTime();

                    NomeTime = setNomeTime.getText().toString();

                    String Regiao = String.valueOf(setRegiaoTime.getSelectedItem());

                    String Estado = String.valueOf(setEstadoTime.getSelectedItem());

                    PaisTime = setPaisesTime.getText().toString();

                    String tipotime = String.valueOf(setTipoTimeTime.getSelectedItem());

                    Liga ligaSelecionada = (Liga) setLigasTime.getSelectedItem();
                    int idSelecionado = ligaSelecionada.getLigaId();

                    Time time = new Time(NomeTime, Regiao, Estado, PaisTime, tipotime, idSelecionado);
                    Retrofit retrofit = APIClient.getClient();
                    TimeResource timeResource = retrofit.create(TimeResource.class);
                    Call<Time> call = timeResource.put(ID, time);

                    call.enqueue(new Callback<Time>() {
                        @Override
                        public void onResponse(Call<Time> call, Response<Time> response) {

//                            Toast.makeText(getApplicationContext(), "Marca atualizada com sucesso !", Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onFailure(Call<Time> call, Throwable t) {

                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                    Toast.makeText(getApplicationContext(), "Time atualizada com sucesso !", Toast.LENGTH_LONG).show();


                    finish();


                }


            }
        });

        btnExcluirTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Integer ID = ItemTime.getIdTime();
                Retrofit retrofit = APIClient.getClient();
                TimeResource timeResource = retrofit.create(TimeResource.class);
                Call<Void> call = timeResource.delete(ID);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        Toast.makeText(getApplicationContext(), "Time " + ID + " excluida com sucesso !", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });

                finish();


            }
        });


    }

    private void AtualizarLigas() {

        Retrofit retrofit = APIClient.getClient();

        LigaResource ligaResource = retrofit.create(LigaResource.class);

        Call<List<Liga>> get = ligaResource.get();

        get.enqueue(new Callback<List<Liga>>() {
            @Override
            public void onResponse(Call<List<Liga>> call, Response<List<Liga>> response) {

                Spinner spinnerLigas = (Spinner) findViewById(R.id.setLigasTime);
                List<Liga> ligas = response.body();

                ArrayAdapter<Liga> adapterSpinner = new ArrayAdapter<Liga>(CadastroTActivity.this, android.R.layout.simple_list_item_1, ligas);
                spinnerLigas.setAdapter(adapterSpinner);


            }

            @Override
            public void onFailure(Call<List<Liga>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }


        });


    }


}
