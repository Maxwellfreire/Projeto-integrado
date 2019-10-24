package br.com.integrador.adm.resource;


import java.util.List;


import br.com.integrador.adm.model.Time;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TimeResource {


    @GET("/time")
    Call<List<Time>> get();


    @GET("/time/{id}")
    Call<List<Time>> get(Integer id);

    @POST("/time")
    Call<Time> post(@Body Time time);

    @PUT("/time/{id}")
    Call<Time> put(@Path("id") int id, @Body Time time);

    @DELETE("/time/{id}")
    Call<Void> delete(@Path("id") int id);
}