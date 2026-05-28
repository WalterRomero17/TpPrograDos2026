package ar.edu.ungs.billetera;

public abstract class Cuenta {
    private String cvu;
    private String alias;
    private Double saldoDisponible;
    private Double saldoInvertido;

    public Cuenta(String cvu, String alias) {
        this.alias = alias;
        this.cvu = cvu;
    }

    abstract void ingresarDinero(Double monto);
    abstract void retirarDinero(Double monto);
    abstract void invertir(Double monto);

    public Double getSaldoDisponible() {
        return this.saldoDisponible;
    }

    public void setSaldoDisponible(Double saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    public Double getSaldoInvertido() {
        return this.saldoInvertido;
    }

    public Double getSaldoTotal(){
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
