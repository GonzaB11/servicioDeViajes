package ar.edu.ungs.prog2.viajeFeliz;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class ViajeFeliz {
	private String cuit;
    private HashMap<String, Cliente> clientes = new HashMap<>();
    private HashMap<Integer, Servicio> servicios = new HashMap<>();
    private HashMap<Integer, PaquetePredefinido> paquetes = new HashMap<>();
    private AtomicInteger codigoGenerator = new AtomicInteger(0);

    public ViajeFeliz(String cuit) {
        if (cuit == null || cuit.isEmpty()) {
            throw new RuntimeException("Cuit inválido");
        }
        this.cuit = cuit;
    }

  
    public void registrarCliente(String dni, String nombre, String direccion) {
        if (clientes.containsKey(dni)) {
            throw new RuntimeException("El cliente ya se encuentra registrado");
        }
        Cliente cliente = new Cliente(dni, nombre, direccion);
        clientes.put(dni, cliente);
    }

    private int generarCodigoUnico() {
        return codigoGenerator.incrementAndGet();
    }

    public int crearServicio(double costoBase, String fechaInicio, int cantidad, String pais, String ciudad, String fechaLlegada, double tasa) {
        int codigo = generarCodigoUnico();
        Servicio vuelo = new Vuelo(codigo, costoBase, fechaInicio, cantidad, pais, ciudad, fechaLlegada, tasa);
        servicios.put(codigo, vuelo);
        return codigo;
    }

 
    public int crearServicio(double costoBase, String fechaInicio, int cantidad, String pais, String ciudad, String fechaSalida, String hotel, double costoTraslado) {
        int codigo = generarCodigoUnico();
        Servicio alojamiento = new Alojamiento(codigo, costoBase, fechaInicio, cantidad, pais, ciudad, fechaSalida, hotel, costoTraslado);
        servicios.put(codigo, alojamiento);
        return codigo;
    }

   
    public int crearServicio(double costoBase, String fechaInicio, int cantidad, String pais, String ciudad, double garantia, String fechaDevolucion) {
        int codigo = generarCodigoUnico();
        Servicio transporte = new Alquiler(codigo, costoBase, fechaInicio, cantidad, pais, ciudad, garantia, fechaDevolucion);
        servicios.put(codigo, transporte);
        return codigo;
    }

    
    public int crearServicio(double costoBase, String fechaInicio, int cantidad, String lugarSalida, boolean esDiaCompleto) {
        int codigo = generarCodigoUnico();
        Servicio excursion = new Excursion(codigo, costoBase, fechaInicio, cantidad, lugarSalida, esDiaCompleto);
        servicios.put(codigo, excursion);
        return codigo;
    }
    
    public int[] crearPaquetesPredefinidos(int cantPaquetes, int[] codigosDeServicios) {
    	int[] codigoPaquetes = new int[cantPaquetes];
    	for (int i = 0; i < cantPaquetes; i++) {
    		int codigo = generarCodigoUnico();  // Generar un código único para cada paquete
    		PaquetePredefinido paquete = new PaquetePredefinido(codigo);
    		for (int codServicio : codigosDeServicios) {	
    			if (!servicios.containsKey(codServicio)) {
    				throw new RuntimeException("El servicio no existe en el catálogo");
    			}
    			paquete.agregarServicio(servicios.get(codServicio));
    		}
    		paquetes.put(codigo, paquete);
    		codigoPaquetes[i] = codigo;
    	}
    	eliminarServicios(codigosDeServicios);
    	return codigoPaquetes;
    }

	private void eliminarServicios(int[] codigosDeServicios) {
		for (int codServicio : codigosDeServicios) {
    		servicios.remove(codServicio);
    	}	
	}

	public int iniciarContratacion(String dni, int codServicio) {
    	 Cliente cliente = clientes.get(dni);
         if (cliente == null) {
             throw new RuntimeException("El cliente no se encuentra registrado");
         }
         if (cliente.tieneContratacionPrevia()) {
        	 throw new RuntimeException("El cliente tiene una contratacion");
         }
         if (!servicios.containsKey(codServicio) && !paquetes.containsKey(codServicio)) {
             throw new RuntimeException("El servicio o paquete predefinido no existe");
         }

         int codPaquete = generarCodigoUnico();
         PaquetePersonalizado paquetePersonalizado = new PaquetePersonalizado(codPaquete);
         if (servicios.containsKey(codServicio)) {
             paquetePersonalizado.agregarServicio(servicios.get(codServicio));
         }else {
             paquetePersonalizado.agregarPaquete(paquetes.get(codServicio));          
         }
         cliente.agregarNuevoPaquetePersonalizado(paquetePersonalizado);
         return codPaquete;
    }
    
    public void agregarServicioAContratacion(String dni, int codServicio) {
        Cliente cliente = clientes.get(dni);
        if (cliente == null) {
            throw new RuntimeException("El cliente no se encuentra registrado");
        }
        if (!servicios.containsKey(codServicio) && !paquetes.containsKey(codServicio)) {
            throw new RuntimeException("El servicio o paquete predefinido no existe");
        }

        if (servicios.containsKey(codServicio)) {
            cliente.agregarServicioAPaqueteVigente(servicios.get(codServicio));
            servicios.remove(codServicio);
        } else {
            cliente.agregarServicioAPaqueteVigente(paquetes.get(codServicio));
        }
    }

    
    public void quitarServicioDeContratacion(String dni, int codServicio) {
        Cliente cliente = clientes.get(dni);
        if (cliente == null) {
            throw new RuntimeException("El cliente no se encuentra registrado");
        }
        boolean servicioEliminado = cliente.quitarServicioDeContratacion(codServicio);
        if (!servicioEliminado) {
            throw new RuntimeException("El servicio no se encuentra en la contratación");
        }    
    }

   
    public double calcularCostoDePaquetePersonalizado(String dni, int codPaquetePersonalizado) {
        Cliente cliente= clientes.get(dni);
        if (cliente==null) {
        	throw new RuntimeException("El cliente no se encuentra registrado");
        }
        PaquetePersonalizado paquete= cliente.existePaquete(codPaquetePersonalizado);
        if (paquete==null) {
        	throw new RuntimeException("El paquete no se encuentra registrado");
        }
        return paquete.calcularCosto();	
    }

    
    public double pagarContratacion(String dni, String fechaPago) {
        Cliente cliente = clientes.get(dni);
        if (cliente == null) {
            throw new RuntimeException("El cliente no se encuentra registrado");
        }
        return cliente.pagarContratacion(fechaPago);
    }

    
    public LinkedList<Integer> historialDeContrataciones(String dni) {
    	LinkedList<Integer> lista= new LinkedList<>();
    	Cliente cliente = clientes.get(dni);
        if (cliente == null) {
            throw new RuntimeException("El cliente no se encuentra registrado");
        }
        lista= cliente.historialContrataciones();  
        return lista;
    }

    
    public String contratacionesSinIniciar(String fecha) {
    	LocalDate fechaActual = LocalDate.parse(fecha);
    	StringBuilder historial = new StringBuilder();
    	for (Cliente cli : clientes.values()) {
    		for (PaquetePersonalizado paquete: cli.obtenerContratados()) {
    			if(paquete.fechaInicioMayorAFecha(fechaActual)) {   
    				historial.append(cli.toString()).append(" | ").append(paquete.toString()).append("\n"); // agregar todos
                }
            }
        }
        return historial.toString();
    }
    
	public ArrayList<String> contratacionesQueInicianEnFecha(String fecha) {
		 ArrayList<String> lista= new ArrayList<>();
		for (Cliente cli : clientes.values()) {
			for (PaquetePersonalizado paquete: cli.obtenerContratados()) {
				if(fecha.equals(paquete.obtenerFechaInicio())) {
					String datos=paquete.obtenerCodigo()+" - ("+ cli.obtenerDni()+
							" "+ cli.obtenerNombre()+ ")";
					lista.add(datos);
				}
			}
		}
		return lista;
    }

    
    public Set<Integer> obtenerCodigosCatalogo() {
        Set<Integer> codigosCatalogo = new HashSet<>(servicios.keySet());
        codigosCatalogo.addAll(paquetes.keySet());
        return codigosCatalogo;
    }


	@Override
	public String toString() {
		return "ViajeFeliz [cuit=" + cuit + "]";
	}
    
}

