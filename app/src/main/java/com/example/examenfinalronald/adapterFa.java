package com.example.examenfinalronald;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class adapterFa extends RecyclerView.Adapter<adapterFa.ViewHolder> {


    List<ModelLibro> libroList;

    public adapterFa(List<ModelLibro> libroList) {
        this.libroList = libroList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_libros, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ModelLibro libro = libroList.get(position);
        holder.nombre.setText(libro.getTitulo());
        Picasso.get()
                .load(libro.getImagen())
                .into(holder.imagen);
    }

    @Override
    public int getItemCount() {
        return libroList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombre;

        ImageView imagen;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre);

            imagen = itemView.findViewById(R.id.imagen);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
