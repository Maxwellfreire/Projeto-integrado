package br.com.integrador.adm.resource;


import br.com.integrador.adm.model.Login;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface LoginResource {


    @POST("/signin")
    Call<Login> post(@Body Login login);


}