package pe.bonifacio.drillingapp.activities;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Collection;
import java.util.List;

import pe.bonifacio.drillingapp.R;
import pe.bonifacio.drillingapp.adapters.InformesAdapter;
import pe.bonifacio.drillingapp.api.WebServiceApi;
import pe.bonifacio.drillingapp.models.ApiServiceGenerator;
import pe.bonifacio.drillingapp.models.Informe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaInformesActivity extends AppCompatActivity {

    private static final String TAG=ListaInformesActivity.class.getSimpleName();
    private RecyclerView informesList;
    public SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_informes);

        informesList = findViewById(R.id.recyclerview);
        informesList.setLayoutManager(new LinearLayoutManager(this));

        informesList.setAdapter(new InformesAdapter());

        swipeRefreshLayout=findViewById(R.id.swiperefresh_lista_informes);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initialize();
            }
        });
        initialize();
    }
    public void initialize(){
        swipeRefreshLayout.setRefreshing(true);
        WebServiceApi service = ApiServiceGenerator.createService(WebServiceApi.class);
        service.getTodasLosInformes().enqueue(new Callback<List<Informe>>() {

            @Override
            public void onResponse(@NonNull Call<List<Informe>> call, @NonNull Response<List<Informe>> response) {
                try {

                    if (response.isSuccessful()) {

                        List<Informe> informes = response.body();
                        Log.d(TAG, "informes: " + informes);
                        InformesAdapter adapter = (InformesAdapter) informesList.getAdapter();
                        adapter.setInformes(informes);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(ListaInformesActivity.this, "Lista de Informes Diarios", Toast.LENGTH_SHORT).show();
                    } else {
                        throw new Exception(ApiServiceGenerator.parseError(response).getMessage());
                    }

                } catch (Throwable t) {
                    Log.e(TAG, "onThrowable: " + t.getMessage(), t);
                    Toast.makeText(ListaInformesActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }finally {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Informe>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage(), t);
                Toast.makeText(ListaInformesActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }

        });
    }

}
