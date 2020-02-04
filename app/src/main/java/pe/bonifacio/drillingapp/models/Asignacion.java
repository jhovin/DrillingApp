package pe.bonifacio.drillingapp.models;

public class Asignacion {

    private Long id;
    private Long proyecto_id;
    private Long maquina_id;
    private String fecha_asignacion;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProyecto_id() {
        return proyecto_id;
    }

    public void setProyecto_id(Long proyecto_id) {
        this.proyecto_id = proyecto_id;
    }

    public Long getMaquina_id() {
        return maquina_id;
    }

    public void setMaquina_id(Long maquina_id) {
        this.maquina_id = maquina_id;
    }

    public String getFecha_asignacion() {
        return fecha_asignacion;
    }

    public void setFecha_asignacion(String fecha_asignacion) {
        this.fecha_asignacion = fecha_asignacion;
    }
}
