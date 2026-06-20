package ar.edu.ungs.billetera;

import java.time.LocalDate;

public class InversionLiquidez extends Inversion {
    private double tasaInteres;
    private double cotizacionInicial;
    private String activo;
    private final double minimoDeInversion = 2000000.0; // misma justificación que en CuentaPremium y CuentaRegular

    public InversionLiquidez(LocalDate fechaOperacion, String dniUsuarioOperador, String cvuCuentaEmisora, boolean aprobada, LocalDate fechaConst, int plazoEnDias, double monto, String tipoInversion, boolean estaActiva, double tasaInteres, String activo) {
        super(fechaOperacion, dniUsuarioOperador, cvuCuentaEmisora, aprobada, fechaConst, plazoEnDias, monto, tipoInversion, estaActiva);
        if(monto < minimoDeInversion){
            throw new IllegalArgumentException("El monto de la inversión debe ser mayor o igual a " + minimoDeInversion);
        }
        this.tasaInteres = tasaInteres;
        this.cotizacionInicial = Utilitarios.consultarCotizacion(activo);
        this.activo = activo;
    }

    @Override
    public double calcularMontoLiquidacion(long diasTranscurridos, boolean esPrecancelado) {
        if (esPrecancelado){
            throw new IllegalArgumentException("La inversion de este tipo no puede ser precancelada");
        }

        double cuotapartesEquivalentes = getMonto() / cotizacionInicial;
        double interesesFondo = cuotapartesEquivalentes * (tasaInteres / 365.0) * diasTranscurridos;
        
        double cotizacionActual = Utilitarios.consultarCotizacion(activo);
        return (cuotapartesEquivalentes + interesesFondo) * cotizacionActual;
    }

    @Override
    public boolean admitePrecancelacion() {
        return false;
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