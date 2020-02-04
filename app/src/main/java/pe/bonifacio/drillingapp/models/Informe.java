package pe.bonifacio.drillingapp.models;

public class Informe {

    private Long infoid;
    private String sistema;
    private String 	descripcion;
    private String motivo;
    private String ot;
    private String horometro;
    private String evento;
    private String fecha;
    private String observacion;
    private Long asignacion_id;


    public Long getInfoid() {
        return infoid;
    }

    public void setInfoid(Long infoid) {
        this.infoid = infoid;
    }

    public String getSistema() {
        return sistema;
    }

    public void setSistema(String sistema) {
        this.sistema = sistema;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getOt() {
        return ot;
    }

    public void setOt(String ot) {
        this.ot = ot;
    }

    public String getHorometro() {
        return horometro;
    }

    public void setHorometro(String horometro) {
        this.horometro = horometro;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Long getAsignacion_id() {
        return asignacion_id;
    }

    public void setAsignacion_id(Long asignacion_id) {
        this.asignacion_id = asignacion_id;
    }
}
