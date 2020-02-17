package pe.bonifacio.drillingapp.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
    private FloatingActionButton floatingActionButton;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_maquinas);

        floatingActionButton=findViewById(R.id.btn_showRegister);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AsignarActivity.class));
            }
        });


        maquinasList = findViewById(R.id.recyclerview);
        maquinasList.setLayoutManager(new LinearLayoutManager(this));

        maquinasList.setAdapter(new MaquinasAdapter());

        swipeRefreshLayout=findViewById(R.id.swiperefresh_lista_maquinas);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initialize();
            }
        });

        initialize();
    }
    public  void initialize(){

        swipeRefreshLayout.setRefreshing(true);
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
                }finally {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Maquina>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage(), t);
                Toast.makeText(ListaMaquinasActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }

        });
    }

}

