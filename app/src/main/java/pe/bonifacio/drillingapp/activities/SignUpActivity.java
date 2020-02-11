package pe.bonifacio.drillingapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pe.bonifacio.drillingapp.R;
import pe.bonifacio.drillingapp.api.WebService;
import pe.bonifacio.drillingapp.api.WebServiceApi;
import pe.bonifacio.drillingapp.models.Usuario;
import pe.bonifacio.drillingapp.shared_pref.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etPassword;
    private EditText etEmail;
    private EditText etDni;
    private EditText etCargo;
    private Button btSignUp;
    private TextView tvLogin;

    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setUpView();
    }

    public void setUpView() {
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etCargo=findViewById(R.id.etCargo);
        etDni=findViewById(R.id.etDni);
        etPassword = findViewById(R.id.etPassword);
        btSignUp = findViewById(R.id.btSignUp);
        tvLogin = findViewById(R.id.tvLogin);

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSignUp();

            }
        });
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

    }

    private void userSignUp() {
        String email = etEmail.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String dni=etDni.getText().toString().trim();
        String cargo=etCargo.getText().toString().trim().toUpperCase();
        String password = etPassword.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("Ingrese un nombre");
            etName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            etEmail.setError(getResources().getString(R.string.email_error));
            etEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError(getResources().getString(R.string.email_doesnt_match));
            etEmail.requestFocus();
            return;
        }
        if(dni.isEmpty()){
            etDni.setError("Ingrese correcto tu dni");
            etDni.requestFocus();
            return;
        }
        if(dni.length()<8){
            etDni.setError("Dni no debe ser menor a 8 digitos");
            etDni.requestFocus();
            return;
        }
        if(cargo.isEmpty()){
            etCargo.setError("Ingrese un cargo");
            etCargo.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError(getResources().getString(R.string.password_error));
            etPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPassword.setError(getResources().getString(R.string.password_error_less_than));
            etPassword.requestFocus();
            return;
        }
        usuario = new Usuario();
        usuario.setNombre(name);
        usuario.setEmail(email);
        usuario.setDni(dni);
        usuario.setCargo(cargo);
        usuario.setPassword(password);
        crearUsuario();

    }
    //Crear Usuarios
    public void crearUsuario() {
        /*
        String BASE_URL = "http://10.0.2.2:8040";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebServiceApi api = WebService.getInstance().createService();
        Call<Void> call = api.registrarUsuario(usuario);
         */
        Call<Void> call= WebService
                .getInstance()
                .createService(WebServiceApi.class)
                .registrarUsuario(usuario);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 201) {
                    Log.d("TAG1", "Usuario guardado correctamente");
                    Toast.makeText(SignUpActivity.this, "Usuario Guardado correctamente", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                } else if (response.code() == 409) {
                    Log.d("TAG1", "Usuario ya existe");
                    Toast.makeText(SignUpActivity.this, "Usuario ya existe", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("TAG1", "error no definido");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("TAG1 Error: ",t.getMessage().toString());
            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            Log.d("TAG1","Usuario se inicio correctamente");
            Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),PerfilActivity.class));

        }
    }
}


