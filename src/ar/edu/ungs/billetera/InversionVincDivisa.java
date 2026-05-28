package ar.edu.ungs.billetera;

public class InversionVincDivisa extends Inversion {
    private String divisaReferencia;
    private double tasaInteres;
    private double cotizacionInicial;

    public InversionVincDivisa(Usuario usuarioOperador, int plazoEnDias, double monto, 
                               Cuenta cuentaAsociada, String divisaReferencia, double tasaInteres) {
        super(usuarioOperador, plazoEnDias, monto, cuentaAsociada);
        this.divisaReferencia = divisaReferencia;
        this.tasaInteres = tasaInteres;
        this.cotizacionInicial = Utilitarios.consultarCotizacion(divisaReferencia);
    }

    @Override
    public double calcularMontoLiquidacion(long diasTranscurridos, boolean esPrecancelado) {
        double divisasEquivalente = monto / cotizacionInicial;
        double interesesEnDivisas = divisasEquivalente * (tasaInteres / 365.0) * diasTranscurridos;
        
        if (esPrecancelado) interesesEnDivisas /= 2.0;
        
        double cotizacionActual = Utilitarios.consultarCotizacion(divisaReferencia);
        return (divisasEquivalente + interesesEnDivisas) * cotizacionActual;
    }

    @Override
    public double calcularRendimiento() {
        return calcularMontoLiquidacion(plazoEnDias, false) - monto;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fecha: ").append(fechaOperacion);
        sb.append(" | Operador: ").append(usuarioOperador.getNombre());
        sb.append(" | Inversión Divisa (").append(divisaReferencia).append(") - Monto: $").append(monto);
        sb.append(" | Cotización de Compra: $").append(cotizacionInicial);
        if (estaActiva) {
            sb.append(" | Estado: Activo");
        } else {
            sb.append(" | Estado: Finalizado/Precancelado");
        }
        return sb.toString();
    }
}