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

public class adapter extends RecyclerView.Adapter<adapter.ViewHolder> {

    Context context;
    List<ModelLibro> libroList;

    public adapter(List<ModelLibro> libroList, Context context) {
        this.libroList = libroList;
        this.context = context;
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

        holder.linearLayout.setOnClickListener(view -> {
            Intent data = new Intent(context, detalleLibro.class);
            data.putExtra("id", libro.getId());
            context.startActivity(data);
        });

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
