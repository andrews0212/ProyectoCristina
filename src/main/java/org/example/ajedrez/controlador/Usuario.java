package org.example.ajedrez.controlador;

public class Usuario {
   private String usuario;
    private String password;
    private String correo;
    public Usuario(String usuario, String password, String correo) {
        this.usuario = usuario;
        this.password = password;
        this.correo = correo;

    }
   public Usuario() {}

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
