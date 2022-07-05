package com.example.examenfinalronald;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.Objects;

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
    ImageView imagen;
    String id;
    ModelLibro modelLibro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_libro);


        publication_title = findViewById(R.id.publication_title);
        publication_resumen = findViewById(R.id.publication_resumen);

        imagen = findViewById(R.id.imagen);
        editar = findViewById(R.id.editar);
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
    }
}