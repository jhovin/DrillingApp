package pe.bonifacio.drillingapp.models;

public class Usuario {

    private Long id;
    private String nombre;
    private String cargo;
    private String email;
    private String dni;
    private String password;


    public Usuario() {

    }

    public Usuario(Long id, String nombre,String dni ,String cargo, String email) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.cargo = cargo;
        this.email = email;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
