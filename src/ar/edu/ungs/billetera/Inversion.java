package ar.edu.ungs.billetera;

import java.time.LocalDate;

public abstract class Inversion extends Actividad {
    protected LocalDate fechaConst;
    protected int plazoEnDias;
    protected double monto;
    protected String tipoInversion;
    protected boolean estaActiva;

    public Inversion(LocalDate fechaOperacion, String dniUsuarioOperador, String cvuCuentaEmisora, boolean aprobada, LocalDate fechaConst, int plazoEnDias, double monto, String tipoInversion, boolean estaActiva) {
        super(fechaOperacion, dniUsuarioOperador, cvuCuentaEmisora, aprobada);
        this.fechaConst = fechaConst;
        this.plazoEnDias = plazoEnDias;
        this.monto = monto;
        this.tipoInversion = tipoInversion;
        this.estaActiva = estaActiva;
    }

    public abstract double calcularRendimiento();

    public abstract double calcularMontoLiquidacion(long diasTranscurridos, boolean esPrecancelado);

    public Double precancelar() {
        if (!estaActiva) {
        	throw new RuntimeException("La inversión no está activa");
        }

        long diasPasados = Utilitarios.hoy().toEpochDay() - fechaConst.toEpochDay();
        
        diasPasados = Math.max(0, diasPasados);

        double montoADevolver = calcularMontoLiquidacion(diasPasados, true);
        this.estaActiva = false;
        return montoADevolver;
    }

    @Override
    public String devolverInfo() {
        return "○ Inversion:\n" +
                "■ fecha: " + fechaOperacion + "\n" +
                "origen: " + dniUsuarioOperador + " (" + cvuCuentaEmisora + ")\n" +
                "desc: " + tipoInversion + "\n" +
                "monto: " + monto + "\n" +
                "plazo: " + plazoEnDias + "\n" +
                "[" + (aprobada ? "Aprobado" : "Rechazado") + "]";
    }

    // getterss
    public LocalDate getFechaConst() { 
    	return fechaConst; 
    	}
    
    public int getPlazoEnDias() { 
    	return plazoEnDias; 
    	}
    
    public double getMonto() { 
    	return monto; 
    	}

}