package ar.edu.ungs.billetera;

public class CuentaPremium extends Cuenta{
    private final double minimoEnCuenta = 500000.0;

    public CuentaPremium(String cvu, String alias,double depositoInicial) {
        super(cvu, alias);
        if(depositoInicial < minimoEnCuenta){
            throw new IllegalArgumentException("El deposito inicial debe ser mayor a " + minimoEnCuenta);
        }
        super.setSaldoDisponible(depositoInicial);
    }

    @Override
    void ingresarDinero(Double monto) {
        super.setSaldoDisponible(super.getSaldoDisponible() + monto);
    }

    @Override
    void retirarDinero(Double monto) {
        double nuevoMonto = super.getSaldoDisponible() - monto;
        if(nuevoMonto < 0.0){
            throw new RuntimeException("No se puede retirar dinero. La cuenta no tiene saldo disponible");
        }

        if(super.getSaldoTotal() - monto < minimoEnCuenta){
            throw new RuntimeException("No se puede retirar dinero. La cuenta debe tener un total minimo de : " + minimoEnCuenta);
        }

        super.setSaldoDisponible(nuevoMonto);
    }

    @Override
    void invertir(Double monto) {

    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CuentaPremium{");
        sb.append("minimoEnCuenta=").append(minimoEnCuenta);
        sb.append(", ").append(super.toString());
        sb.append('}');
        return sb.toString();
    }
}
