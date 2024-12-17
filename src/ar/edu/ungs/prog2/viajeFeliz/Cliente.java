package ar.edu.ungs.prog2.viajeFeliz;


import java.util.ArrayList;
import java.util.LinkedList;

public class Cliente {
	private String dni;
	private String nombre;
	private String direccion;
	private ArrayList<PaquetePersonalizado> contratados = new ArrayList<>();

	public Cliente(String dni, String nombre, String direccion) {
		if (dni == null || dni.isEmpty()) {
			throw new RuntimeException("Ingrese dni correcto");
		}
		if (nombre == null || nombre.isEmpty()) {
			throw new RuntimeException("Ingrese nombre correcto");
		}
		if (direccion == null || direccion.isEmpty()) {
			throw new RuntimeException("Ingrese dirección correcta");
		}
		this.dni = dni;
		this.nombre = nombre;
		this.direccion = direccion;
	}
	
	public String obtenerNombre() {
		return this.nombre;
	}
	public String obtenerDni() {
		return this.dni;
	}
	
	public ArrayList<PaquetePersonalizado> obtenerContratados() {
		return this.contratados;
	}

	public boolean tieneContratacionPrevia() {
		for (PaquetePersonalizado paquete : contratados) {
			if (!paquete.estaFinalizado()) {
				return true;
			}
		}
		return false;
	}

	public void agregarNuevoPaquetePersonalizado(PaquetePersonalizado paquete) {
		contratados.add(paquete);
	}

	public void agregarServicioAPaqueteVigente(Servicio servicio) {
		for (PaquetePersonalizado paquete : contratados) {
			if (!paquete.estaFinalizado()) {
				paquete.agregarServicio(servicio);
				return;
			}
		}
		throw new RuntimeException("No hay paquete vigente para agregar servicio");
	}

	public boolean quitarServicioDeContratacion(int codServicio) {
		for (PaquetePersonalizado paquete : contratados) {
			if (paquete.contieneServicio(codServicio)) {
				paquete.quitarServicio(codServicio);
				return true;
			}
		}
		return false;
	}


	public PaquetePersonalizado obtenerPaquetePersonalizado(int codPaquetePersonalizado) {
		for (PaquetePersonalizado paquete : contratados) {
			if (paquete.obtenerCodigo() == codPaquetePersonalizado) {
				return paquete;
			}
		}
		return null;
	}

	public double pagarContratacion(String fechaPago) {
		for (PaquetePersonalizado paquete : contratados) {
			if (!paquete.estaFinalizado()) {
				return paquete.pagar(fechaPago);
			}
		}
		throw new RuntimeException("No hay paquete vigente para pagar");
	}

	public PaquetePersonalizado existePaquete(int cod) {
		for (PaquetePersonalizado p: contratados) {
			if (p.obtenerCodigo()==cod) {
				return p;
			}
		}
		return null;
	}

	public LinkedList<Integer> historialContrataciones() {
	    LinkedList<Integer> lista = new LinkedList<>();
	    for (PaquetePersonalizado paquetes : contratados) {
	        if (paquetes.estaFinalizado()) {
	            lista.add(paquetes.obtenerCodigo());
	        }
	    }
	    return lista;
	}

	

	@Override
	public String toString() {
		return nombre;
	}
	


	

	
}
