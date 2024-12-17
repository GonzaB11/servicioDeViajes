package ar.edu.ungs.prog2.viajeFeliz;

public abstract class ServicioSimple extends Servicio {
    private String pais;
    private String ciudad;

    public ServicioSimple(int codigo, double costoBase, String fechaInicio, int cantidad, String pais, String ciudad) {
        super(codigo, costoBase, fechaInicio, cantidad);
        if (pais.isEmpty() || pais == null) {
			throw new RuntimeException ("Ingrese un país");
		}
        if (ciudad.isEmpty() || ciudad == null) {
			throw new RuntimeException ("Ingrese una ciudad");
		}
        this.pais = pais;
        this.ciudad = ciudad;
    }

    public String obtenerPais() {
        return pais;
    }

    public String obtenerCiudad() {
        return ciudad;
    }

    @Override
    public abstract double calcularCosto();
}
