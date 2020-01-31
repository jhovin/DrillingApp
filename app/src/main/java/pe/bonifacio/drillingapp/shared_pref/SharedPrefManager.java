package pe.bonifacio.drillingapp.shared_pref;

import android.content.Context;
import android.content.SharedPreferences;

import pe.bonifacio.drillingapp.models.Usuario;

public class SharedPrefManager {
    private static final String SHARED_PREFERENCES = "SHARED_PREFERENCES";
    private static final String SHARED_PREFERENCES_ID = "SHARED_PREFERENCES_ID";
    private static final String SHARED_PREFERENCES_EMAIL = "SHARED_PREFERENCES_EMAIL";
    private static final String SHARED_PREFERENCES_NAME = "SHARED_PREFERENCES_NAME";
    private static final String SHARED_PREFERENCES_DNI = "SHARED_PREFERENCES_DNI";
    private static final String SHARED_PREFERENCES_CARGO = "SHARED_PREFERENCES_CARGO";
    private static final String SHARED_PREFERENCES_FOTO = "SHARED_PREFERENCES_FOTO";


    private static SharedPrefManager instance;
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SharedPrefManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if(instance == null){
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public void saveUsuario(Usuario usuario){
        editor.putLong(SHARED_PREFERENCES_ID, usuario.getId());
        editor.putString(SHARED_PREFERENCES_NAME, usuario.getNombre());
        editor.putString(SHARED_PREFERENCES_DNI, usuario.getDni());
        editor.putString(SHARED_PREFERENCES_CARGO, usuario.getCargo());
        editor.putString(SHARED_PREFERENCES_EMAIL, usuario.getEmail());
        editor.putString(SHARED_PREFERENCES_FOTO, usuario.getFoto());
        editor.apply();
    }

    public boolean isLoggedIn(){
        if(sharedPreferences.getLong(SHARED_PREFERENCES_ID, -1) != -1){
            return true;
        }
        return false;
    }
    public Usuario getUsuario(){

        Usuario usuario=new Usuario(
                sharedPreferences.getLong(SHARED_PREFERENCES_ID,-1),
                sharedPreferences.getString(SHARED_PREFERENCES_NAME,null),
                sharedPreferences.getString(SHARED_PREFERENCES_DNI,null),
                sharedPreferences.getString(SHARED_PREFERENCES_CARGO,null),
                sharedPreferences.getString(SHARED_PREFERENCES_EMAIL,null),
                sharedPreferences.getString(SHARED_PREFERENCES_FOTO,null)

        );
        return usuario;
    }


    public void logOut(){
        editor.clear();
        editor.apply();
    }
}
