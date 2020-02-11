package pe.bonifacio.drillingapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pe.bonifacio.drillingapp.R;
import pe.bonifacio.drillingapp.api.WebService;
import pe.bonifacio.drillingapp.api.WebServiceApi;
import pe.bonifacio.drillingapp.models.Usuario;
import pe.bonifacio.drillingapp.shared_pref.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    private EditText etPassword;
    private EditText etEmail;
    private Button btLogin;
    private TextView tvSignUp;


    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUpView();
    }
    public void setUpView() {

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btLogin = findViewById(R.id.btLogin);
        tvSignUp = findViewById(R.id.tvSignUp);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
            }
        });
    }
    //Ingresar e Validar Login
    private void userLogin() {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

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

        if (password.isEmpty()) {
            etPassword.setError("Ingrese correctamente");
            etPassword.requestFocus();
            return;
        }
        if (password.isEmpty()){
            etPassword.setError(getResources().getString(R.string.password_error_less_than));
            etPassword.requestFocus();
            return;
        }
        usuario =new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(password);
        login();

    }
    //Ingresar Usuario Logueado
    public void  login(){
        Call<List<Usuario>>call=WebService
                .getInstance()
                .createService(WebServiceApi.class)
                .login(usuario);
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                if(response.code() == 200){
                    Log.d("TAG1", "Usuario logeado "+"id"+response.body().get(0).getId()
                            +"email: "+response.body().get(0).getEmail());
                    Toast.makeText(LoginActivity.this, "BIENVENIDOS", Toast.LENGTH_SHORT).show();
                    SharedPrefManager.getInstance(getApplicationContext())
                            .saveUsuario(response.body().get(0));
                    startActivity(new Intent(getApplicationContext(),PerfilActivity.class));


                }else if (response.code()==404){
                    Log.d("TAG1", "Usuario no existe");
                    Toast.makeText(LoginActivity.this, "Correo y/o clave inválido", Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("TAG1", "Error Desconocido");
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Usuario inválido: "+t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    //Redirect el intranet
    public void irIntranet(View view) {
        Uri uri = Uri.parse("http://intranet.redrilsa.com.pe/inicio.asp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
