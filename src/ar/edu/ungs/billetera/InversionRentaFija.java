package ar.edu.ungs.billetera;

public class InversionRentaFija extends Inversion {
    private double tasaInteres;

    public InversionRentaFija(Usuario usuarioOperador, int plazoEnDias, double monto, 
                              Cuenta cuentaAsociada, double tasaInteres) {
        super(usuarioOperador, plazoEnDias, monto, cuentaAsociada);
        this.tasaInteres = tasaInteres;
    }

    @Override
    public double calcularMontoLiquidacion(long diasTranscurridos, boolean esPrecancelado) {
        double interesAcumulado = monto * (tasaInteres / 365.0) * diasTranscurridos;
        if (esPrecancelado) {
            interesAcumulado /= 2.0; // Penalización que pide el test
        }
        return monto + interesAcumulado;
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
        sb.append(" | Plazo Fijo Tradicional - Monto: $").append(monto);
        sb.append(" | TNA: ").append(tasaInteres * 100).append("%");
        if (estaActiva) {
            sb.append(" | Estado: Activo");
        } else {
            sb.append(" | Estado: Finalizado/Precancelado");
        }
        return sb.toString();
    }
}