package ar.edu.ungs.prog2.viajeFeliz;

public abstract class Servicio {
    private int codigo;
    private double costoBase;
    private String fechaInicio;
    private int cantidad;

    public Servicio(int codigo, double costoBase, String fechaInicio, int cantidad) {
    	if(codigo<0) {
			throw new RuntimeException ("codigo tiene que ser mayor que 0");
		}
    	if (costoBase < 0) {
			throw new RuntimeException ("Ingrese costo valido");
		}
		if (cantidad < 0) {
			throw new RuntimeException ("Cantidad de personas tienen que ser mayor a 0");
		}
		if (fechaInicio == null) {
			throw new RuntimeException("Ingrese una fecha");
		}
    	this.codigo = codigo;
        this.costoBase = costoBase;
        this.fechaInicio = fechaInicio;
        this.cantidad = cantidad;
    }

    public int obtenerCodigo() {
        return codigo;
    }

    public double obtenerCostoBase() {
        return costoBase;
    }

    public String obtenerFechaInicio() {
        return fechaInicio;
    }

    public int obtenerCantidad() {
        return cantidad;
    }

    public abstract double calcularCosto();
}
