package ar.edu.ungs.prog2.viajeFeliz;

public class Principal {

	public static void main(String[] args) {
		ViajeFeliz emp = new ViajeFeliz("11-11111111-1");

		System.out.println(emp);

		// Registro de clientes nuevos
		String dniHomero = "30000001";
		String dniNed = "30000020";
		String dniEdna = "30000300";
		String dniKirk = "30004000";

		emp.registrarCliente(dniHomero, "Homero", "Av. siempre viva 742");
		emp.registrarCliente(dniNed, "Ned", "Av. siempre viva 744");
		emp.registrarCliente(dniEdna, "Edna", "Av. siempre viva 82");
		emp.registrarCliente(dniKirk, "Kirk", "Pikeland 316");

		System.out.println();
		System.out.println();
		System.out.println(emp);

		// Registro de paquetes predefinidos (ofertas/promociones)
		// Operacion:
		// 1- Se crea cada servicio en el sistema y guardamos el codigo de cada uno.
		// 2- Se solicita crear un paquete pasando todos los codigos de servicio creados
		// y la cantidad de paquetes disponibles.

		// promo san valentin
		int sViaje = emp.crearServicio(700, "2025-02-13", 2, "Francia", "Paris", "2025-02-15", 15);
		int sAlojamiento = emp.crearServicio(50, "2025-02-14", 2, "Francia", "Paris", "2025-02-15", "Boutique Hotel",
				20);

		System.out.println();
		System.out.println();
		System.out.println(emp);

		int[] serviciosEnPaquete = { sViaje, sAlojamiento };
		int[] promosParaSanValentin = emp.crearPaquetesPredefinidos(15, serviciosEnPaquete);

		System.out.println();
		System.out.println();
		System.out.println(emp);

		// Operaciones para la contratacion de un paquete predefinido
		// 1- Se solicita iniciar una contratacion o agregar un servicio indicando el
		// codigo del paquete.
		//
		// Operaciones para la contratacion de un servicio simple
		// 1- Se crea un servicio en el sistema (devuelve el codigo de servicio)
		// 2- Se solicita agregar ese servicio a la contratación del cliente.

		// Kirk compra el paquete de san valentin y lo paga
		emp.iniciarContratacion(dniKirk, promosParaSanValentin[0]);
		emp.pagarContratacion(dniKirk, "2024-05-21");

		// Edna compra el paquete, agrega un servicio de alquiler y lo paga
		int codContratacion = emp.iniciarContratacion(dniEdna, promosParaSanValentin[1]);
		int sAlquiler = emp.crearServicio(15, "2025-02-14", 2, "Paris", "Francia", 20, "2025-02-15");

		try {
			// si intento iniciar una contratación sin terminar la anterior,
			// se debe lanzar una exception.
			emp.iniciarContratacion(dniEdna, sAlquiler);
			System.out.println("Si se imprime este mensaje, tienes algo mas que resolver.");
		} catch (RuntimeException e) {
		}

		emp.agregarServicioAContratacion(dniEdna, sAlquiler);
		System.out.println("Monto a pagar por contratacion de Edna: $"
				+ emp.calcularCostoDePaquetePersonalizado(dniEdna, codContratacion));
		emp.pagarContratacion(dniEdna, "2024-07-26");

		// Homero compra todo por separado
		// Excursion
		int sAux = emp.crearServicio(160, "2025-02-14", 2, "City Tour", true);
		codContratacion = emp.iniciarContratacion(dniHomero, sAux);
		emp.pagarContratacion(dniHomero, "2024-05-24");

		// Vuelo
		sAux = emp.crearServicio(800, "2025-02-13", 2, "Francia", "Paris", "2025-02-15", 15);
		codContratacion = emp.iniciarContratacion(dniHomero, sAux);
		emp.pagarContratacion(dniHomero, "2024-05-30");

		// Alojamiento
		sAux = emp.crearServicio(100, "2025-02-14", 2, "Francia", "Paris", "2025-02-15", "Boutique Hotel", 20);
		codContratacion = emp.iniciarContratacion(dniHomero, sAux);
		emp.pagarContratacion(dniHomero, "2024-06-15");

		System.out.println();
		System.out.println();
		System.out.println("Contrataciones sin iniciar a la fecha: 2024-11-01");
		System.out.println(emp.contratacionesSinIniciar("2024-11-01"));

		System.out.println();
		System.out.println();
		System.out.println("Contrataciones que incia en fecha: 2025-02-13");
		for (String s : emp.contratacionesQueInicianEnFecha("2025-02-13"))
			System.out.println(" - " + s);

		System.out.println();
		System.out.println();
		System.out.println("Codigos de los servicios disponibles para contratar: ");
		for (Integer i: emp.obtenerCodigosCatalogo())
			System.out.println(" - " + i);
		
		
		System.out.println();
		System.out.println();
		System.out.println("Historial de contrataciones del cliente con dni " + dniHomero);
		for (Integer i: emp.historialDeContrataciones(dniHomero))
			System.out.println(" - " + i);
		
		
		System.out.println();
		System.out.println();
		System.out.println(emp);
	}

}
