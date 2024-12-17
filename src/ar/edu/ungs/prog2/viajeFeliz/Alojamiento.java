package ar.edu.ungs.prog2.viajeFeliz;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;

public class Alojamiento extends ServicioSimple {
	private String fechaSalida;
    private String hotel;
    private double costoTraslado;
 

    public Alojamiento(int codigo, double costoBase, String fechaInicio, int cantidad, String pais, String ciudad, String fechaSalida, String hotel, double costoTraslado) {
        super(codigo, costoBase, fechaInicio, cantidad, pais, ciudad);
        if (fechaSalida.isEmpty()|| fechaSalida==null) {
        	throw new RuntimeException ("Ingrese fecha de Salida correctamente");
        }
        if (hotel.isEmpty() || hotel==null) {
        	throw new RuntimeException ("Ingrese nombre de hotel correctamente");
        }
        if (costoTraslado<=0) {
        	throw new RuntimeException ("Costo de traslado tiene que ser mayor a 0");
        }
        this.fechaSalida = fechaSalida;
        this.hotel = hotel;
        this.costoTraslado = costoTraslado;
    }

    public String obtenerFechaSalida() {
        return fechaSalida;
    }

    public String obtenerHotel() {
        return hotel;
    }

    public double obtenerCostoTraslado() {
        return costoTraslado;
    }
    
    public int cantidadDias() {
        LocalDate fechaSalida = LocalDate.parse(this.fechaSalida);
        LocalDate fechaInicio = LocalDate.parse(super.obtenerFechaInicio());
        return (int) ChronoUnit.DAYS.between(fechaInicio, fechaSalida);
    }
    

    @Override
    public double calcularCosto() {
    	double costo = 0;
    	if (super.obtenerCantidad() > 5) {
    		throw new RuntimeException("La cantidad no puede ser mayor a 5");
    	}
    	if(super.obtenerCantidad() > 0 || super.obtenerCantidad() <=2) {
    		costo = super.obtenerCostoBase()* cantidadDias() + this.costoTraslado;
    	} else if (super.obtenerCantidad() <= 4) {
    			costo = (super.obtenerCostoBase()*2) * cantidadDias() + this.costoTraslado;
    	} else {
    		costo = (super.obtenerCostoBase()*2.5)* cantidadDias() + this.costoTraslado;;	
    	}
        return costo;
    }
    @Override
	public String toString() {
		return "Alojamiento";
	}

}