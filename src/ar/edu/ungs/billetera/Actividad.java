package ar.edu.ungs.billetera;

import java.time.LocalDate;
import java.util.Random;

public abstract class Actividad {
    protected LocalDate fechaOperacion;
    protected String dniUsuarioOperador;
    protected String cvuCuentaEmisora;
    protected boolean aprobada;
    protected int id;

    public Actividad(LocalDate fechaOperacion, String dniUsuarioOperador, String cvuCuentaEmisora, boolean aprobada) {
        this.fechaOperacion = fechaOperacion;
        this.dniUsuarioOperador = dniUsuarioOperador;
        this.cvuCuentaEmisora = cvuCuentaEmisora;
        this.aprobada = aprobada;
        this.id = crearId();
    }

    private int crearId(){
        Random random = new Random();

        return random.nextInt(1000000000);
    }

    // Consideramos esta manera es correcta, dentro de lo requerido en el enunciado.
    // Esto es porque si usaramos el toString() de la superclase, los toString() de las subclases
    // no se mostrarían correctamente para lo que el enunciado requiere.
    abstract String devolverInfo();

    public int getId() {
        return id;
    }

    public String getCvuCuentaEmisora() {
        return cvuCuentaEmisora;
    }

    public String getDniUsuarioOperador() {
        return dniUsuarioOperador;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Actividad{");
        sb.append("fechaOperacion=").append(fechaOperacion);
        sb.append(", dniUsuarioOperador='").append(dniUsuarioOperador).append('\'');
        sb.append('}');
        return sb.toString();
    }
}