package com.example.examenfinalronald;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MostrarFavoritos extends AppCompatActivity {


    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_favoritos);


        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        CARGARlIOBROS();

    }

    private void CARGARlIOBROS() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://6298a8b7f2decf5bb74859ed.mockapi.io/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Servicio service = retrofit.create(Servicio.class);


        Call<List<ModelLibro>> listGet = service.obtenerListaFavoritos();
        listGet.enqueue(new Callback<List<ModelLibro>>() {
            @Override
            public void onResponse(Call<List<ModelLibro>> call, Response<List<ModelLibro>> response) {


                if (response.code() == 200) {
                    List<ModelLibro> data = response.body();

                    adapterFa adapter = new adapterFa(data);
                    mRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<ModelLibro>> call, Throwable t) {

            }
        });
    }
}