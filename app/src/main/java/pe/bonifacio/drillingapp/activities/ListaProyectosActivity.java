package pe.bonifacio.drillingapp.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import pe.bonifacio.drillingapp.R;
import pe.bonifacio.drillingapp.adapters.ProyectosAdapter;
import pe.bonifacio.drillingapp.api.WebServiceApi;
import pe.bonifacio.drillingapp.models.ApiServiceGenerator;
import pe.bonifacio.drillingapp.models.Proyecto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaProyectosActivity extends AppCompatActivity {

    private static final String TAG=ListaProyectosActivity.class.getSimpleName();
    private RecyclerView proyectosList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_proyectos);

        proyectosList = findViewById(R.id.recyclerview);
        proyectosList.setLayoutManager(new LinearLayoutManager(this));

        proyectosList.setAdapter(new ProyectosAdapter());

        initialize();
    }
    public void initialize(){

        WebServiceApi service = ApiServiceGenerator.createService(WebServiceApi.class);

        service.getTodosLosProyectos().enqueue(new Callback<List<Proyecto>>() {

            @Override
            public void onResponse(@NonNull Call<List<Proyecto>> call, @NonNull Response<List<Proyecto>> response) {
                try {

                    if (response.isSuccessful()) {

                        List<Proyecto> proyectos = response.body();
                        Log.d(TAG, "proyectos: " + proyectos);

                        ProyectosAdapter adapter = (ProyectosAdapter) proyectosList.getAdapter();
                        adapter.setProyectos(proyectos);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(ListaProyectosActivity.this, "Lista de Proyectos", Toast.LENGTH_SHORT).show();
                    } else {
                        throw new Exception(ApiServiceGenerator.parseError(response).getMessage());
                    }

                } catch (Throwable t) {
                    Log.e(TAG, "onThrowable: " + t.getMessage(), t);
                    Toast.makeText(ListaProyectosActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Proyecto>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage(), t);
                Toast.makeText(ListaProyectosActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

}

