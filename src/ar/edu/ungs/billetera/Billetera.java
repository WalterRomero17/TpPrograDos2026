package ar.edu.ungs.billetera;

import java.util.Collections;
import java.util.List;

public class Billetera implements IBilletera{

    @Override
    public void registrarEmpresa(String cuit, String nombreFantasia, String telefono, String email, String nombreContacto) {

    }

    @Override
    public void agregarPersonaAutorizada(String cuitEmpresa, String dniAutorizado) {

    }

    @Override
    public void registrarUsuario(String dni, String nombre, String telefono, String email) {

    }

    @Override
    public String crearCuentaRegular(String dniUsuario, String alias) {
        return "";
    }

    @Override
    public String crearCuentaPremium(String dniUsuario, String alias, double depositoInicial) {
        return "";
    }

    @Override
    public String crearCuentaCorporativa(String dniUsuario, String alias, String cuitEmpresa) {
        return "";
    }

    @Override
    public List<String> obtenerCuentas(String dniUsuario) {
        return Collections.emptyList();
    }

    @Override
    public double obtenerSaldoDisponible(String cvu) {
        return 0;
    }

    @Override
    public void realizarTransferencia(String cvuOrigen, String cvuDestino, double monto) {

    }

    @Override
    public int realizarInversionRentaFija(String dni, String cvu, double monto, int plazoDias) {
        return 0;
    }

    @Override
    public int realizarInversionDivisa(String dni, String cvu, double monto, int plazoDias, String divisa, double tasa) {
        return 0;
    }

    @Override
    public int realizarInversionLiquidez(String dni, String cvu, double monto, int plazoDias) {
        return 0;
    }

    @Override
    public void precancelarInversion(String dni, String cvu, int idInversion) {

    }

    @Override
    public String consultarCvu(String alias) {
        return "";
    }

    @Override
    public List<String> consultarHistorialGlobal() {
        return Collections.emptyList();
    }

    @Override
    public List<String> consultarHistorialCuenta(String cvu) {
        return Collections.emptyList();
    }

    @Override
    public List<String> consultarHistorialUsuario(String dniUsuario) {
        return Collections.emptyList();
    }

    @Override
    public double obtenerTotalInvertido(String dniUsuario) {
        return 0;
    }

    @Override
    public List<String> cuentasConMayorVolumen(int cantidadTop) {
        return Collections.emptyList();
    }
}
