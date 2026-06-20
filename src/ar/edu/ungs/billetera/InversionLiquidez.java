package ar.edu.ungs.billetera;

import java.time.LocalDate;

public class InversionLiquidez extends Inversion {
    private double tasaInteres;
    private double cotizacionInicial;

    public InversionLiquidez(LocalDate fechaOperacion, String dniUsuarioOperador, String cvuCuentaEmisora, boolean aprobada, LocalDate fechaConst, int plazoEnDias, double monto, String tipoInversion, boolean estaActiva, double tasaInteres, String Cotizacion) {
        super(fechaOperacion, dniUsuarioOperador, cvuCuentaEmisora, aprobada, fechaConst, plazoEnDias, monto, tipoInversion, estaActiva);
        this.tasaInteres = tasaInteres;
        this.cotizacionInicial = Utilitarios.consultarCotizacion(Cotizacion);
    }

    @Override
    public double calcularMontoLiquidacion(long diasTranscurridos, boolean esPrecancelado) {
        double cuotapartesEquivalentes = getMonto() / cotizacionInicial;
        double interesesFondo = cuotapartesEquivalentes * (tasaInteres / 365.0) * diasTranscurridos;
        
        if (esPrecancelado) { 
        	interesesFondo /= 2.0;
        }
        
        double cotizacionActual = Utilitarios.consultarCotizacion("FLE");
        return (cuotapartesEquivalentes + interesesFondo) * cotizacionActual;
    }

    @Override
    public double calcularRendimiento() {
        return calcularMontoLiquidacion(getPlazoEnDias(), false) - getMonto();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fecha: ").append(fechaOperacion);
        sb.append(" | Operador: ").append(dniUsuarioOperador);
        sb.append(" | Fondo Empresarial (FLE) - Monto: $").append(getMonto());
        sb.append(" | Valor Inicial: $").append(cotizacionInicial);
        if (getEstado()) {
            sb.append(" | Estado: Activo");
        } else {
            sb.append(" | Estado: Finalizado/Precancelado");
        }
        return sb.toString();
    }
}