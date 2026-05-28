package ar.edu.ungs.billetera;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Usuario {
    private String dni;
    private String nombre;
    private String telefono;
    private String email;
    private Double totalInvertido = 0.0;
    private HashMap<String, Cuenta> cuentas = new HashMap<>();

    public Usuario(String dni, String nombre, String telefono, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    public void agregarCuenta(Cuenta cuenta) {
        this.cuentas.put(cuenta.getCvu(), cuenta);
    }

    public Cuenta getCuenta(String cvu) {
        return this.cuentas.get(cvu);
    }

    public List<String> obtenerCvuCuentas() {
        return new ArrayList<>(this.cuentas.keySet());
    }
    public List<Cuenta> obtenerCuentas() {
        return new ArrayList<>(this.cuentas.values());
    }

    public void recalcularTotalInvertido() {
        Double sumaTotal = 0.0;
        for(Cuenta cuenta : this.cuentas.values()){
            sumaTotal += cuenta.getSaldoInvertido();
        }
        this.totalInvertido = sumaTotal;
    }

    public Double getTotalInvertido() {
        return this.totalInvertido;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Usuario{");
        sb.append("dni='").append(dni).append('\'');
        sb.append(", nombre='").append(nombre).append('\'');
        sb.append(", telefono='").append(telefono).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", totalInvertido=").append(totalInvertido);
        sb.append(", cuentas=").append(cuentas);
        sb.append('}');
        return sb.toString();
    }
}
