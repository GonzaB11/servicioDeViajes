package ar.edu.ungs.prog2.viajeFeliz;

public class Vuelo extends ServicioSimple {
    private String fechaLlegada;
    private double tasa;

    public Vuelo(int codigo, double costoBase, String fechaInicio, int cantidad, String pais, String ciudad, String fechaLlegada, double tasa) {
        super(codigo, costoBase, fechaInicio, cantidad, pais, ciudad);
        if (fechaLlegada==null || fechaLlegada.isEmpty()) {
        	throw new RuntimeException ("Ingrese fecha de llegada correctamente");
        }
        if (tasa <=0) {
        	throw new RuntimeException ("tasa tiene que ser mayor que 0");
        }
        this.fechaLlegada = fechaLlegada;
        this.tasa = tasa;
    }

    public String obtenerFechaLlegada() {
        return fechaLlegada;
    }

    public double obtenerTasa() {
        return tasa;
    }

    @Override
    public double calcularCosto() {
        return obtenerCostoBase() * super.obtenerCantidad()* (1+tasa);
    }
    @Override
	public String toString() {
		return "Vuelo";
	}

}
