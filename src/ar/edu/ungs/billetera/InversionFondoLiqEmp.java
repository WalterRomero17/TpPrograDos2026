package ar.edu.ungs.billetera;

public class InversionFondoLiqEmp extends Inversion {
    private double tasaInteres;
    private double cotizacionInicial;

    public InversionFondoLiqEmp(Usuario usuarioOperador, int plazoEnDias, double monto, 
                                Cuenta cuentaAsociada, double tasaInteres) {
        super(usuarioOperador, plazoEnDias, monto, cuentaAsociada);
        this.tasaInteres = tasaInteres;
        // Consulta la cuotaparte del fondo
        this.cotizacionInicial = Utilitarios.consultarCotizacion("FLE");
    }

    @Override
    public double calcularMontoLiquidacion(long diasTranscurridos, boolean esPrecancelado) {
        double cuotapartesEquivalentes = monto / cotizacionInicial;
        double interesesFondo = cuotapartesEquivalentes * (tasaInteres / 365.0) * diasTranscurridos;
        
        if (esPrecancelado) interesesFondo /= 2.0;
        
        double cotizacionActual = Utilitarios.consultarCotizacion("FLE");
        return (cuotapartesEquivalentes + interesesFondo) * cotizacionActual;
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
        sb.append(" | Fondo Empresarial (FLE) - Monto: $").append(monto);
        sb.append(" | Valor Inicial: $").append(cotizacionInicial);
        if (estaActiva) {
            sb.append(" | Estado: Activo");
        } else {
            sb.append(" | Estado: Finalizado/Precancelado");
        }
        return sb.toString();
    }
}