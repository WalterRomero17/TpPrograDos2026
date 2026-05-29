package ar.edu.ungs.billetera;

import java.time.LocalDate;

public class Transferencia extends Actividad {
    private String cvuCuentaReceptora;
    private String dniDestino;
    private double monto;

    public Transferencia(LocalDate fechaOperacion, String dniUsuarioOperador, String cvuCuentaEmisora, boolean aprobada, String cvuCuentaReceptora, String dniDestino, double monto) {
        super(fechaOperacion, dniUsuarioOperador, cvuCuentaEmisora, aprobada);
        this.cvuCuentaReceptora = cvuCuentaReceptora;
        this.dniDestino = dniDestino;
        this.monto = monto;
    }

    @Override
    public String devolverInfo() {
        return "○ Transferencia:\n" +
                "■ fecha: " + fechaOperacion +
                " origen: " + dniUsuarioOperador + " (" + cvuCuentaEmisora + ")" +
                " destino: " + dniDestino + " (" + cvuCuentaReceptora + ")" +
                " monto: " + monto +
                " [" + (aprobada ? "Aprobado" : "Rechazado") + "]";
    }
    
    //getters
    public double getMonto() { 
    	return monto; 
    	}
    
    public String getCvuCuentaReceptora() {
        return cvuCuentaReceptora;
    }
    
    public String getDniDestino() {
    	return dniDestino;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Transferencia{");
        sb.append("cvuCuentaEmisora='").append(cvuCuentaEmisora).append('\'');
        sb.append(", cvuCuentaReceptora='").append(cvuCuentaReceptora).append('\'');
        sb.append(", monto=").append(monto);
        sb.append(", fechaOperacion=").append(fechaOperacion);
        sb.append(", dniUsuarioOperador='").append(dniUsuarioOperador).append('\'');
        sb.append('}');
        return sb.toString();
    }
}