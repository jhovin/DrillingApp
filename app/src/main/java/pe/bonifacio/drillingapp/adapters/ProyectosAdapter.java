package pe.bonifacio.drillingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pe.bonifacio.drillingapp.R;
import pe.bonifacio.drillingapp.models.Proyecto;

public class ProyectosAdapter extends RecyclerView.Adapter<ProyectosAdapter.ViewHolder>{

private static final String TAG = ProyectosAdapter.class.getSimpleName();

private List<Proyecto> proyectos;

public ProyectosAdapter(){

        this.proyectos = new ArrayList<>();
        }
public void setProyectos(List<Proyecto> proyectos){
        this.proyectos = proyectos;
        }

class ViewHolder extends RecyclerView.ViewHolder{

    ImageView fotoImage;
    TextView nombreText;
    TextView distritoText;


    ViewHolder(View itemView) {
        super(itemView);
        fotoImage = itemView.findViewById(R.id.foto_image);
        nombreText = itemView.findViewById(R.id.nombre_text);
        distritoText=itemView.findViewById(R.id.distrito_text);

    }
}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proyecto, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int position) {

        final Context context = viewHolder.itemView.getContext();

        final Proyecto proyecto = this.proyectos.get(position);

        viewHolder.nombreText.setText(proyecto.getNombre());
        viewHolder.distritoText.setText(proyecto.getDistrito());

        final Proyecto pro = this.proyectos.get(position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return this.proyectos.size();
    }
}


