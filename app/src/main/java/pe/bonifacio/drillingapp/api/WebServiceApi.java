package pe.bonifacio.drillingapp.api;

import java.util.List;

import pe.bonifacio.drillingapp.models.ApiMessage;
import pe.bonifacio.drillingapp.models.Asignacion;
import pe.bonifacio.drillingapp.models.Informe;
import pe.bonifacio.drillingapp.models.Maquina;
import pe.bonifacio.drillingapp.models.Proyecto;
import pe.bonifacio.drillingapp.models.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface WebServiceApi {


    String BASE_URL = "http://10.0.2.2:8040/";

    /*
       USUARIOS
        */

    @POST("/api/usuarios")
    Call<Void> registrarUsuario(@Body Usuario usuario);

    @POST("/api/login")
    Call<List<Usuario>> login(@Body Usuario usuario);

    @DELETE("api/usuarios/{id}")
    Call<Void> deleteById(@Path("id") Long id);

    @PUT("api/update_sql")
    Call<Usuario> update(@Body Usuario usuario);

    @GET("api/usuarios")
    Call<List<Usuario>> getUsuarios();


    /*
   Proyectos
    */
    @POST("api/proyectos")
    Call<Void> crearProyecto(@Body Proyecto proyecto);

    @GET("api/proyectos")
    Call<List<Proyecto>> getTodosLosProyectos();

    @POST("api/proyectos_usuario")
    Call<List<Proyecto>> getProyectosUsuarios(@Body Usuario usuario);

    @DELETE("api/proyectos/{proid}")
    Call<ApiMessage>destroyProyecto(@Path("proid")Long proid);



    /*
      Maquinas
    */

    @POST("api/maquinas")
    Call<Void>crearMaquina(@Body Maquina Maquina);

    @GET("api/maquinas")
    Call<List<Maquina>>getTodasLasMaquinas();

    @GET("api/maquinas/{id}")
    Call<Maquina>showMaquina(@Path("id")Long id);


    @DELETE("api/maquinas/{id}")
    Call<ApiMessage>destroyMaquina(@Path("id")Long id);



       /*
      Informes
    */

    @POST("api/informes")
    Call<Void>crearInforme(@Body Informe informe);
    @GET("api/informes")
    Call<List<Informe>>getTodasLosInformes();
    @GET("/api/informes/{infoid}")
    Call<Informe>showInforme(@Path("infoid") Long infoid);

    /*
     Asignaciones
     */
    @POST("api/asignaciones")
    Call<Void>crearAsignacion(@Body Asignacion asignacion);








}
