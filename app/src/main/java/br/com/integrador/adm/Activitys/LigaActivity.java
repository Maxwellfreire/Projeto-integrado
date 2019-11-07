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

import com.baoyz.widget.PullRefreshLayout;

import java.util.List;

import br.com.integrador.adm.R;
import br.com.integrador.adm.adapters.APIAdapterLiga;
import br.com.integrador.adm.boostrap.APIClient;
import br.com.integrador.adm.model.Liga;
import br.com.integrador.adm.resource.LigaResource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LigaActivity extends AppCompatActivity {
    public ListView minhaListaLiga;
    private ProgressDialog pDialog;
    PullRefreshLayout refreshLiga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liga);

        statusdeconectividade();


        pDialog = new ProgressDialog(LigaActivity.this);


        Atualizar();

        refreshLiga = (PullRefreshLayout) findViewById(R.id.refreshLiga);

        refreshLiga.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                statusdeconectividade();
                Atualizar();

            }
        });

        refreshLiga.setRefreshing(false);
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

        LigaResource ligaResource = retrofit.create(LigaResource.class);

        Call<List<Liga>> get = ligaResource.get();

        get.enqueue(new Callback<List<Liga>>() {
            @Override
            public void onResponse(Call<List<Liga>> call, Response<List<Liga>> response) {
                //Dismiss Dialog
                pDialog.dismiss();
                //Se deu certo executa este método
                minhaListaLiga = findViewById(R.id.minhaListaLiga);
                List<Liga> ligas = response.body();
                APIAdapterLiga apiAdapterLiga = new APIAdapterLiga(getApplicationContext(), ligas);
                minhaListaLiga.setAdapter(apiAdapterLiga);

                apiAdapterLiga.notifyDataSetChanged();
                refreshLiga.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Liga>> call, Throwable t) {
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
                LigaActivity.this).create();
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


    public void btnCadastraLiga(View view) {

        startActivity(new Intent(this, CadastroLActivity.class));
    }


}
