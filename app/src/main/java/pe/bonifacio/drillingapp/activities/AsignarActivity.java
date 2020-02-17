package pe.bonifacio.drillingapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pe.bonifacio.drillingapp.R;
import pe.bonifacio.drillingapp.api.WebService;
import pe.bonifacio.drillingapp.api.WebServiceApi;
import pe.bonifacio.drillingapp.models.Asignacion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsignarActivity extends AppCompatActivity {

    private static String TAG = AsignarActivity.class.getSimpleName();
    private EditText etFechaAsignacion;
    private EditText etTipoMaquinas;
    private Button btAsignar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignar);

        asignar();
    }

    private void asignar() {

        etFechaAsignacion=findViewById(R.id.etFechaAsignacion);
        etTipoMaquinas=findViewById(R.id.etTipoMaquinas);
        btAsignar=findViewById(R.id.btAsignar);
        btAsignar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearAsignacion();
            }
        });

    }
    public void crearAsignacion(){
        Asignacion asignar=new Asignacion();
        asignar.setFecha_asignacion(etFechaAsignacion.getText().toString().trim());
        if(etFechaAsignacion.getText().toString().isEmpty()){
            etFechaAsignacion.setError("Ingrese una fecha de asignación");
            etFechaAsignacion.requestFocus();
            return;
        }
        Call<Void> call= WebService
                .getInstance()
                .createService(WebServiceApi.class)
                .crearAsignacion(asignar);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 201) {
                    Log.d("TAG1", "Maquina creado correctamente");
                    Toast.makeText(AsignarActivity.this, "Máquina asignada correctamente", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),ListaMaquinasActivity.class));
                }

            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }

}
