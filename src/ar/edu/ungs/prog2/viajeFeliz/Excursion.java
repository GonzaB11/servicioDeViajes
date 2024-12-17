package ar.edu.ungs.prog2.viajeFeliz;

public class Excursion extends Servicio {
    private String lugarSalida;
    private boolean esDiaCompleto;

    public Excursion(int codigo, double costoBase, String fechaInicio, int cantidad, String lugarSalida, boolean esDiaCompleto) {
        super(codigo, costoBase, fechaInicio, cantidad);
        if (lugarSalida.isEmpty() || lugarSalida== null) {
        	throw new RuntimeException ("Ingrese lugar de salida correctamente");
        }
        this.lugarSalida = lugarSalida;
        this.esDiaCompleto = esDiaCompleto;
    }

    public String obtenerLugarSalida() {
        return lugarSalida;
    }
   
    @Override
    public double calcularCosto() {
    	double costo = 0;
    	if (this.esDiaCompleto) {
    		costo = (super.obtenerCostoBase()*2) * super.obtenerCantidad() * 0.90;
    	} else {
    		costo= super.obtenerCostoBase()* super.obtenerCantidad() * 0.95;
    	}
        return costo;
      }
    @Override
	public String toString() {
		return "Excursion";
	}

}