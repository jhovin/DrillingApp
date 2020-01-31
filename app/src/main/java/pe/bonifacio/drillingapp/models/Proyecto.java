package pe.bonifacio.drillingapp.models;

public class Proyecto {

    private Long proid;
    private String nombre;
    private String distrito;
    private String provincia;
    private String departamento;
    private String fecha_inicio;
    private String fecha_fin;
    private Long prousuario;
    private Long procliente;


    public Long getProid() {
        return proid;
    }

    public void setProid(Long proid) {
        this.proid = proid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public Long getProusuario() {
        return prousuario;
    }

    public void setProusuario(Long prousuario) {
        this.prousuario = prousuario;
    }

    public Long getProcliente() {
        return procliente;
    }

    public void setProcliente(Long procliente) {
        this.procliente = procliente;
    }
}
