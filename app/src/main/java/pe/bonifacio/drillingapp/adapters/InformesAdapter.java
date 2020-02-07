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
import pe.bonifacio.drillingapp.activities.DetailActivity;
import pe.bonifacio.drillingapp.models.Informe;


public class InformesAdapter extends RecyclerView.Adapter<InformesAdapter.ViewHolder> {

    private static final String TAG = InformesAdapter.class.getSimpleName();

    private List<Informe> informes;

    public InformesAdapter(){
        this.informes = new ArrayList<>();
    }

    public void setInformes(List<Informe> informes){
        this.informes = informes;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombreText;
        TextView fechaText;
        TextView observacionText;

        ViewHolder(View itemView) {
            super(itemView);
            nombreText = itemView.findViewById(R.id.sistema_text);
            fechaText=itemView.findViewById(R.id.fecha_text);
            observacionText=itemView.findViewById(R.id.observacion_text);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_informe, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        final Context context = viewHolder.itemView.getContext();

        final Informe informe = this.informes.get(position);

        viewHolder.nombreText.setText(informe.getSistema());
        viewHolder.fechaText.setText(informe.getFecha());
        viewHolder.observacionText.setText(informe.getObservacion());

        final Informe info= this.informes.get(position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewHolder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("ID" , info.getInfoid());
                viewHolder.itemView.getContext().startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return this.informes.size();
    }
}
