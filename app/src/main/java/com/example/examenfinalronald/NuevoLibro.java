package com.example.examenfinalronald;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NuevoLibro extends AppCompatActivity {

    TextInputLayout publication_title;
    TextInputLayout publication_resumen;
    TextInputLayout publication_latitud;
    TextInputLayout publication_longitud;
    Button registrar;
    ImageView imagen;
    String stringImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_libro);


        publication_title = findViewById(R.id.publication_title);
        publication_resumen = findViewById(R.id.publication_resumen);
        publication_latitud = findViewById(R.id.publication_latitud);
        publication_longitud = findViewById(R.id.publication_longitud);
        imagen = findViewById(R.id.imagen);
        registrar = findViewById(R.id.registrar);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registar();
            }
        });

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });
    }


    void registar() {

        Retrofit imagenRe = new Retrofit.Builder()
                .baseUrl("https://api.imgur.com/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Servicio imageService = imagenRe.create(Servicio.class);

        Call<imageResponseBody> subir = imageService.subirImagen("Client-ID 8bcc638875f89d9", new modelImagen(stringImagen));
        subir.enqueue(new Callback<imageResponseBody>() {
            @Override
            public void onResponse(Call<imageResponseBody> call, Response<imageResponseBody> response) {
                if (response.code() == 200) {
                    imageResponseBody body = response.body();

                    assert body != null;
                    Log.e("dd", response.toString());
                    data data = body.getData();
                    Log.e("dd", data.getLink());
                     subirLibro(data.getLink());
                }
            }

            @Override
            public void onFailure(Call<imageResponseBody> call, Throwable t) {

            }
        });
    }

    private void subirLibro(String link) {

        ModelLibro libro = new ModelLibro(
                link,
                Objects.requireNonNull(publication_title.getEditText()).getText().toString().trim(),
                Objects.requireNonNull(publication_resumen.getEditText()).getText().toString().trim(),
                Objects.requireNonNull(publication_latitud.getEditText()).getText().toString().trim(),
                Objects.requireNonNull(publication_longitud.getEditText()).getText().toString().trim(),
                false,
                ""
        );

        Retrofit imagenRe = new Retrofit.Builder()
                .baseUrl("https://6298a8b7f2decf5bb74859ed.mockapi.io/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Servicio imageService = imagenRe.create(Servicio.class);
        Call<Void> subir = imageService.Createlibro(libro);
        subir.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 201) {
                    onBackPressed();
                } else {

                    Toast.makeText(NuevoLibro.this, "Error: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }

    ActivityResultLauncher<Intent> sIntentActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    assert data != null;

                    Uri uri = data.getData();
                    imagen.setImageURI(uri);

                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                        byte[] image = outputStream.toByteArray();
                        stringImagen = Base64.encodeToString(image, Base64.DEFAULT);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
    );

    private void cargarImagen() {
        Intent data = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        data.setType("*/*");
        data = Intent.createChooser(data, "choose a file");
        sIntentActivityResultLauncher.launch(data);
    }
}

