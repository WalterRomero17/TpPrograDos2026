package ar.edu.ungs.billetera;

public class Transferencia extends Actividad {
    private Cuenta cuentaEmisora;
    private Cuenta cuentaReceptora;
    private double monto;

    public Transferencia(Usuario usuarioOperador, Cuenta cuentaEmisora, Cuenta cuentaReceptora, double monto) {
        super(usuarioOperador); // La fecha se pone sola en la clase padre
        this.cuentaEmisora = cuentaEmisora;
        this.cuentaReceptora = cuentaReceptora;
        this.monto = monto;
    }

    public boolean ejecutarTransferencia() {
        if (monto <= 0) return false;

        if (cuentaEmisora.getSaldo() >= monto) {
            cuentaEmisora.extraer(monto);
            cuentaReceptora.depositar(monto);
            return true;
        }
        return false;
    }

    //getters
    public Cuenta getCuentaEmisora() { 
    	return cuentaEmisora; 
    	}
    public Cuenta getCuentaReceptora() { 
    	return cuentaReceptora; 
    }
    public double getMonto() { 
    	return monto; 
    	}
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fecha: ").append(fechaOperacion);
        sb.append(" | Operador: ").append(usuarioOperador.getNombre());
        sb.append(" | Transferencia Bancaria - Monto: $").append(monto);
        sb.append(" | Origen CVU: ").append(cuentaEmisora.getCvu());
        sb.append(" | Destino CVU: ").append(cuentaReceptora.getCvu());
        return sb.toString();
    }

}