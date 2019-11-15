package br.com.integrador.adm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.integrador.adm.Activitys.CadastroMActivity;
import br.com.integrador.adm.boostrap.APIClient;
import br.com.integrador.adm.model.Cargo;
import br.com.integrador.adm.model.Login;
import br.com.integrador.adm.resource.LoginResource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    EditText etUsername, etSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }

    public void btnLogar(View view) {

        etUsername = (EditText) findViewById(R.id.etUsername);
        etSenha = (EditText) findViewById(R.id.etSenha);

        String Nome, Senha;

        Nome = etUsername.getText().toString();

        Senha = etSenha.getText().toString();

        if (Nome.isEmpty()) {

            etUsername.setError("Email está vazio !");

        } else if (Senha.isEmpty()) {

            etSenha.setError("Senha está vazia !");

        } else {


            final Login login = new Login(Nome, Senha);
            Retrofit retrofit = APIClient.getClient();
            LoginResource loginResource = retrofit.create(LoginResource.class);
            Call<Login> call = loginResource.post(login);

            call.enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {


                    if (response.code() == 401) {

                        Toast.makeText(getApplicationContext(), "Senha está incorreta, Tente novamente !", Toast.LENGTH_LONG).show();

                    } else if (response.code() == 400) {

                        Toast.makeText(getApplicationContext(), "Email está incorreto, Tente novamente !", Toast.LENGTH_LONG).show();

                    } else {


                        startActivity(new Intent(getApplicationContext(), InicioActivity.class));

                        Toast.makeText(getApplicationContext(), login.getEmail() + " está logado !", Toast.LENGTH_LONG).show();

                    }


                }

                @Override
                public void onFailure(Call<Login> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }


    }
}
