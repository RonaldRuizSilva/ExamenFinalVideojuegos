package com.example.examenfinalronald;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class EditarLibro extends AppCompatActivity {

    String id;
    TextInputLayout publication_title;
    TextInputLayout publication_resumen;
    TextInputLayout publication_latitud;
    TextInputLayout publication_longitud;
    ModelLibro modelLibro;
    Button actualizar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_libro);


        publication_title = findViewById(R.id.publication_title);
        publication_resumen = findViewById(R.id.publication_resumen);
        publication_latitud = findViewById(R.id.publication_latitud);
        publication_longitud = findViewById(R.id.publication_longitud);
        actualizar = findViewById(R.id.actualizar);

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
                    Objects.requireNonNull(publication_latitud.getEditText()).setText(modelLibro.getLatitud());
                    Objects.requireNonNull(publication_longitud.getEditText()).setText(modelLibro.getLongitud());
                }

            }

            @Override
            public void onFailure(Call<ModelLibro> call, Throwable t) {

            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ModelLibro libroA = new ModelLibro();
                libroA.setTitulo(Objects.requireNonNull(publication_title.getEditText()).getText().toString().trim());
                libroA.setResumen(Objects.requireNonNull(publication_resumen.getEditText()).getText().toString().trim());
                libroA.setLatitud(Objects.requireNonNull(publication_latitud.getEditText()).getText().toString().trim());
                libroA.setLatitud(Objects.requireNonNull(publication_longitud.getEditText()).getText().toString().trim());

                Call<ResponseBody> update = libro.actualizarLibro(libroA, id);

                update.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.code() == 200) {
                            Toast.makeText(EditarLibro.this, "actualizado", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(EditarLibro.this, "Error al actualizar libro", Toast.LENGTH_SHORT).show();
                        }
                        onBackPressed();
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    }
                });
            }
        });
    }
}