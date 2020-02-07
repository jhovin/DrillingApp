package pe.bonifacio.drillingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pe.bonifacio.drillingapp.R;
import pe.bonifacio.drillingapp.activities.InformeActivity;
import pe.bonifacio.drillingapp.models.Maquina;

public class MaquinasAdapter extends RecyclerView.Adapter<MaquinasAdapter.ViewHolder> {

private static final String TAG = MaquinasAdapter.class.getSimpleName();

private List<Maquina> maquinas;

public MaquinasAdapter(){
        this.maquinas = new ArrayList<>();
        }

public void setMaquinas(List<Maquina> maquinas){
        this.maquinas = maquinas;
        }

class ViewHolder extends RecyclerView.ViewHolder{
    TextView nombreText;
    TextView tipoText;

    ViewHolder(View itemView) {
        super(itemView);
        nombreText = itemView.findViewById(R.id.nombre_maquina_text);
        tipoText=itemView.findViewById(R.id.tipo_maquina_text);
    }
}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maquina, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Context context = viewHolder.itemView.getContext();

        final Maquina maquina = this.maquinas.get(position);

        viewHolder.nombreText.setText(maquina.getNombre());
        viewHolder.tipoText.setText(maquina.getTipo());


        final Maquina maq = this.maquinas.get(position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InformeActivity.class);
                intent.putExtra("id", maq.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.maquinas.size();
    }
}
