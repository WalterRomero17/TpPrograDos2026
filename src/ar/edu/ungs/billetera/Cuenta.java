package ar.edu.ungs.billetera;

public abstract class Cuenta {
    private String cvu;
    private String alias;
    private double saldoDisponible = 0.0;
    private double saldoInvertido = 0.0;

    public Cuenta(String cvu, String alias) {
        this.alias = alias;
        this.cvu = cvu;
    }

    abstract void ingresarDinero(Double monto);
    abstract void retirarDinero(Double monto);
    abstract String obtenerInfoCuenta();

    public void invertir(double monto){
    	if (this.saldoDisponible < monto) {
            throw new IllegalStateException("Saldo insuficiente para invertir");
        }
        this.saldoDisponible -= monto;
        this.saldoInvertido += monto;
    };
    
    public void finalizarInversion(Double capitalInvertido, Double montoTotalADevolver){
        this.saldoInvertido -= capitalInvertido;     
        this.saldoDisponible += montoTotalADevolver;   // ahora si rey le sumamos al bolsillo el capital + las ganancias
    };

    public double getSaldoDisponible() {
        return this.saldoDisponible;
    }

    public void setSaldoDisponible(Double saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    public double getSaldoInvertido() {
        return this.saldoInvertido;
    }

    public double getSaldoTotal(){
        return this.saldoDisponible + this.saldoInvertido;
    }

    public String getCvu() {
        return cvu;
    }

    public String getAlias() {
        return alias;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cuenta{");
        sb.append("cvu='").append(cvu).append('\'');
        sb.append(", alias='").append(alias).append('\'');
        sb.append(", saldoDisponible=").append(saldoDisponible);
        sb.append(", saldoInvertido=").append(saldoInvertido);
        sb.append('}');
        return sb.toString();
    }
}
