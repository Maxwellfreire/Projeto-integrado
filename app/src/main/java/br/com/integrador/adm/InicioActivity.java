package br.com.integrador.adm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import br.com.integrador.adm.Activitys.CargoActivity;
import br.com.integrador.adm.Activitys.FuncionarioActivity;
import br.com.integrador.adm.Activitys.LigaActivity;
import br.com.integrador.adm.Activitys.MarcaActivity;
import br.com.integrador.adm.Activitys.ProdutoActivity;
import br.com.integrador.adm.Activitys.TimeActivity;
import br.com.integrador.adm.Activitys.TipoprodutoActivity;

public class InicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void BtnMarca(View view) {
        startActivity(new Intent(this, MarcaActivity.class));
    }

    public void btnCargo(View view) {
        startActivity(new Intent(this, CargoActivity.class));
    }

    public void btnLiga(View view) {
        startActivity(new Intent(this, LigaActivity.class));
    }

    public void btnTipoproduto(View view) {
        startActivity(new Intent(this, TipoprodutoActivity.class));
    }

    public void btnTime(View view) {

        startActivity(new Intent(this, TimeActivity.class));

    }


    public void btnProduto(View view) {
        startActivity(new Intent(this, ProdutoActivity.class));
    }

    public void btnFuncionario(View view) {
        startActivity(new Intent(this, FuncionarioActivity.class));
    }
}
