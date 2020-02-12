package pe.bonifacio.drillingapp.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
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
    private int mYearIni, mMonthIni, mDayIni, sYearIni, sMonthIni, sDayIni;
    static final int DATE_ID = 0;
    Calendar C = Calendar.getInstance();

    private EditText etObservacion;
    private Button btCrearInforme;
    private Button btVerInformes;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informe);
        sMonthIni = C.get(Calendar.MONTH);
        sDayIni = C.get(Calendar.DAY_OF_MONTH);
        sYearIni = C.get(Calendar.YEAR);

        EditText etFechaInforme=findViewById(R.id.etFecha_informe);
        int textLength = etFechaInforme.getText().length();
        etFechaInforme.setSelection(textLength,textLength);
        etFechaInforme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_ID);
            }
        });


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
        if(etSistema.getText().toString().isEmpty()){
            etSistema.setError("Ingrese el sistema");
            etSistema.requestFocus();
            return;
        }
        info.setDescripcion(etDescripcion.getText().toString().trim());
        if(etDescripcion.getText().toString().isEmpty()){
            etDescripcion.setError("Ingrese la descripción");
            etDescripcion.requestFocus();
            return;
        }
        info.setMotivo(etMotivo.getText().toString().trim());
        if(etMotivo.getText().toString().isEmpty()){
            etMotivo.setError("Ingrese el motivo");
            etMotivo.requestFocus();
            return;
        }
        info.setOt(etOt.getText().toString().trim());
        if(etOt.getText().toString().isEmpty()){
            etOt.setError("Ingrese el número de ot");
            etOt.requestFocus();
            return;
        }
        info.setHorometro((etLecHorometro.getText().toString().trim()));
        if(etLecHorometro.getText().toString().isEmpty()){
            etLecHorometro.setError("Ingrese la lectura de horómetro");
            etLecHorometro.requestFocus();
            return;
        }
        info.setEvento(etEvento.getText().toString().trim());
        if(etEvento.getText().toString().isEmpty()){
            etEvento.setError("Ingrese el evento");
            etEvento.requestFocus();
            return;
        }
        info.setFecha(etFechaInforme.getText().toString().trim());
        if(etFechaInforme.getText().toString().isEmpty()){
            etFechaInforme.setError("Ingrese la fecha");
            etFechaInforme.requestFocus();
            return;
        }
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
                    startActivity(new Intent(getApplicationContext(),ListaInformesActivity.class));
                    finish();
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });


    }
    //Ver los Informes
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

    public void colocar_fecha(){

        etFechaInforme.setText (mDayIni + "/" + (mMonthIni + 1) + "/" + mYearIni+" ");
    }
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYearIni = year;
                    mMonthIni = monthOfYear;
                    mDayIni = dayOfMonth;
                    colocar_fecha();

                }

            };
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_ID:
                return new DatePickerDialog(this, mDateSetListener, sYearIni, sMonthIni, sDayIni);
        }

        return null;
    }

}
