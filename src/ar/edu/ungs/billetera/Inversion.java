package ar.edu.ungs.billetera;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class Inversion extends Actividad {
    protected LocalDate fechaConst;
    protected int plazoEnDias;
    protected double monto;
    protected boolean estaActiva;
    protected Cuenta cuentaAsociada;

    public Inversion(Usuario usuarioOperador, int plazoEnDias, double monto, Cuenta cuentaAsociada) {
        super(usuarioOperador);
        // Automáticamente arranca "hoy" según la simulación
        this.fechaConst = Utilitarios.hoy(); 
        this.plazoEnDias = plazoEnDias;
        this.monto = monto;
        this.cuentaAsociada = cuentaAsociada;
        this.estaActiva = true;
    }

    public abstract double calcularRendimiento();

    public abstract double calcularMontoLiquidacion(long diasTranscurridos, boolean esPrecancelado);

    public boolean precancelar() {
        if (!estaActiva) return false;

        long diasPasados = Utilitarios.hoy().toEpochDay() - fechaConst.toEpochDay();
        
        diasPasados = Math.max(0, diasPasados);

        double montoADevolver = calcularMontoLiquidacion(diasPasados, true);
        cuentaAsociada.depositar(montoADevolver);
        this.estaActiva = false;
        return true;
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
    
    public Cuenta getCuentaAsociada() { 
    	return cuentaAsociada; 
    	}
}