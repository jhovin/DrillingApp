
package pe.bonifacio.drillingapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import pe.bonifacio.drillingapp.R;
import pe.bonifacio.drillingapp.api.WebService;
import pe.bonifacio.drillingapp.api.WebServiceApi;
import pe.bonifacio.drillingapp.models.Maquina;
import pe.bonifacio.drillingapp.models.Proyecto;
import pe.bonifacio.drillingapp.models.Usuario;
import pe.bonifacio.drillingapp.shared_pref.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MaquinaActivity extends AppCompatActivity {

    private static String TAG = DetailActivity.class.getSimpleName();

    private EditText etFecha;
    private EditText etMaquina;
    private EditText etTipo;
    private EditText etPlaca;
    private EditText etMotor;
    private EditText etHorometro;
    private EditText etObservacion;
    private Button btCrearMaquina;
    private Button btVerTodosMaquinas;

    private Usuario usuario;
    private Proyecto proyecto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maquina);

        maquina();
    }
    public void maquina(){

        usuario= SharedPrefManager.getInstance(getApplicationContext()).getUsuario();
        etFecha=findViewById(R.id.etFecha);
        etMaquina=findViewById(R.id.etMaquina);
        etTipo=findViewById(R.id.etTipo);
        etPlaca=findViewById(R.id.etPlaca);
        etMotor=findViewById(R.id.etMotor);
        etHorometro=findViewById(R.id.etLecturaHorometro);
        etObservacion=findViewById(R.id.etObservacion);

        btVerTodosMaquinas=findViewById(R.id.btVerTodosMaquinas);
        btVerTodosMaquinas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todasMaquinas();
                startActivity(new Intent(getApplicationContext(),ListaMaquinasActivity.class));
            }
        });

        btCrearMaquina=findViewById(R.id.btCrearMaquina);
        btCrearMaquina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearMaquina();
            }
        });

    }
    //Crear Maquina
    public void crearMaquina(){
        Maquina maquina = new Maquina();

        maquina.setFecha_inicio(etFecha.getText().toString().trim());
        if(etFecha.getText().toString().isEmpty()){
            etFecha.setError("Ingrese la fecha de inicio");
            etFecha.requestFocus();
            return;
        }
        maquina.setNombre(etMaquina.getText().toString().toUpperCase());
        if(etMaquina.getText().toString().isEmpty()){
            etMaquina.setError("Ingrese nombre de la máquina");
            etMaquina.requestFocus();
            return;
        }
        maquina.setTipo(etTipo.getText().toString().trim().toUpperCase());
        if(etTipo.getText().toString().isEmpty()){
            etTipo.setError("Ingrese el tipo de máquina");
            etTipo.requestFocus();
            return;
        }

        maquina.setPlaca(etPlaca.getText().toString());
        if(etPlaca.getText().toString().isEmpty()){
            etPlaca.setError("Ingrese la placa");
            etPlaca.requestFocus();
            return;

        }
        maquina.setSerie(etMotor.getText().toString());
        if(etMotor.getText().toString().isEmpty()){
            etMotor.setError("Ingrese serie de motor");
            etMotor.requestFocus();
            return;
        }
        maquina.setLectura(etHorometro.getText().toString());
        if(etHorometro.getText().toString().isEmpty()){
            etHorometro.setError("Ingrese lectura del horometro");
            etHorometro.requestFocus();
            return;
        }
        maquina.setObservacion(etObservacion.getText().toString());


        Call<Void> call= WebService
                .getInstance()
                .createService(WebServiceApi.class)
                .crearMaquina(maquina);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 201) {
                    Log.d("TAG1", "Maquina creado correctamente");
                    Toast.makeText(MaquinaActivity.this, "Máquina creada correctamente", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),ListaMaquinasActivity.class));
                    finish();
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    //Ver todas las Máquinas
    public void todasMaquinas(){
    Call<List<Maquina>> call = WebService
            .getInstance()
            .createService(WebServiceApi.class)
            .getTodasLasMaquinas();

        call.enqueue(new Callback<List<Maquina>>() {
        @Override
        public void onResponse(Call<List<Maquina>> call, Response<List<Maquina>> response) {
            if(response.code()==200){
                for(int i=0; i<response.body().size(); i++){
                    Log.d("TAG1", "Nombre Maquina: " + response.body().get(i).getNombre());
                }
            }else if(response.code()==404){
                Log.d("TAG1", "No hay maquinas");
                Toast.makeText(MaquinaActivity.this, "No hay máquinas", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<List<Maquina>> call, Throwable t) {

        }
    });

}

}
