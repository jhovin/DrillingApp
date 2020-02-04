package pe.bonifacio.drillingapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import pe.bonifacio.drillingapp.R;
import pe.bonifacio.drillingapp.api.WebService;
import pe.bonifacio.drillingapp.api.WebServiceApi;
import pe.bonifacio.drillingapp.models.Informe;
import pe.bonifacio.drillingapp.models.Usuario;
import pe.bonifacio.drillingapp.shared_pref.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformeActivity extends AppCompatActivity {

    private EditText etSistema;
    private EditText etDescripcion;
    private EditText etMotivo;
    private EditText etOt;
    private EditText etLecHorometro;
    private EditText etEvento;
    private EditText etFechaInforme;
    private EditText etObservacion;
    private Button btCrearInforme;
    private Button btVerInformes;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informe);

        informe();
    }
    public void informe(){

        usuario= SharedPrefManager.getInstance(getApplicationContext()).getUsuario();
        etSistema=findViewById(R.id.etSistema);
        etDescripcion=findViewById(R.id.etDescripcion);
        etMotivo=findViewById(R.id.etMotivo);
        etOt=findViewById(R.id.etOt);
        etLecHorometro=findViewById(R.id.etLecHorometro);
        etEvento=findViewById(R.id.etEvento);
        etFechaInforme=findViewById(R.id.etFecha_informe);
        etObservacion=findViewById(R.id.etDescripcion);
        btCrearInforme=findViewById(R.id.btRegistrar);
        btCrearInforme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearInforme();
                startActivity(new Intent(getApplicationContext(),ListaInformesActivity.class));
            }
        });
        btVerInformes=findViewById(R.id.btVerInformes);
        btVerInformes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verInformes();
                startActivity(new Intent(getApplicationContext(),ListaInformesActivity.class));
            }
        });

    }
    //Crear Informe
    public void crearInforme(){
        Informe info= new Informe();
        info.setSistema(etSistema.getText().toString().trim());
        info.setDescripcion(etDescripcion.getText().toString().trim());
        info.setMotivo(etMotivo.getText().toString().trim());
        info.setOt(etOt.getText().toString().trim());
        info.setHorometro((etLecHorometro.getText().toString().trim()));
        info.setEvento(etEvento.getText().toString().trim());
        info.setFecha(etFechaInforme.getText().toString().trim());
        info.setObservacion(etObservacion.getText().toString().trim());

        Call<Void> call= WebService
                .getInstance()
                .createService(WebServiceApi.class)
                .crearInforme(info);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 201) {
                    Log.d("TAG1", "Informe creado correctamente");
                    Toast.makeText(InformeActivity.this, "Informe creado correctamente", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });


    }
    public void verInformes(){
        Call<List<Informe>> call = WebService
                .getInstance()
                .createService(WebServiceApi.class)
                .getTodasLosInformes();

        call.enqueue(new Callback<List<Informe>>() {
            @Override
            public void onResponse(Call<List<Informe>> call, Response<List<Informe>> response) {
                if(response.code()==200){
                    for(int i=0; i<response.body().size(); i++){
                            Log.d("TAG1", "Nombre Informe: " + response.body().get(i).getSistema());
                    }
                }else if(response.code()==404){
                    Log.d("TAG1", "No hay Informes");
                    Toast.makeText(InformeActivity.this, "No hay informes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Informe>> call, Throwable t) {

            }
        });

    }

}
