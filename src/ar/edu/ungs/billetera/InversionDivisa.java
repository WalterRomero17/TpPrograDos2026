package ar.edu.ungs.billetera;

import java.time.LocalDate;

public class InversionDivisa extends Inversion {
    private String divisaReferencia;
    private double tasaInteres;
    private double cotizacionInicial;

    public InversionDivisa(LocalDate fechaOperacion, String dniUsuarioOperador, String cvuCuentaEmisora, boolean aprobada, LocalDate fechaConst, int plazoEnDias, double monto, String tipoInversion, boolean estaActiva, String divisaReferencia, double tasaInteres, double cotizacionInicial) {
        super(fechaOperacion, dniUsuarioOperador, cvuCuentaEmisora, aprobada, fechaConst, plazoEnDias, monto, tipoInversion, estaActiva);
        this.divisaReferencia = divisaReferencia;
        this.tasaInteres = tasaInteres;
        this.cotizacionInicial = cotizacionInicial;
    }

    @Override
    public double calcularMontoLiquidacion(long diasTranscurridos, boolean esPrecancelado) {
        double divisasEquivalente = getMonto() / cotizacionInicial;
        double interesesEnDivisas = divisasEquivalente * (tasaInteres / 365.0) * diasTranscurridos;
        
        if (esPrecancelado) interesesEnDivisas /= 2.0; // Penalización que se menciona en el enunciado
        
        double cotizacionActual = Utilitarios.consultarCotizacion(divisaReferencia);
        return (divisasEquivalente + interesesEnDivisas) * cotizacionActual;
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
        sb.append(" | Inversión Divisa (").append(divisaReferencia).append(") - Monto: $").append(getMonto());
        sb.append(" | Cotización de Compra: $").append(cotizacionInicial);
        if (getEstado()) {
            sb.append(" | Estado: Activo");
        } else {
            sb.append(" | Estado: Finalizado/Precancelado");
        }
        return sb.toString();
    }
}