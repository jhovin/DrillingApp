package pe.bonifacio.drillingapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import pe.bonifacio.drillingapp.R;
import pe.bonifacio.drillingapp.api.WebServiceApi;
import pe.bonifacio.drillingapp.models.ApiServiceGenerator;
import pe.bonifacio.drillingapp.models.Informe;
import pe.bonifacio.drillingapp.models.Maquina;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMaquinaActivity extends AppCompatActivity {

    private static final String TAG = DetailMaquinaActivity.class.getSimpleName();

    private Long id;

    private TextView tvNombre;
    private TextView tvFecha;
    private TextView tvPlaca;
    private TextView tvSerie;
    private TextView tvLectura;
    private TextView tvTipo;
    private TextView tvObservacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_maquina);

        tvNombre = (TextView) findViewById(R.id.nombre_des);
        tvPlaca = (TextView) findViewById(R.id.placa_des);
        tvSerie = (TextView) findViewById(R.id.serie_des);
        tvLectura=(TextView)findViewById(R.id.lectura_des);
        tvTipo=(TextView)findViewById(R.id.tipo_des);
        tvFecha=(TextView)findViewById(R.id.fecha_des);
        tvObservacion=(TextView)findViewById(R.id.observacion_des);

        id = getIntent().getExtras().getLong("ID");
        Log.e(TAG, "id:" + id);

        maquina();
    }
    public void maquina(){

        WebServiceApi service = ApiServiceGenerator.createService(WebServiceApi.class);

        Call<Maquina> call = service.showMaquina(id);

        call.enqueue(new Callback<Maquina>() {
            @Override
            public void onResponse(Call<Maquina> call, Response<Maquina> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        Maquina maquina = response.body();
                        Log.d(TAG, "maquina: " + maquina);

                        tvNombre.setText(maquina.getNombre());
                        tvPlaca.setText(maquina.getPlaca());
                        tvSerie.setText(maquina.getSerie());
                        tvLectura.setText(maquina.getLectura());
                        tvTipo.setText(maquina.getTipo());
                        tvFecha.setText(maquina.getFecha_inicio());
                        tvObservacion.setText(maquina.getObservacion());

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(DetailMaquinaActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) {
                    }
                }
            }

            @Override
            public void onFailure(Call<Maquina> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(DetailMaquinaActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }
}
