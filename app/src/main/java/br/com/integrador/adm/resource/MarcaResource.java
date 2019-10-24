package br.com.integrador.adm.resource;


import java.util.List;

import br.com.integrador.adm.model.Marca;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MarcaResource {


    @GET("/marcas")
    Call<List<Marca>> get();


    @GET("/marcas/{id}")
    Call<List<Marca>> get(Integer id);

    @POST("/marcas")
    Call<Marca> post(@Body Marca marca);

    @PUT("/marcas/{id}")
    Call<Marca> put(@Path("id") int id, @Body Marca marca);

    @DELETE("/marcas/{id}")
    Call<Void> delete(@Path("id") int id);

}