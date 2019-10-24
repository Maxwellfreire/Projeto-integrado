package br.com.integrador.adm.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.integrador.adm.R;
import br.com.integrador.adm.adapters.APIAdapterTime;
import br.com.integrador.adm.boostrap.APIClient;
import br.com.integrador.adm.model.Time;
import br.com.integrador.adm.resource.TimeResource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TimeActivity extends AppCompatActivity {
    public ListView minhaListaTime;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        statusdeconectividade();


        pDialog = new ProgressDialog(TimeActivity.this);


        Atualizar();
    }

    protected void onResume() {
        super.onResume();
        Atualizar();
    }

    private void Atualizar() {
        pDialog.setMessage("Carregando...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        Retrofit retrofit = APIClient.getClient();

        TimeResource timeResource = retrofit.create(TimeResource.class);

        Call<List<Time>> get = timeResource.get();

        get.enqueue(new Callback<List<Time>>() {
            @Override
            public void onResponse(Call<List<Time>> call, Response<List<Time>> response) {
                //Dismiss Dialog
                pDialog.dismiss();
                //Se deu certo executa este método
                minhaListaTime = findViewById(R.id.minhaListaTime);
                List<Time> times = response.body();
                APIAdapterTime apiAdapterTime = new APIAdapterTime(getApplicationContext(), times);
                minhaListaTime.setAdapter(apiAdapterTime);
                //marcas.forEach(p-> Log.i  ("senai",p.toString()));
            }

            @Override
            public void onFailure(Call<List<Time>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }


        });


    }


    private void statusdeconectividade() {

        boolean wifiConnected;
        boolean mobileConnected;
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) { //connected with either mobile or wifi
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if (wifiConnected) { //wifi connected

            } else if (mobileConnected) { //Conectado com a conexão de dados móveis

            }
        } else {

            mensagemalerta();

        }
    }

    private void mensagemalerta() {
        // Creating alert Dialog with one Button
        AlertDialog alertDialog = new AlertDialog.Builder(
                TimeActivity.this).create();
        // Setting Dialog Title
        alertDialog.setTitle("Parece que não há internet!");
        // Setting Dialog Message
        alertDialog.setMessage("Verifique a sua conexão para continuar navegando.");
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.tick);
        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog
                // closed
                //Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }

    public void btnCadastraTime(View view) {
        startActivity(new Intent(this, CadastroTActivity.class));
    }

    public void carregarTime(View view) {

        statusdeconectividade();
        Atualizar();

    }
}
