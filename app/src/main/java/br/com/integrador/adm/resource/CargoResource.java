package br.com.integrador.adm.resource;


import java.util.List;

import br.com.integrador.adm.model.Cargo;
import br.com.integrador.adm.model.Marca;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CargoResource {


    @GET("/cargos")
    Call<List<Cargo>> get();


    @GET("/cargos/{id}")
    Call<List<Cargo>> get(Integer id);

    @POST("/cargos")
    Call<Cargo> post(@Body Cargo cargo);

    @PUT("/cargos/{id}")
    Call<Cargo> put(@Path("id") int id, @Body Cargo cargo);

    @DELETE("/cargos/{id}")
    Call<Void> delete(@Path("id") int id);
}