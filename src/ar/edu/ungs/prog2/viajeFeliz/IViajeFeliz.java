package ar.edu.ungs.prog2.viajeFeliz;

import java.util.List;
import java.util.Set;

public interface IViajeFeliz {
	// 1) Es el constructor con el CUIT como parametro

	/**
	 * 2)
	 * Registrar un cliente nuevo con su nombre, dirección y dni.
	 * 
	 * @param dni
	 * @param nombre
	 * @param direccion
	 */
	void registrarCliente(String dni, String nombre, String direccion);

	/**
	 * 3)
	 * Registrar un servicio de Vuelo.
	 * 
	 * @param costoBase
	 * @param fechaInicio
	 * @param cantidad
	 * @param pais
	 * @param ciudad
	 * @param fechaLlegada
	 * @param tasa
	 * @return
	 */
	int crearServicio(double costoBase, String fechaInicio, int cantidad, String pais, String ciudad, String fechaLlegada, double tasa);
	/**
	 * 
	 * 3)
	 * Registrar un servicio de Alojamiento
	 * 
	 * @param costoBase
	 * @param fechaInicio
	 * @param cantidad
	 * @param pais
	 * @param ciudad
	 * @param fechaSalida
	 * @param hotel
	 * @param costoTraslado
	 * @return
	 */
	int crearServicio(double costoBase, String fechaInicio, int cantidad, String pais, String ciudad, String fechaSalida, String hotel, double costoTraslado);
	/**
	 * 3)
	 * Registrar un servicio de Alquiler
	 * 
	 * @param costoBase
	 * @param fechaInicio
	 * @param cantidad
	 * @param pais
	 * @param ciudad
	 * @param fechaDevolucion
	 * @param garantia
	 * @return
	 */
	int crearServicio(double costoBase, String fechaInicio, int cantidad, String pais, String ciudad, double garantia, String fechaDevolucion);
	/**
	 * 
	 * 3)
	 * Registrar un servicio de Excursion
	 * @param costoBase
	 * @param fechaInicio
	 * @param cantidad
	 * @param lugarSalida
	 * @param esDiaCompleto
	 * @return codigo de servicio unico
	 */
	int crearServicio(double costoBase, String fechaInicio, int cantidad, String lugarSalida, boolean esDiaCompleto);


	/**
	 * 4)
	 * Crear un paquete predefinido con los servicios recibidos por Parametro.
	 * Se pueden crear varias copias del paquete para su uso.
	 * 
	 * @param cantPaquetes
	 * @param codigosDeServicios
	 * @return arreglo con los codigos de los paquetes generados.
	 */
	int[] crearPaquetesPredefinidos(int cantPaquetes, int[] codigosDeServicios);

	/**
	 * 5)
	 * Se Inicia la contratacion de un servicio o paquete predefinido por parte 
	 * del cliente.
	 * Si tiene contrataciones previas sin finalizar, se debe lanzar una 
	 * excepción.
	 * 
	 * 
	 * @param dni
	 * @param codServicio
	 * @return codigo de paquete personalizado asociado a la contratación
	 */
	int iniciarContratacion(String dni, int codServicio);

	/**
	 * 6)
	 * Agrega un servicio o paquete predefinido a la contratación vigente de un 
	 * cliente.
	 * Al asociar el servicio con el cliente. se debe quitar del 
	 * registro de servicios que tiene la empresa.
	 * 
	 * @param dni
	 * @param codServicio
	 */
	void agregarServicioAContratacion(String dni, int codServicio);

	/**
	 * 7)
	 * Quitar un servicio de la contratacion vigente del cliente.
	 * Si es un servicio simple, se borra del sistema.
	 * 
	 * Si es un paquete, se debe regresar al sistema como paquete disponible
	 *  
	 * 
	 * @param dni
	 * @param codServicio
	 */
	void quitarServicioDeContratacion(String dni, int codServicio);

	/**
	 * 8)
	 * Calcular el total a pagar por alguno de los paquetes personalizados 
	 * que contrató el cliente.
	 * Para esto se identifica al cliente con el dni y el paquete 
	 * personalizado por su codigo unico. Puede ser el paquete asociado a 
	 * la contratación actual o alguno de los paquetes contratados en el pasado.
	 * 
	 * @param dni
	 * @param codPaquetePersonalizado
	 * @return
	 */
	double calcularCostoDePaquetePersonalizado(String dni, int codPaquetePersonalizado);

	/**
	 * 9)
	 * Efectiviza la contratación registrando que fué pagado y registrando la 
	 * fecha de pago.
	 * 
	 * La fecha de pago debe ser anterior que la fecha de inicio del paquete 
	 * personalizado.
	 * 
	 * 
	 * @param dni
	 * @param fechaPago
	 * @return
	 */
	double pagarContratacion(String dni, String fechaPago);

	/**
	 * 10)
	 * Devolver un listado con los codigos unicos de los paquetes que contrató 
	 * un cliente desde que lo registró la empresa, conociendo su dni.
	 * 
	 * @param dni
	 * @return
	 */
	List<Integer> historialDeContrataciones(String dni);

	/**
	 * 11)
	 * Devolver los paquetes que aún no se iniciaron dando nombre del cliente, 
	 * fecha de inicio y los datos de los servicios contratados.
	 * " {nombre_cliente} | {fecha_inicio} | [ {tipo_servicio_1}, {tipo_servicio_2}]
	 * Ejemplo: Homero | 2024-02-14 | [Vuelo, Alojamiento, Excursion]
	 * 
	 * @param fecha
	 * @return
	 */
	String contratacionesSinIniciar(String fecha);

	/**
	 * 12)
	 * Devuelve una lista con los datos de las contrataciones que se inician 
	 * en la fecha pasada por parámetro. Indicando a que cliente pertenecen.
	 * Formato de cada entrada: "{codigoUnicoContratacion} - ({dniCliente} {nombreCliente})"
	 * 
	 * @param fecha
	 * @return
	 */
	List<String> contratacionesQueInicianEnFecha(String fecha);


	/**
	 * 13)
	 * Devuelve un conjunto con las claves de los servicios y paquetes
	 * predefinidos ofertados por la empresa.
	 * 
	 * @return
	 */
	Set<Integer> obtenerCodigosCatalogo();

}

