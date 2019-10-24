package br.com.integrador.adm.resource;


import java.util.List;


import br.com.integrador.adm.model.Cupomdesconto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CupomdescontoResource {


    @GET("/cupomdesconto")
    Call<List<Cupomdesconto>> get();


    @GET("/cupomdesconto/{id}")
    Call<List<Cupomdesconto>> get(Integer id);

    @POST("/cupomdesconto")
    Call<Cupomdesconto> post(@Body Cupomdesconto cupomdesconto);

    @PUT("/cupomdesconto/{id}")
    Call<Cupomdesconto> put(@Path("id") int id, @Body Cupomdesconto cupomdesconto);

    @DELETE("/cupomdesconto/{id}")
    Call<Void> delete(@Path("id") int id);
}