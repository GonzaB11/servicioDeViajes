package ar.edu.ungs.prog2.viajeFeliz;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Alquiler extends ServicioSimple {
    private double garantia;
    private String fechaDevolucion;

    public Alquiler(int codigo, double costoBase, String fechaInicio, int cantidad, String pais, String ciudad, double garantia, String fechaDevolucion) {
        super(codigo, costoBase, fechaInicio, cantidad, pais, ciudad);
        if(garantia<=0) {
        	throw new RuntimeException ("Garantia tiene que ser mayor a 0");
        }
        if (fechaDevolucion.isEmpty() || fechaDevolucion==null) {
        	throw new RuntimeException ("Ingrese fecha de devolucion correctamente");
        }
        this.garantia = garantia;
        this.fechaDevolucion = fechaDevolucion;
    }

    public double obtenerGarantia() {
        return garantia;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }
    public int cantidadDias() {
        LocalDate fechaSalida = LocalDate.parse(this.fechaDevolucion);
        LocalDate fechaInicio = LocalDate.parse(super.obtenerFechaInicio());
        return (int) ChronoUnit.DAYS.between(fechaInicio, fechaSalida);
    }

    @Override
    public double calcularCosto() {
    	double costo = 0;
    	if (super.obtenerCantidad()<5) {
    		costo= super.obtenerCostoBase()* cantidadDias() + this.garantia;
    	}
    	if (super.obtenerCantidad()>=5 && super.obtenerCantidad()<=8) {
    		costo=(super.obtenerCostoBase()* cantidadDias()) *2 + this.garantia;
    	}
    	if (super.obtenerCantidad()>8 && super.obtenerCantidad()<=10 ) {
    		costo= (super.obtenerCostoBase()* cantidadDias())* 3 + this.garantia;
    	}
        return costo;
    }
    @Override
	public String toString() {
		return "Alquiler";
	}

}
