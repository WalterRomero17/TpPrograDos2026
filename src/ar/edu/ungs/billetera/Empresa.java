package ar.edu.ungs.billetera;

import java.util.ArrayList;
import java.util.List;

public class Empresa {

    private String cuit;
    private String nombreFantasia;
    private String telefono;
    private String email;
    private String nombreContacto;
    private List<String> dniPersonasAutorizadas = new ArrayList<>();


    public Empresa(String cuit, String nombreFantasia, String telefono, String email, String nombreContacto) {
        this.cuit = cuit;
        this.nombreFantasia = nombreFantasia;
        this.telefono = telefono;
        this.email = email;
        this.nombreContacto = nombreContacto;
    }

    public void agregarPersonaAutorizada(String dniAutorizado) {
        if (dniPersonasAutorizadas.contains(dniAutorizado)){
            throw new RuntimeException("DNI duplicado");
        }
        this.dniPersonasAutorizadas.add(dniAutorizado);
    }

    public Boolean esPersonaAutorizada(String dni) {
        return this.dniPersonasAutorizadas.contains(dni);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Empresa{");
        sb.append("cuit='").append(cuit).append('\'');
        sb.append(", nombreFantasia='").append(nombreFantasia).append('\'');
        sb.append(", telefono='").append(telefono).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", nombreContacto='").append(nombreContacto).append('\'');
        sb.append(", dniPersonasAutorizadas=").append(dniPersonasAutorizadas);
        sb.append('}');
        return sb.toString();
    }
}
