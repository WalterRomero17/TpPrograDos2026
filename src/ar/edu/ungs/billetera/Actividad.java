package ar.edu.ungs.billetera;

import java.time.LocalDate;

public abstract class Actividad {
    protected LocalDate fechaOperacion;
    protected Usuario usuarioOperador;

    public Actividad(Usuario usuarioOperador) {
        this.fechaOperacion = Utilitarios.hoy();
        this.usuarioOperador = usuarioOperador;
    }

    public LocalDate getFechaOperacion() {
        return fechaOperacion;
    }

    public Usuario getUsuarioOperador() {
        return usuarioOperador;
    }

    @Override
    public String toString() {
        return "Fecha: " + fechaOperacion + " | Operador: " + usuarioOperador.getNombre();
    }
}