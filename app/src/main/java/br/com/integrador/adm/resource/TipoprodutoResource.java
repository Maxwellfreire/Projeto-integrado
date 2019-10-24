package br.com.integrador.adm.resource;


import java.util.List;


import br.com.integrador.adm.model.Tipoproduto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TipoprodutoResource {


    @GET("/tiposprodutos")
    Call<List<Tipoproduto>> get();


    @GET("/tiposprodutos/{id}")
    Call<List<Tipoproduto>> get(Integer id);

    @POST("/tiposprodutos")
    Call<Tipoproduto> post(@Body Tipoproduto tipoproduto);

    @PUT("/tiposprodutos/{id}")
    Call<Tipoproduto> put(@Path("id") int id, @Body Tipoproduto tipoproduto);

    @DELETE("/tiposprodutos/{id}")
    Call<Void> delete(@Path("id") int id);
}