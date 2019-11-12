package br.com.integrador.adm.resource;


import java.util.List;

import br.com.integrador.adm.model.Funcionario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FuncionarioResource {


    @GET("/funcionario")
    Call<List<Funcionario>> get();


    @GET("/funcionario/{id}")
    Call<List<Funcionario>> get(Integer id);

    @POST("/funcionario")
    Call<Funcionario> post(@Body Funcionario funcionario);

    @PUT("/funcionario/{id}")
    Call<Funcionario> put(@Path("id") int id, @Body Funcionario funcionario);

    @DELETE("/funcionario/{id}")
    Call<Void> delete(@Path("id") int id);
}