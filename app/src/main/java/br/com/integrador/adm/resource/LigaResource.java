package br.com.integrador.adm.resource;


import java.util.List;

import br.com.integrador.adm.model.Liga;
import br.com.integrador.adm.model.Marca;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LigaResource {


    @GET("/ligas")
    Call<List<Liga>> get();


    @GET("/ligas/{id}")
    Call<List<Liga>> get(Integer id);

    @POST("/ligas")
    Call<Liga> post(@Body Liga liga);

    @PUT("/ligas/{id}")
    Call<Liga> put(@Path("id") int id, @Body Liga liga);

    @DELETE("/ligas/{id}")
    Call<Void> delete(@Path("id") int id);
}