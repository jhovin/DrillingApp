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
import pe.bonifacio.drillingapp.adapters.MaquinasAdapter;
import pe.bonifacio.drillingapp.api.WebServiceApi;
import pe.bonifacio.drillingapp.models.ApiServiceGenerator;
import pe.bonifacio.drillingapp.models.Maquina;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaMaquinasActivity extends AppCompatActivity {

    private static final String TAG=ListaMaquinasActivity.class.getSimpleName();
    private RecyclerView maquinasList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_maquinas);

        maquinasList = findViewById(R.id.recyclerview);
        maquinasList.setLayoutManager(new LinearLayoutManager(this));

        maquinasList.setAdapter(new MaquinasAdapter());

        initialize();
    }
    public  void initialize(){

        WebServiceApi service = ApiServiceGenerator.createService(WebServiceApi.class);

        service.getTodasLasMaquinas().enqueue(new Callback<List<Maquina>>() {

            @Override
            public void onResponse(@NonNull Call<List<Maquina>> call, @NonNull Response<List<Maquina>> response) {
                try {

                    if (response.isSuccessful()) {

                        List<Maquina> maquinas = response.body();
                        Log.d(TAG, "maquinas: " + maquinas);

                        MaquinasAdapter adapter = (MaquinasAdapter) maquinasList.getAdapter();
                        adapter.setMaquinas(maquinas);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(ListaMaquinasActivity.this, "Lista de Máquinas", Toast.LENGTH_SHORT).show();
                    } else {
                        throw new Exception(ApiServiceGenerator.parseError(response).getMessage());
                    }

                } catch (Throwable t) {
                    Log.e(TAG, "onThrowable: " + t.getMessage(), t);
                    Toast.makeText(ListaMaquinasActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Maquina>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage(), t);
                Toast.makeText(ListaMaquinasActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

}

