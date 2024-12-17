package ar.edu.ungs.prog2.viajeFeliz;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class ViajeFelizTest {

	private ViajeFeliz ReservaYa, TuViaje, MejorPrecio;
	private String dniCli, nombreCliente;
	private LinkedList<Integer> histCli = new LinkedList<>();
	private LinkedList<String> contratacionesInician10Jul = new LinkedList<>();
	private int servQuitado, paqQuitado;
	private double costoPagarPaquete;
	private String listadosSinIniciar = "";

	@Before
	public void setUp() throws Exception {
		// Si se genera una excepcion en este bloque todos los TESTs fallarán (Rojo).
		ReservaYa = new ViajeFeliz("11-11111111-1");
		TuViaje = new ViajeFeliz("22-22222222-2");
		MejorPrecio = new ViajeFeliz("33-33333333-3");

		dniCli = "34000000";
		nombreCliente = "Homero";
		String direccion = "Av. Siempreviva 742";
		ReservaYa.registrarCliente(dniCli, nombreCliente, direccion);

		iniciarServicios(TuViaje);
		crearNPaquetesPredefinidos(3, MejorPrecio);
		crearPaquetePersonalizado();
		agregarPaqueteIniciado();
		costoPagarPaquete = iniciarServicioNoPago();
	}

	@Test(expected = RuntimeException.class)
	public void siSeIngresanUnDniExistenteLanzaExcepcion() {
		ReservaYa.registrarCliente("34000000", "Raul", "Sarmiento 1880");
	}

	@Test
	public void crearServicioGeneraUnCodigo() {
		assertFalse(TuViaje.obtenerCodigosCatalogo().contains(null));
	}

	@Test
	public void codigosDeServiciosSonUnicos() {
		assertEquals(4, TuViaje.obtenerCodigosCatalogo().size());
	}

	@Test
	public void creaLaCantidadDePaquetesPredefinidosSolicitados() {
		assertEquals(3, MejorPrecio.obtenerCodigosCatalogo().size());
	}

	@Test(expected = RuntimeException.class)
	public void iniciarContratacionConDniInexistentelanzaExcepcion() {
		ReservaYa.iniciarContratacion("99999999", 2);
	}

	@Test(expected = RuntimeException.class)
	public void iniciarUnServicioInexistenteAlCotratarLanzaExcepcion() {
		ReservaYa.iniciarContratacion("34000000", 1000000);
	}

	@Test(expected = RuntimeException.class)
	public void agregarServicioAContratacionConDniInexistentelanzaExcepcion() {
		ReservaYa.agregarServicioAContratacion("99999999", 2);
	}

	@Test(expected = RuntimeException.class)
	public void agregarUnServicioInexistenteAlCotratarLanzaExcepcion() {
		ReservaYa.agregarServicioAContratacion("34000000", 1000000);
	}

	@Test(expected = RuntimeException.class)
	public void quitarServicioDeContratacionConDniInexistentelanzaExcepcion() {
		ReservaYa.quitarServicioDeContratacion("99999999", 2);
	}

	@Test(expected = RuntimeException.class)
	public void quitarServicioInexistenteAlCotratarLanzaExcepcion() {
		ReservaYa.quitarServicioDeContratacion(dniCli, 11111111);
	}

	@Test
	public void quitarServicioEliminaDelCatalogo() {
		assertFalse(ReservaYa.obtenerCodigosCatalogo().contains(servQuitado));
	}

	@Test
	public void quitarPaqueteDevuelveAlCatalogo() {
		assertTrue(ReservaYa.obtenerCodigosCatalogo().contains(paqQuitado));
	}

	@Test
	public void calcularcostoPaquetePersonalizadosDevuelveCostoOk() {
		assertEquals(calCosto(), ReservaYa.calcularCostoDePaquetePersonalizado(dniCli, histCli.get(0)), 0.1);
	}

	@Test
	public void listaDeServiciosContratadosDevuelveListaOk() {
		assertEquals(histCli, ReservaYa.historialDeContrataciones(dniCli));
	}

	@Test(expected = RuntimeException.class)
	public void pagarConFechaPosteriorDevuelveError() {
		ReservaYa.pagarContratacion(dniCli, "2024-07-15");
	}
	
	@Test
	public void pagarContratacionDevuelveCostoTotalOk() {
		assertEquals(costoPagarPaquete, ReservaYa.pagarContratacion(dniCli, "2024-07-07"), 0.1);
	}

	@Test
	public void formatoStringDeLasContratacionesSinIniciarOk() {
		assertEquals(listadosSinIniciar, ReservaYa.contratacionesSinIniciar("2024-07-13"));
	}

	@Test
	public void formatoStringContratacionesEnFechaOk() {
		assertEquals(contratacionesInician10Jul, ReservaYa.contratacionesQueInicianEnFecha("2024-07-10"));
	}

//	#### Metodos Auxiliares Test ####

	private void iniciarServicios(ViajeFeliz emp) {
		emp.crearServicio(2000, "2024-07-15", 3, "Italia", "Roma", "2024-07-16", 0.20);
		emp.crearServicio(600, "2024-07-16", 2, "Italia", "Roma", "2024-07-20", "Hotel ABC", 75);
		emp.crearServicio(350, "2024-07-16", 1, "Italia", "Roma", 1200, "2024-07-20");
		emp.crearServicio(250, "2024-07-16", 2, "Coliseo", false);
	}

	private void crearNPaquetesPredefinidos(int cant, ViajeFeliz emp) {
		iniciarServicios(emp);
		emp.crearPaquetesPredefinidos(cant, obtenerCodigos(emp));
	}

	private int[] obtenerCodigos(ViajeFeliz emp) {
		Set<Integer> catalogo = emp.obtenerCodigosCatalogo();
		return catalogo.stream().mapToInt(Integer::intValue).toArray();		
	}

	private void crearPaquetePersonalizado() {
		crearNPaquetesPredefinidos(3, ReservaYa);
		int[] c = obtenerCodigos(ReservaYa);
		histCli.add(ReservaYa.iniciarContratacion(dniCli, c[0]));
		agregarServiciosAlPaquete();
	}

	private void agregarServiciosAlPaquete() {
		int ultServ = ReservaYa.crearServicio(250, "2024-07-17", 3, "Napoles", true);
		ReservaYa.agregarServicioAContratacion(dniCli, ultServ);
		ReservaYa.pagarContratacion(dniCli, "2024-07-12");
		List<String> s = Arrays.asList("Vuelo", "Alojamiento", "Alquiler", "Excursion", "Excursion");
		agregarPaqueteNoIniciado(nombreCliente, "2024-07-15", s);
	}

	private double calCosto() {
		double cVuelo = 2000 * 3 * 1.2; // Costo * Cant + % Tasa
		double cAloj = 600 * 4 + 75; // Costo para 2 * cantDias + Traslado
		double cAlq = 350 * 1 * 4 + 1200; // Costo * cant * cantDias + garantia
		double cExc1 = 250 * 2 * 0.95; // ValorMedioDia * Cant - Desc 5% (2 personas)
		double cExc2 = 250 * 2 * 3 * 0.90; // MedioDia * 2 (completo) * Cant - Desc 10% (3 personas)
		double sumaCosto = cVuelo + cAloj + cAlq + cExc1 + cExc2;
		return sumaCosto * 0.95; // 5% Desc por 2 servicios (un paquetePredefinido y un servicio de excursion)
	}

	private void agregarPaqueteNoIniciado(String nombre, String fechaInicio, List<String> servicios) {
		String serviciosString = String.join(", ", servicios);
		listadosSinIniciar += String.format("%s | %s | [%s]\n", nombre, fechaInicio, serviciosString);
	}

	private void agregarPaqueteIniciado() {
		int vueloCanada = ReservaYa.crearServicio(2000, "2024-07-10", 3, "Canada", "Toronto", "2024-07-11", 0.15);
		int codContratacion = ReservaYa.iniciarContratacion(dniCli, vueloCanada); 
		histCli.add(codContratacion);
		ReservaYa.pagarContratacion(dniCli, "2024-07-08");
		contratacionesInician10Jul.add(fInicioAString(codContratacion));
	}

	private double iniciarServicioNoPago() {
		int v = ReservaYa.crearServicio(1500, "2024-07-12", 2, "Uruguay", "Montevideo", "2024-07-21", 0.15);
		ReservaYa.iniciarContratacion(dniCli, v);
		servicioAQuitar(); // No se suman al costo
		paqueteAQuitar(); //
		return 1500 * 2 * 1.15; // Costo Base * Cant pasajes + % Tasa
	}

	private void servicioAQuitar() {
		servQuitado = ReservaYa.crearServicio(100, "2024-07-13", 3, "Punta Del Este", true);
		ReservaYa.agregarServicioAContratacion(dniCli, servQuitado);
		ReservaYa.quitarServicioDeContratacion(dniCli, servQuitado);
	}

	private void paqueteAQuitar() {
		int[] e = { ReservaYa.crearServicio(100, "2024-07-14", 3, "Cataratas", true) };
		paqQuitado = ReservaYa.crearPaquetesPredefinidos(1, e)[0];
		ReservaYa.agregarServicioAContratacion(dniCli, paqQuitado);
		ReservaYa.quitarServicioDeContratacion(dniCli, paqQuitado);
	}

	private String fInicioAString(int codContratacion) {
		return String.format("%s - (%s %s)", codContratacion, dniCli, nombreCliente);
	}
}