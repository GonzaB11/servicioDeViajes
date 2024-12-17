package ar.edu.ungs.prog2.viajeFeliz;
import java.util.ArrayList;

public class PaquetePredefinido extends Servicio {
	private ArrayList<Servicio> servicios;

    public PaquetePredefinido(int codigo) {
        super(codigo, 0, "", 0);
        this.servicios = new ArrayList<>();
    }

    public void agregarServicio(Servicio servicio) {
        servicios.add(servicio);
    }

    public ArrayList<Servicio> obtenerServicios() {
        return servicios;
    }

	@Override
	public double calcularCosto() {
		return 0;
	}

   
}
