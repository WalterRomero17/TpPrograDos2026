package ar.edu.ungs.billetera;

public class CuentaRegular extends Cuenta{
    //Usamos esta manera, ya que creemos que es la mejor solución, dentro de las limitaciones de los visto en la materia.
    //En otro contexto, para que sea escalable, usariamos un archivo de propiedades o un llamado a la base de datos.
    private final double limiteCuenta = 5000000.0;

    public CuentaRegular(String cvu, String alias) {
        super(cvu, alias);
    }

    @Override
    public void ingresarDinero(Double monto) {
        double nuevoMontoTotal = super.getSaldoTotal() + monto;
        if(nuevoMontoTotal > limiteCuenta){
            throw new IllegalStateException("No se puede ingresar dinero. Se llego al limite de la cuenta");
        }

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
    public String obtenerInfoCuenta() {
        StringBuilder sb = new StringBuilder("Regular: ");
        sb.append(super.getAlias()).append(" (");
        sb.append(super.getCvu()).append(")");
        return sb.toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CuentaRegular{");
        sb.append("limiteCuenta=").append(limiteCuenta);
        sb.append(", ").append(super.toString());
        sb.append('}');
        return sb.toString();
    }
}
