package ar.edu.ungs.prog2.viajeFeliz;

import java.time.LocalDate;
import java.util.ArrayList;

public class PaquetePersonalizado {
	private int codigo;
	private ArrayList<Servicio> servicios;
	private String fechaInicio;
	private boolean finalizado;

	public PaquetePersonalizado(int codigo) {
		this.codigo = codigo;
		this.servicios = new ArrayList<>();
		this.finalizado = false;
	}

	public int obtenerCodigo() {
		return codigo;
	}

	

	@Override
	public String toString() {
		return this.fechaInicio + " | " + servicios;
	}
	

	public String obtenerFechaInicio() {
		Servicio s= servicios.get(0);
		this.fechaInicio= s.obtenerFechaInicio();
		return fechaInicio;
	}

	

	public void agregarServicio(Servicio servicio) {
		servicios.add(servicio);
	}

	public void agregarPaquete(PaquetePredefinido paquete) {
		for (Servicio servicio : paquete.obtenerServicios()) {
			agregarServicio(servicio);
		}
	}
	
	public boolean estaFinalizado() {
		return finalizado;
	}

	public double calcularCosto() {
		double costoTotal = 0;
		for (Servicio servicio : servicios) {
			costoTotal += servicio.calcularCosto();
		}
		if(servicios.size()== 2) {
			return costoTotal * 0.90;
		} else if(servicios.size() >= 3) {
			return costoTotal*0.95;
		}
		return costoTotal; // preguntar al profe
	}

	public double pagar(String fechaPago) {
		String fecha=fechaPago;
		LocalDate pago= LocalDate.parse(fecha);
        LocalDate fechaInicio = LocalDate.parse(obtenerFechaInicio());
        if (pago.isAfter(fechaInicio)) {
        	throw new RuntimeException("Fecha de pago no puede ser despues que la fecha de inicio");
        }
		finalizado = true;
		return calcularCosto();
	}
	
	public boolean contieneServicio(int codServicio) {
	    for (Servicio servicio : servicios) {
	        if (servicio.obtenerCodigo() == codServicio) {
	            return true;
	        }
	    }
	    return false;
	}

	public void quitarServicio(int codServicio) {
	    servicios.removeIf(servicio -> servicio.obtenerCodigo() == codServicio);
	}

	public boolean fechaInicioMayorAFecha(LocalDate fechaActual) {
		LocalDate fechaInicio = LocalDate.parse(obtenerFechaInicio());
		if (fechaActual.isBefore(fechaInicio)) {
			return true;
		}
		return false;
	}
}
