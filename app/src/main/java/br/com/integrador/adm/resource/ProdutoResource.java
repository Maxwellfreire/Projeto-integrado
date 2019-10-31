package br.com.integrador.adm.resource;


import java.util.List;

import br.com.integrador.adm.model.Produto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProdutoResource {


    @GET("/produto")
    Call<List<Produto>> get();


    @GET("/produto/{id}")
    Call<List<Produto>> get(Integer id);

    @POST("/produto")
    Call<Produto> post(@Body Produto produto);

    @PUT("/produto/{id}")
    Call<Produto> put(@Path("id") int id, @Body Produto produto);

    @DELETE("/produto/{id}")
    Call<Void> delete(@Path("id") int id);
}