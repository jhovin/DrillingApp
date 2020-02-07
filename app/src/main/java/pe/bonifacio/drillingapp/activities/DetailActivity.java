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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

    private Long infoid;

    private TextView tvSistema;
    private TextView tvDescripcion;
    private TextView tvMotivo;
    private TextView tvOt;
    private TextView tvHorometro;
    private TextView tvEvento;
    private TextView tvFecha;
    private TextView tvObservacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvSistema = (TextView) findViewById(R.id.sistema_des);
        tvDescripcion = (TextView) findViewById(R.id.descripcion_des);
        tvMotivo = (TextView) findViewById(R.id.motivo_des);
        tvOt=(TextView)findViewById(R.id.ot_des);
        tvHorometro=(TextView)findViewById(R.id.horometro_des);
        tvEvento=(TextView)findViewById(R.id.evento_des);
        tvFecha=(TextView)findViewById(R.id.fecha_des);
        tvObservacion=(TextView)findViewById(R.id.observacion_des);

        infoid = getIntent().getExtras().getLong("ID");
        Log.e(TAG, "id:" + infoid);


        initialize();
    }

    private void initialize() {

        WebServiceApi service = ApiServiceGenerator.createService(WebServiceApi.class);

        Call<Informe> call = service.showInforme(infoid);

        call.enqueue(new Callback<Informe>() {
            @Override
            public void onResponse(Call<Informe> call, Response<Informe> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        Informe informe = response.body();
                        Log.d(TAG, "informe: " + informe);


                        tvSistema.setText(informe.getSistema());
                        tvDescripcion.setText(informe.getDescripcion());
                        tvMotivo.setText(informe.getMotivo());
                        tvOt.setText(informe.getOt());
                        tvHorometro.setText(informe.getHorometro());
                        tvEvento.setText(informe.getEvento());
                        tvFecha.setText(informe.getFecha());
                        tvObservacion.setText(informe.getObservacion());

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) {
                    }
                }
            }

            @Override
            public void onFailure(Call<Informe> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }
}

