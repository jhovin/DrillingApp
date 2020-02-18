package pe.bonifacio.drillingapp.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pe.bonifacio.drillingapp.R;
import pe.bonifacio.drillingapp.activities.MaquinaActivity;
import pe.bonifacio.drillingapp.activities.ProyectoActivity;
import pe.bonifacio.drillingapp.api.WebServiceApi;
import pe.bonifacio.drillingapp.models.ApiMessage;
import pe.bonifacio.drillingapp.models.ApiServiceGenerator;
import pe.bonifacio.drillingapp.models.Proyecto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    ImageButton menuButton;


    ViewHolder(View itemView) {
        super(itemView);
        fotoImage = itemView.findViewById(R.id.foto_image);
        nombreText = itemView.findViewById(R.id.nombre_text);
        distritoText=itemView.findViewById(R.id.distrito_text);
        menuButton=itemView.findViewById(R.id.menu_button);


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

        viewHolder.menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.remove_button:

                                WebServiceApi service = ApiServiceGenerator.createService(WebServiceApi.class);

                                service.destroyProyecto(proyecto.getProid()).enqueue(new Callback<ApiMessage>() {
                                    @Override
                                    public void onResponse(@NonNull Call<ApiMessage> call, @NonNull Response<ApiMessage> response) {
                                        try {

                                            int statusCode = response.code();
                                            Log.d(TAG, "HTTP status code: " + statusCode);

                                            if (response.isSuccessful()) {

                                                ApiMessage apiMessage = response.body();
                                                Log.d(TAG, "apiMessage: " + apiMessage);

                                                // Eliminar item del recyclerView y notificar cambios
                                                proyectos.remove(position);
                                                notifyItemRemoved(position);
                                                notifyItemRangeChanged(position, proyectos.size());

                                                Toast.makeText(v.getContext(), apiMessage.getMessage(), Toast.LENGTH_LONG).show();

                                            } else {
                                                Log.e(TAG, "onError: " + response.errorBody().string());
                                                throw new Exception("Error en el servicio");
                                            }

                                        } catch (Throwable t) {
                                            Log.e(TAG, "onThrowable: " + t.getMessage(), t);
                                            Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<ApiMessage> call, @NonNull Throwable t) {
                                        Log.e(TAG, "onFailure: " + t.getMessage(), t);
                                        Toast.makeText(v.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                });

                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
        final Proyecto pro = this.proyectos.get(position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MaquinaActivity.class);
                intent.putExtra("id", pro.getProid());
                context.startActivity(intent);
                Toast.makeText(context, "Registro de Máquinas", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return this.proyectos.size();
    }
}


