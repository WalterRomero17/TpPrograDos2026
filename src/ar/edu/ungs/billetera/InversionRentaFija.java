package ar.edu.ungs.billetera;

import java.time.LocalDate;

public class InversionRentaFija extends Inversion {
    private double tasaInteres;

    public InversionRentaFija(LocalDate fechaOperacion, String dniUsuarioOperador, String cvuCuentaEmisora, boolean aprobada, LocalDate fechaConst, int plazoEnDias, double monto, String tipoInversion, boolean estaActiva, double tasaInteres) {
        super(fechaOperacion, dniUsuarioOperador, cvuCuentaEmisora, aprobada, fechaConst, plazoEnDias, monto, tipoInversion, estaActiva);
        this.tasaInteres = tasaInteres;
        this.fechaConst = Utilitarios.hoy();
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
        sb.append(" | Operador: ").append(dniUsuarioOperador);
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