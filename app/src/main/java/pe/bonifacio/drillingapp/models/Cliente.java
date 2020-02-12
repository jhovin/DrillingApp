package pe.bonifacio.drillingapp.models;

public class Cliente {

    private Long clienid;
    private String nombre;
    private String gerente;
    private String telefono;



    public Cliente() {

    }

    public Long getClienid() {
        return clienid;
    }

    public void setClienid(Long clienid) {
        this.clienid = clienid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGerente() {
        return gerente;
    }

    public void setGerente(String gerente) {
        this.gerente = gerente;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
