package pe.bonifacio.drillingapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pe.bonifacio.drillingapp.R;
import pe.bonifacio.drillingapp.activities.DetailMaquinaActivity;
import pe.bonifacio.drillingapp.activities.InformeActivity;
import pe.bonifacio.drillingapp.activities.LoginActivity;
import pe.bonifacio.drillingapp.activities.PerfilActivity;
import pe.bonifacio.drillingapp.api.WebServiceApi;
import pe.bonifacio.drillingapp.models.ApiMessage;
import pe.bonifacio.drillingapp.models.ApiServiceGenerator;
import pe.bonifacio.drillingapp.models.Maquina;
import pe.bonifacio.drillingapp.shared_pref.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    ImageButton menuButton;


    ViewHolder(View itemView) {
        super(itemView);
        nombreText = itemView.findViewById(R.id.nombre_maquina_text);
        tipoText=itemView.findViewById(R.id.tipo_maquina_text);
        menuButton=itemView.findViewById(R.id.menu_button);

    }
}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maquina, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final Context context = viewHolder.itemView.getContext();

        final Maquina maquina = this.maquinas.get(position);

        viewHolder.nombreText.setText(maquina.getNombre());
        viewHolder.tipoText.setText(maquina.getTipo());

        viewHolder.menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(final MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.remove_button:
                                        WebServiceApi service = ApiServiceGenerator.createService(WebServiceApi.class);

                                        service.destroyMaquina(maquina.getId()).enqueue(new Callback<ApiMessage>() {
                                            @Override
                                            public void onResponse(@NonNull Call<ApiMessage> call, @NonNull Response<ApiMessage> response) {
                                                try {

                                                    int statusCode = response.code();
                                                    Log.d(TAG, "HTTP status code: " + statusCode);

                                                    if (response.isSuccessful()) {

                                                        ApiMessage apiMessage = response.body();
                                                        Log.d(TAG, "apiMessage: " + apiMessage);

                                                        // Eliminar item del recyclerView y notificar cambios
                                                        maquinas.remove(position);
                                                        notifyItemRemoved(position);
                                                        notifyItemRangeChanged(position, maquinas.size());

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
                            case R.id.mas_button:

                                WebServiceApi servicio = ApiServiceGenerator.createService(WebServiceApi.class);

                                servicio.showMaquina(maquina.getId()).enqueue(new Callback<Maquina>() {
                                    @Override
                                    public void onResponse(Call<Maquina> call, Response<Maquina> response) {
                                        try {

                                            int statusCode = response.code();
                                            Log.d(TAG, "HTTP status code: " + statusCode);

                                            if (response.isSuccessful()) {

                                                Maquina maquina = response.body();
                                                Log.d(TAG, "maquina: " + maquina);

                                                maquina.setNombre(maquina.getNombre());
                                                maquina.setPlaca(maquina.getPlaca());
                                                maquina.setSerie(maquina.getSerie());
                                                maquina.setLectura(maquina.getLectura());
                                                maquina.setTipo(maquina.getTipo());
                                                maquina.setFecha_inicio(maquina.getFecha_inicio());
                                                maquina.setObservacion(maquina.getObservacion());

                                            } else {
                                                Log.e(TAG, "onError: " + response.errorBody().string());
                                                throw new Exception("Error en el servicio");
                                            }

                                        } catch (Throwable t) {
                                            try {
                                                Log.e(TAG, "onThrowable: " + t.toString(), t);
                                            } catch (Throwable x) {
                                            }
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<Maquina> call, Throwable t) {
                                        Log.e(TAG, "onFailure: " + t.toString());
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
        final Maquina maq = this.maquinas.get(position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InformeActivity.class);
                intent.putExtra("id", maq.getId());
                context.startActivity(intent);
                Toast.makeText(context, "Registro de Informes Diarios", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.maquinas.size();
    }


}























