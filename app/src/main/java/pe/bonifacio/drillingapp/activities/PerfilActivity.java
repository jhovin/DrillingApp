package pe.bonifacio.drillingapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import pe.bonifacio.drillingapp.R;
import pe.bonifacio.drillingapp.api.WebService;
import pe.bonifacio.drillingapp.api.WebServiceApi;
import pe.bonifacio.drillingapp.models.Usuario;
import pe.bonifacio.drillingapp.shared_pref.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etEmail;
    private EditText etDni;
    private EditText etCargo;
    private ImageView ivUsuario;
    private TextView tvDelete;
    private TextView tvLogOut;
    private TextView tvProyectos;
    private TextView tvDato;
    private Button btUpdate;
    private Usuario usuario;
    private Bitmap bitmap;

    private static final int IMG_REQUEST=333;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        setUpProfesor();
        setUpView();
    }
    private void setUpProfesor(){
        usuario= SharedPrefManager.getInstance(getApplicationContext()).getUsuario();

    }
    public void setUpView(){

        etName=findViewById(R.id.etName);
        etName.setText(usuario.getNombre());
        etEmail=findViewById(R.id.etEmail);
        etEmail.setText(usuario.getEmail());
        etDni=findViewById(R.id.etDni);
        etDni.setText(usuario.getDni());
        etCargo=findViewById(R.id.etCargo);
        etCargo.setText(usuario.getCargo());
        tvDato=findViewById(R.id.tvDato);
        btUpdate=findViewById(R.id.btUpdate);
        tvDelete=findViewById(R.id.tvDelete);
        tvLogOut=findViewById(R.id.tvLogOut);

        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        //Actualizar Usuario
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUsuario();
            }
        });
        //Eliminar Usuario
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteById();
            }
        });

        //FOTO
        ivUsuario=findViewById(R.id.imageView);
        if(usuario.getFoto()!=null){
            ivUsuario.setImageBitmap(stringToImage(usuario.getFoto()));
        }
        ivUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarImagenPerfil();
            }
        });

        tvProyectos=findViewById(R.id.tvProyectos);
        tvProyectos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ProyectoActivity.class));
            }
        });

    }

    public void obtnerproyectos(){

    }

    //Cambiar foto de Perfil
    private Bitmap stringToImage(String encondedString){
        try{
            byte[] encodeByte = Base64.decode(encondedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch (Exception e){
            return null;
        }
    }
    private String imageToString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    public void cambiarImagenPerfil(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null){
            Uri path = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                if(bitmap != null){
                    ivUsuario.setImageBitmap(bitmap);
                    usuario.setFoto(imageToString());
                }else{
                    usuario.setFoto("");
                }
            }catch (IOException e){

            }
        }
    }


    public void logout(){
        SharedPrefManager.getInstance(getApplicationContext()).logOut();
        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
    }
    //Actualiza USUARIO
    public void updateUsuario(){
        String email = etEmail.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String cargo=etCargo.getText().toString().trim().toUpperCase();


        if(name.isEmpty()){
            etName.setError(getResources().getString(R.string.name_error));
            etName.requestFocus();
            return;
        }

        if(email.isEmpty()){
            etEmail.setError(getResources().getString(R.string.email_error));
            etEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError(getResources().getString(R.string.email_doesnt_match));
            etEmail.requestFocus();
            return;
        }
        if(cargo.isEmpty()){
            etCargo.setError("ingrese cargo");
            etCargo.requestFocus();
            return;
        }
        usuario.setNombre(name);
        usuario.setEmail(email);
        usuario.setCargo(cargo);

        Call<Usuario> call = WebService
                .getInstance()
                .createService(WebServiceApi.class)
                .update(usuario);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.code() == 200){
                    Log.d("TAG1", "Usuario actualizado correctamente");
                    SharedPrefManager.getInstance(getApplicationContext())
                            .saveUsuario(response.body());

                }else if(response.code()==400){
                    Log.d("TAG1", "Usuario no existe");
                }else{
                    Log.d("TAG1", "Error indeterminado");
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {

            }
        });
    }
    //Delete USUARIO
    public void deleteById(){
        Call<Void>call=WebService
                .getInstance()
                .createService()
                .deleteById(usuario.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    Log.d("TAG1", "Usuario eliminado correctamente");
                    logout();
                }else{
                    Log.d("TAG1", "Error no definido");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }


}
