package ar.edu.ungs.billetera;

import java.time.LocalDate;

public abstract class Inversion extends Actividad {
    private LocalDate fechaConst;
    private int plazoEnDias;
    private double monto;
    private String tipoInversion;
    private boolean estaActiva;

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

    public boolean admitePrecancelacion() {
        return true;
    }

    public Double precancelar() {

        if (!admitePrecancelacion()){
            throw new IllegalArgumentException("La inversión no admite precancelación");
        }

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
    	return this.plazoEnDias; 
    	}
    
    public double getMonto() { 
    	return this.monto; 
    	}
    
    public boolean getEstado() { 
    	return this.estaActiva; 
    	}

}