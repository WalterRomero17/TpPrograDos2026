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
            throw new IllegalStateException("No se puede retirar dinero. La cuenta no tiene saldo disponible");
        }
        super.setSaldoDisponible(nuevoMontoTotal);
    }
    
    public String getCuitEmpresa() {
        return cuitEmpresa;
    }

    @Override
    String obtenerInfoCuenta() {
        StringBuilder sb = new StringBuilder("Corporativo: ");
        sb.append(super.getAlias()).append(" (");
        sb.append(super.getCvu()).append(")");
        return sb.toString();
    }
}
