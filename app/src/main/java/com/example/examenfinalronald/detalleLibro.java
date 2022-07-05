package com.example.examenfinalronald;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class detalleLibro extends AppCompatActivity {

    TextInputLayout publication_title;
    TextInputLayout publication_resumen;

    Button editar;
    Button agregarFavorito;
    Button quitarFavorito;
    Button comprar;
    ImageView imagen;
    String id;
    ModelLibro modelLibro;
    ModelLibro modelLibroResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_libro);


        publication_title = findViewById(R.id.publication_title);
        publication_resumen = findViewById(R.id.publication_resumen);

        imagen = findViewById(R.id.imagen);
        editar = findViewById(R.id.editar);
        agregarFavorito = findViewById(R.id.agregarFavorito);
        quitarFavorito = findViewById(R.id.quitarFavorito);
        id = getIntent().getStringExtra("id");


        Retrofit imagenRe = new Retrofit.Builder()
                .baseUrl("https://6298a8b7f2decf5bb74859ed.mockapi.io/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Servicio libro = imagenRe.create(Servicio.class);

        Call<ModelLibro> li = libro.obtenerLibro(id);
        li.enqueue(new Callback<ModelLibro>() {
            @Override
            public void onResponse(@NonNull Call<ModelLibro> call, Response<ModelLibro> response) {
                if (response.code() == 200) {
                    modelLibro = response.body();
                    Objects.requireNonNull(publication_title.getEditText()).setText(modelLibro.getTitulo());
                    Objects.requireNonNull(publication_resumen.getEditText()).setText(modelLibro.getResumen());

                    Picasso.get()
                            .load(modelLibro.getImagen())
                            .into(imagen);

                    if (modelLibro.isFavorito()) {
                        agregarFavorito.setVisibility(View.GONE);
                        quitarFavorito.setVisibility(View.VISIBLE);
                    } else {
                        agregarFavorito.setVisibility(View.VISIBLE);
                        quitarFavorito.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onFailure(Call<ModelLibro> call, Throwable t) {

            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent(detalleLibro.this, EditarLibro.class);
                data.putExtra("id", modelLibro.getId());
                startActivity(data);
            }
        });

        agregarFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit imagenRe = new Retrofit.Builder()
                        .baseUrl("https://6298a8b7f2decf5bb74859ed.mockapi.io/api/v1/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Servicio libro = imagenRe.create(Servicio.class);

                Call<ModelLibro> getUser = libro.crearFavortito(modelLibro);
                getUser.enqueue(new Callback<ModelLibro>() {
                    @Override
                    public void onResponse(@NonNull Call<ModelLibro> call, @NonNull Response<ModelLibro> response) {
                        Log.e("resp", response.toString());
                        if (response.code() == 201) {
                            modelLibroResponse = response.body();
                            assert modelLibroResponse != null;
                            actualizarLibro(modelLibroResponse.id, id, true);

                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ModelLibro> call, @NonNull Throwable t) {

                    }
                });
            }


        });

        quitarFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Retrofit imagenRe = new Retrofit.Builder()
                        .baseUrl("https://6298a8b7f2decf5bb74859ed.mockapi.io/api/v1/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Servicio libro = imagenRe.create(Servicio.class);

                Call<ResponseBody> getUser = libro.eliminarFavorito(modelLibro.getLibroFavorito());
                getUser.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.code() == 200) {
                            actualizarLibro("", modelLibro.id, false);
                        } else {
                            Toast.makeText(detalleLibro.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        Toast.makeText(detalleLibro.this, "Error", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }
                });
            }
        });
    }

    private void actualizarLibro(String id, String id1, boolean b) {

        Retrofit imagenRe = new Retrofit.Builder()
                .baseUrl("https://6298a8b7f2decf5bb74859ed.mockapi.io/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Servicio libro = imagenRe.create(Servicio.class);

        ModelLibro libroA = new ModelLibro();
        libroA.setFavorito(b);
        libroA.setLibroFavorito(id);

        Call<ResponseBody> update = libro.actualizarLibro(libroA, id1);

        update.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Toast.makeText(detalleLibro.this, "actualizado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(detalleLibro.this, "Error al actualizar libro", Toast.LENGTH_SHORT).show();
                }
                onBackPressed();
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
            }
        });
    }
}