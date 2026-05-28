package ar.edu.ungs.billetera;

public class CuentaCorporativa extends Cuenta {

    private String cuitEmpresa;

    public CuentaCorporativa(String cvu, String alias, String cuitEmpresa) {
        super(cvu, alias);
        this.cuitEmpresa = cuitEmpresa;
    }

    @Override
    void ingresarDinero(Double monto) {
        super.setSaldoDisponible(super.getSaldoDisponible() + monto);
    }

    @Override
    public void retirarDinero(Double monto) {
        double nuevoMontoTotal = super.getSaldoDisponible() - monto;
        if(nuevoMontoTotal < 0.0){
            throw new RuntimeException("No se puede retirar dinero. La cuenta no tiene saldo disponible");
        }
        super.setSaldoDisponible(nuevoMontoTotal);
    }

    @Override
    void invertir(Double monto) {

    }
}
