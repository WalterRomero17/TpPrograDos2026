package ar.edu.ungs.billetera;

import java.time.LocalDate;
import java.util.*;

public class Billetera implements IBilletera{

    HashMap<String, Usuario> usuarios = new HashMap<>();
    HashMap<Integer, Actividad> actividades = new HashMap<>();
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
        return usuarios.get(dniUsuario).obtenerInfoCuentas();
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
        Cuenta cuentaOrigen = null;
        Cuenta cuentaDestino = null;
        String dniUsuario = null;
        String dniDestino = null;
        
        for(Usuario usuario : usuarios.values()){
            Cuenta cuentaOrigenAux = usuario.getCuenta(cvuOrigen);
            Cuenta cuentaDestinoAux = usuario.getCuenta(cvuDestino);
            if(cuentaOrigenAux != null) {
                cuentaOrigen = cuentaOrigenAux;
                dniUsuario = usuario.getDni();
            }
            if(cuentaDestinoAux != null) {
                cuentaDestino = cuentaDestinoAux;
                dniDestino = usuario.getDni();
            }
        }

        if(cuentaOrigen == null) {
            throw new RuntimeException("Cuenta de origen no encontrada");
        }

        if(cuentaDestino == null) {
            throw new RuntimeException("Cuenta de destino no encontrada");
        }

       
        //primero chequeamos si va a fallar. 
        // si va a fallar por saldo, creamos la actividad como RECHAZADA (false) y la guardamos 
        if (cuentaOrigen.getSaldoDisponible() < monto) {
            Actividad actividadFallida = new Transferencia(Utilitarios.hoy(), dniUsuario, cvuOrigen, false, cvuDestino, dniDestino, monto);
            actividades.put(actividadFallida.getId(), actividadFallida);
            
            // una vez guardada en el historial para que sume volumen, dejamos que la cuenta tire el error
            cuentaOrigen.retirarDinero(monto); 
        }

        // si tiene saldo, el "if" de arriba se ignora y pasa por acá de forma normal:
        cuentaOrigen.retirarDinero(monto);
        cuentaDestino.ingresarDinero(monto);

        // creamos y guardamos la transferencia exitosa (true)
        Actividad actividadOk = new Transferencia(Utilitarios.hoy(), dniUsuario, cvuOrigen, true, cvuDestino, dniDestino, monto);
        actividades.put(actividadOk.getId(), actividadOk);
    }

    @Override
    public int realizarInversionRentaFija(String dni, String cvu, double monto, int plazoDias) {
        if(!usuarios.containsKey(dni)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        Usuario usuario = usuarios.get(dni);
        Cuenta cuenta = usuario.getCuenta(cvu);
        if(cuenta == null) {
            throw new RuntimeException("Cuenta no encontrada");
        }

        cuenta.invertir(monto);
        usuario.recalcularTotalInvertido();

        Actividad actividad = new InversionRentaFija(Utilitarios.hoy(), dni,  cvu, true, Utilitarios.hoy(), plazoDias, monto, "Inversion Renta Fija", true, 0.0);
        actividades.put(actividad.getId(), actividad);
        return actividad.getId();
    }

    @Override
    public int realizarInversionDivisa(String dni, String cvu, double monto, int plazoDias, String divisa, double tasa) {
        if(!usuarios.containsKey(dni)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        Usuario usuario = usuarios.get(dni);
        Cuenta cuenta = usuario.getCuenta(cvu);
        if(cuenta == null) {
            throw new RuntimeException("Cuenta no encontrada");
        }

        cuenta.invertir(monto);

        Actividad actividad = new InversionVincDivisa(Utilitarios.hoy(), dni, cvu,true, LocalDate.now(), plazoDias, monto, "Inversion Vinculada a Divisa",  true, divisa, tasa, Utilitarios.consultarCotizacion(divisa));
        actividades.put(actividad.getId(), actividad);
        
        usuario.recalcularTotalInvertido();
        
        return actividad.getId();
    }

    @Override
    public int realizarInversionLiquidez(String dni, String cvu, double monto, int plazoDias) {
        if(!usuarios.containsKey(dni)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        Usuario usuario = usuarios.get(dni);
        Cuenta cuenta = usuario.getCuenta(cvu);
        if(cuenta == null) {
            throw new RuntimeException("Cuenta no encontrada");
        }

        cuenta.invertir(monto);

        Actividad actividad = new InversionFondoLiqEmp(Utilitarios.hoy(), dni, cvu,true, LocalDate.now(), plazoDias, monto, "Inversion Fondo Liquidez",  true, 0.08);
        actividades.put(actividad.getId(), actividad);
        usuario.recalcularTotalInvertido();
        return actividad.getId();
    }

    @Override
    public void precancelarInversion(String dni, String cvu, int idInversion) {
        if(!usuarios.containsKey(dni)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        Cuenta cuenta = usuarios.get(dni).getCuenta(cvu);
        if(cuenta == null) {
            throw new RuntimeException("Cuenta no encontrada");
        }

        Actividad actividad = actividades.get(idInversion);
        if(actividad == null || !(actividad instanceof Inversion)) {
            throw new IllegalArgumentException("Inversion no encontrada");
        }

        Inversion inversion = (Inversion) actividad;

        // 1. Ejecutamos la precancelación, que nos devuelve la plata final (Capital + Interés penalizado)
        Double montoADevolver = inversion.precancelar();

        // 2. Usamos el método arreglado de Cuenta pasándole AMBOS valores
        cuenta.finalizarInversion(inversion.getMonto(), montoADevolver);

        // 3. Recalculamos el total invertido del usuario porque acaba de sacar plata de sus inversiones
        usuarios.get(dni).recalcularTotalInvertido();
        actividades.put(idInversion, inversion);
    }

    @Override
    public String consultarCvu(String alias) {
    	for (Usuario usuario : usuarios.values()) {
            for (String cvu : usuario.obtenerCvuCuentas()) {
                Cuenta cuenta = usuario.getCuenta(cvu);
                
                if (cuenta != null && alias.equals(cuenta.getAlias())) {
                    return cuenta.getCvu();
                }
            }
        }
        
        throw new IllegalArgumentException("Cuenta no encontrada para el alias especificado: " + alias);
    }
    	

    @Override
    public List<String> consultarHistorialGlobal() {
        List<String> historial = new ArrayList<>();
        for(Actividad actividad : actividades.values()) {
            historial.add(actividad.devolverInfo());
        }
        return historial;
    }

    @Override
    public List<String> consultarHistorialCuenta(String cvu) {
        List<String> historial = new ArrayList<>();
        for(Actividad actividad : actividades.values()) {
        	// es emisor?
            boolean esEmisor = cvu.equals(actividad.getCvuCuentaEmisora());
            
            // es receptor?
            boolean esReceptor = false;
            if (actividad instanceof Transferencia) {
                Transferencia trans = (Transferencia) actividad;
                esReceptor = cvu.equals(trans.getCvuCuentaReceptora()); 
            }
            
            // si es alguno de los 2
            if (esEmisor || esReceptor) {
                historial.add(actividad.devolverInfo());
            }   
        }
        return historial;
    }

    @Override
    public List<String> consultarHistorialUsuario(String dniUsuario) {
        List<String> historial = new ArrayList<>();
        for(Actividad actividad : actividades.values()) {
        	boolean esEmisor = dniUsuario.equals(actividad.getDniUsuarioOperador());
            
            boolean esReceptor = false;
            if (actividad instanceof Transferencia) {
                Transferencia trans = (Transferencia) actividad;
                esReceptor = dniUsuario.equals(trans.getDniDestino());
            }
            
            if (esEmisor || esReceptor) {
                historial.add(actividad.devolverInfo());
            }
        }
        return historial;
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
        Map<String, Integer> cantidadPorCvu = new HashMap<>();
        
        for (Actividad actividad : actividades.values()) {    
        	
            String cvu = actividad.getCvuCuentaEmisora();
            
            if (cvu != null) {
                if (cantidadPorCvu.containsKey(cvu)) {
                    int cantidadActual = cantidadPorCvu.get(cvu);
                    cantidadPorCvu.put(cvu, cantidadActual + 1);
                } else {
                    cantidadPorCvu.put(cvu, 1);
                }
            }
            
            if (actividad instanceof Transferencia) {
                Transferencia trans = (Transferencia) actividad;
                String cvuReceptor = trans.getCvuCuentaReceptora();
                
                if (cvuReceptor != null) {
                    if (cantidadPorCvu.containsKey(cvuReceptor)) {
                        int cantidadActual = cantidadPorCvu.get(cvuReceptor);
                        cantidadPorCvu.put(cvuReceptor, cantidadActual + 1);
                    } else {
                        cantidadPorCvu.put(cvuReceptor, 1);
                    }
                }
            }
        }

        List<Map.Entry<String, Integer>> listaOrdenada = new ArrayList<>(cantidadPorCvu.entrySet());
        Collections.sort(listaOrdenada, new Comparator<Map.Entry<String, Integer>>() {
                    @Override
                    public int compare(Map.Entry<String, Integer> o1,
                                       Map.Entry<String, Integer> o2) {
                        return o2.getValue().compareTo(o1.getValue());
                    }
                });

        List<String> cuentasTop = new ArrayList<>();
        for (int i = 0; i < cantidadTop && i < listaOrdenada.size(); i++) {
            cuentasTop.add(listaOrdenada.get(i).getKey());
        }

        return cuentasTop;
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
