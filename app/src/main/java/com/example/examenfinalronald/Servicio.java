package com.example.examenfinalronald;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Servicio {

    @POST("image")
    Call<imageResponseBody> subirImagen(@Header("Authorization") String uid, @Body modelImagen image);

    @POST("/Libros")
    Call<Void> Createlibro(@Body ModelLibro libro);

    @GET("/Libros")
    Call<List<ModelLibro>> obtenerListaLibros();

    @GET("/Libros/{id}")
    Call<ModelLibro> obtenerLibro(@Path("id") String id);

    @PUT("/Libros/{id}")
    Call<ResponseBody> actualizarLibro(@Body ModelLibro libro, @Path("id") String id);

    @POST("/favoritos")
    Call<ModelLibro> crearFavortito(@Body ModelLibro libro);

    @DELETE("/favoritos/{id}")
    Call<ResponseBody> eliminarFavorito(@Path("id") String id);

    @GET("/favoritos")
    Call<List<ModelLibro>> obtenerListaFavoritos();
}
