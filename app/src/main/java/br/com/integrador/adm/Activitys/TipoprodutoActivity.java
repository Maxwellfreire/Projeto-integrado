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
import br.com.integrador.adm.adapters.APIAdapterTipoproduto;
import br.com.integrador.adm.boostrap.APIClient;
import br.com.integrador.adm.model.Tipoproduto;
import br.com.integrador.adm.resource.TipoprodutoResource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TipoprodutoActivity extends AppCompatActivity {
    public ListView minhaListaTipoproduto;
    private ProgressDialog pDialog;
    PullRefreshLayout refreshTipoproduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipoproduto);

        statusdeconectividade();

        pDialog = new ProgressDialog(TipoprodutoActivity.this);


        Atualizar();

        refreshTipoproduto = (PullRefreshLayout) findViewById(R.id.refreshTipoproduto);

        refreshTipoproduto.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                statusdeconectividade();
                Atualizar();

            }
        });

        refreshTipoproduto.setRefreshing(false);
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

        TipoprodutoResource tipoprodutoResource = retrofit.create(TipoprodutoResource.class);

        Call<List<Tipoproduto>> get = tipoprodutoResource.get();

        get.enqueue(new Callback<List<Tipoproduto>>() {
            @Override
            public void onResponse(Call<List<Tipoproduto>> call, Response<List<Tipoproduto>> response) {
                //Dismiss Dialog
                pDialog.dismiss();
                //Se deu certo executa este método
                minhaListaTipoproduto = findViewById(R.id.minhaListaTipoproduto);
                List<Tipoproduto> tipoprodutos = response.body();
                APIAdapterTipoproduto apiAdapterTipoproduto = new APIAdapterTipoproduto(getApplicationContext(), tipoprodutos);
                minhaListaTipoproduto.setAdapter(apiAdapterTipoproduto);

                apiAdapterTipoproduto.notifyDataSetChanged();
                refreshTipoproduto.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Tipoproduto>> call, Throwable t) {
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
                TipoprodutoActivity.this).create();
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

    public void btnCadastraTipoproduto(View view) {

        startActivity(new Intent(this, CadastroTPActivity.class));
    }


}
