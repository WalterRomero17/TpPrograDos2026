package ar.edu.ungs.billetera;

import java.util.*;

public class Billetera implements IBilletera{

    HashMap<String, Usuario> usuarios = new HashMap<>();
    //HashMap<String, Actividades> cuentas;
    HashMap<String, Empresa> empresas = new HashMap<>();

    private final String codigoInicioCvu = "00000031" ;

    @Override
    public void registrarEmpresa(String cuit, String nombreFantasia, String telefono, String email, String nombreContacto) {
        if (empresas.containsKey(cuit)) {
            throw new RuntimeException("Empresa duplicada");
        }
        empresas.put(cuit, new Empresa(cuit, nombreFantasia, telefono, email, nombreContacto));
    }

    @Override
    public void agregarPersonaAutorizada(String cuitEmpresa, String dniAutorizado) {
        if (!empresas.containsKey(cuitEmpresa)) {
            throw new RuntimeException("Empresa no encontrada");
        }
        empresas.get(cuitEmpresa).agregarPersonaAutorizada(dniAutorizado);
    }

    @Override
    public void registrarUsuario(String dni, String nombre, String telefono, String email) {
        if (usuarios.containsKey(dni)) {
            throw new RuntimeException("Usuario duplicado");
        }
        usuarios.put(dni, new Usuario(dni, nombre, telefono, email));
    }


    @Override
    public String crearCuentaRegular(String dniUsuario, String alias) {
        if (!usuarios.containsKey(dniUsuario)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        Cuenta cuenta = new CuentaRegular(generarCvu(), alias);
        usuarios.get(dniUsuario).agregarCuenta(cuenta);

        return cuenta.getCvu();
    }

    @Override
    public String crearCuentaPremium(String dniUsuario, String alias, double depositoInicial) {
        if (!usuarios.containsKey(dniUsuario)) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Cuenta cuenta = new CuentaPremium(generarCvu(), alias,depositoInicial);
        usuarios.get(dniUsuario).agregarCuenta(cuenta);

        return cuenta.getCvu();
    }

    @Override
    public String crearCuentaCorporativa(String dniUsuario, String alias, String cuitEmpresa) {
        if (!usuarios.containsKey(dniUsuario)) {
            throw new RuntimeException("Usuario no encontrado");
        }

        if (!empresas.containsKey(cuitEmpresa)) {
            throw new RuntimeException("Empresa no encontrada");
        }

        if (!empresas.get(cuitEmpresa).esPersonaAutorizada(dniUsuario)) {
            throw new RuntimeException("El usuario no es autorizado para crear una cuenta corporativa");
        }

        Cuenta cuenta = new CuentaCorporativa(generarCvu(), alias,cuitEmpresa);
        usuarios.get(dniUsuario).agregarCuenta(cuenta);

        return cuenta.getCvu();
    }

    @Override
    public List<String> obtenerCuentas(String dniUsuario) {
        if (!usuarios.containsKey(dniUsuario)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        return usuarios.get(dniUsuario).obtenerCvuCuentas();
    }

    @Override
    public double obtenerSaldoDisponible(String cvu) {
        for(Usuario usuario : usuarios.values()){
            Cuenta cuenta = usuario.getCuenta(cvu);
            if(cuenta != null) {
                return cuenta.getSaldoDisponible();
            }
        }
        throw new RuntimeException("Cuenta no encontrado");
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
        if (!usuarios.containsKey(dniUsuario)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        return usuarios.get(dniUsuario).getTotalInvertido();
    }

    @Override
    public List<String> cuentasConMayorVolumen(int cantidadTop) {
        return Collections.emptyList();
    }

    private String generarCvu() {
        Random random = new Random();
        StringBuilder cvu = new StringBuilder(codigoInicioCvu);

        // Generar los 14 dígitos restantes de la cuenta
        for (int i = 0; i < 14; i++) {
            cvu.append(random.nextInt(10));
        }

        return cvu.toString();
    }


}
