package saboteadores;

import saboteadores.Jugador;
import saboteadores.enums.CartaAccionTipo;
import saboteadores.enums.CartaTipo.*;
import saboteadores.tablero.Tablero;
import saboteadores.tablero.Slot;
import saboteadores.tablero.Slots_tablero;
import saboteadores.mazo.cartas.Carta;
import saboteadores.mazo.cartas.CartaReparacion;
import saboteadores.mazo.cartas.CartaSabotaje;
import saboteadores.mazo.cartas.Carta_Accion;
import saboteadores.mazo.cartas.Carta_Camino;
import saboteadores.mazo.cartas.Carta_Oro;

import java.util.ArrayList;
import java.util.Scanner;

public class VistaTerminal {


	private ArrayList<String> nombres = new ArrayList<>();
	private final int MAX = 10;

	private int cartasRestantesMazo;
	private Jugador jugador;
	private Slots_tablero tableroJuego;

	/// por cada interaccion, se muestra denuevo la vista
	public VistaTerminal(){
		//this.tableroJuego = tableroJuego;
		System.out.println("Inicio la vista");
	}
	public void vistaPorDefecto(){
		this.verJugadorActual();
		this.verOrosJugador();
		this.verRestriccionesJugador();
		this.verTablero();
		this.verCantidadDeCartasRestantes();
	}
	public void iniciarJuego(){
		System.out.println("Bienvenido al juego de los saboteadores");
	}

	public ArrayList<String> generarJugadores() {
		Scanner sc = new Scanner(System.in);
		System.out.println("=== Carga de nombres ===");
		System.out.print("Ingresa nombres de los jugadores, maximo 10, minimo 3 ");
		System.out.println("No tiene que haber nombres repetidos ");
		System.out.println("Escribí 'fin' para terminar antes.");
		System.out.println();

		while (nombres.size() < MAX) {
			System.out.print("Ingresá un nombre: ");
			String nombre = sc.nextLine().trim();

			if (nombre.equalsIgnoreCase("fin")) {
				// cortar antes
				if (nombres.size() > 2) {
					System.out.println("Carga finalizada por el usuario.");
					break;
				}else{
					System.out.println("Faltan Jugadores..");
					continue;
				}

			}
			// validar vacío
			if (nombre.isEmpty()) {
				System.out.println("El nombre no puede estar vacío.");
				continue;
			}
			// validar repetido
			if (nombres.contains(nombre)) {
				System.out.println("Ese nombre ya fue ingresado.");
				continue;
			}
			// agregar
			nombres.add(nombre);
			System.out.println("✓ Agregado.");
		}
		if (nombres.size() == MAX) {
			System.out.println("Se alcanzó el máximo de " + MAX + " nombres.");
		}
		return this.nombres;
	}
	public void actualizarTablero(Slots_tablero tablero){
		this.tableroJuego = tablero;
	}
	public void actualizarCartasRestantes(int cantidad){
		this.cartasRestantesMazo = cantidad;
	}
	public void jugadorActual(Jugador j){
		this.jugador = j;
	}
	private void verJugadorActual(){
		System.out.println("==========================================");
		System.out.print("Estas controlando al jugador:");
		System.out.println(this.jugador.getNombre());
		System.out.print("tiene el rol de:");
		System.out.println(this.jugador.getRol());
		System.out.println("==========================================");
	}
	private void verCartasJugador(){
		int i = 1;
		for(Carta c : jugador.getManoJugador()){
			System.out.print(i+ " ");
			System.out.print(c.getTipo());
			switch(c.getTipo()){
				case CALLEJON:
					if(c instanceof Carta_Camino){
						Carta_Camino camino = (Carta_Camino) c;
						System.out.print(camino.getForma());
						System.out.println(" ");
					}
					break;
				case CAMINO:
					if(c instanceof Carta_Camino){
						Carta_Camino camino = (Carta_Camino) c;
						System.out.print(camino.getForma());
						System.out.println(" ");
					}
					break;
				case VISTA:
					System.out.println("Ojo");
					break;
				case DERRUMBE:
					System.out.println("");
					break;
				case SABOTAJE:
					if(c instanceof CartaSabotaje){
						CartaSabotaje sabos = (CartaSabotaje) c;
						System.out.print(" ");
						System.out.print(sabos.getAccion());
						System.out.println(" ");
					}
					break;
				case REPARACION:
					if(c instanceof CartaReparacion){
						CartaReparacion repa = (CartaReparacion) c;
						System.out.print(" ");
						System.out.print(repa.getReparacion1());
						if(repa.getReparacion2() != CartaAccionTipo.VACIO){
							System.out.print(" ");
							System.out.print(repa.getReparacion2());
						}
						System.out.println(" ");
					}
					break;
				case ACCION:
					System.out.println("");
					break;
				case REVERSO:
					System.out.println("");
					break;
			}
			i++;
		}
	}
	private void verOrosJugador(){
		int i = 1;
		if(jugador.getOrosJugador().isEmpty()){
			System.out.println("== Todavia no hay oros vistos ==");
		}else{
			System.out.println("== Oros vistos: ==");
			for(Carta_Oro c : jugador.getOrosJugador()){
				System.out.print(i+ " ");
				System.out.print(c.getTipo());
				System.out.print(c.getForma());
				if(c.getOro()){
					System.out.println("Oro");
				}else{
					System.out.println("Carbon");
				}
				i++;
			}
		}
	}
	private void verRestriccionesJugador(){
		int i = 1;
		if(jugador.getRestriccionesJugador().isEmpty()){
			System.out.println("== Todavia no hay restricciones ==");
		}else{
			System.out.println("== Restricciones: ==");
			for(CartaSabotaje c : jugador.getRestriccionesJugador()){
				System.out.print(i+ " ");
				System.out.println(c.getAccion());
				i++;
			}
		}
	}

	private void verTablero(){
		System.out.println("== Tablero Actual: ==");
		int contador = 0;

		//System.out.print("═  ║  ╔  ╗  ╚  ╝v  ╠  ╣  ╦  ╩  ╬" );
		System.out.println("╔═════════╗" );
		for(int fila = 0; fila < 5; fila++){
			System.out.print("║");
			for(int columna = 0; columna < 9; columna++){
				//System.out.print("pos"+contador+"\t" );
				if(tableroJuego.getSlot(contador).taOcupao() == true){
					//esto realmente tiene que pedir el array de slots
					System.out.print(tableroJuego.getSlot(contador).getCartaAlojadaEnSlot().getForma() );
				}else{
					System.out.print(" ");
				}
				contador ++;
			}
			System.out.println("║" );
		}
		System.out.println("╚═════════╝");
	}
	private void verTableroConPosiciones(){
		System.out.println("== Tablero con posiciones: ==");
		int contador = 0;
		for(int fila = 0; fila < 5; fila++){
			for(int columna = 0; columna < 9; columna++){
				System.out.print(contador+">\t" );
				if(tableroJuego.getSlot(contador).taOcupao() == true){
					//esto realmente tiene que pedir el array de slots
					System.out.print(tableroJuego.getSlot(contador).getCartaAlojadaEnSlot().getForma() );
				}else{
					System.out.print(" ");
				}
				contador ++;
			}
			System.out.println("");
		}
	}

	private void verCantidadDeCartasRestantes(){
		System.out.println("Cartas Restantes en mazo:" 
				+this.cartasRestantesMazo );
	}
	public Carta menuDeCartas() {
		Scanner sc = new Scanner(System.in);
		System.out.println("\n=== Mano del Jugador ===");
		// Mostrar la mano numerada
		verCartasJugador();
		System.out.print("\nElegí el número de carta: ");
		int opcionCarta = sc.nextInt();
		sc.nextLine(); // limpiar buffer

		while(opcionCarta < 1 || opcionCarta > jugador.getManoJugador().size()) {
			System.out.println("Número de carta incorrecto.");
			System.out.print("\nElegí el número de carta: ");
			opcionCarta = sc.nextInt();
			sc.nextLine(); // limpiar buffer
		}
		System.out.println("\nElegiste: " + 
				this.jugador.getManoJugador().get(opcionCarta-1).getTipo());
		return this.jugador.getManoJugador().get(opcionCarta -1);
	}

	public int menuOpciones() {
		System.out.println("¿Qué querés hacer?");
		System.out.println("1) Jugar sobre el tablero");
		System.out.println("2) Jugar sobre un jugador");
		System.out.println("3) Descartar la carta");
		System.out.println("4) Cancelar");
		System.out.print("Opción: ");
		Scanner sc = new Scanner(System.in);
		int accion = sc.nextInt();
		sc.nextLine();
		switch (accion) {
			case 1:
				System.out.println("Jugando la carta sobre el tablero...");
				//jugarEnTablero(jugador, cartaElegida);
				break;

			case 2:
				System.out.println("Jugando la carta sobre un jugador...");
				//menuElegirJugador(jugador, cartaElegida);
				break;

			case 3:
				System.out.println("Descartando la carta...");
				//jugador.descartarCarta(cartaElegida);
				break;

			case 4:
				System.out.println("Acción cancelada.");
				break;

			default:
				System.out.println("Opción inválida.");
		}
		return accion;
	}
	public String menuElegirJugador() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Elegí un jugador objetivo:");
		int i = 1;
		for (String j : nombres) {
			System.out.println(i + ") " + j);
			i++;
		}
		int op = sc.nextInt();
		sc.nextLine();
		while(op < 1 || op > nombres.size()) {
			System.out.println("Jugador inválido.");
			op = sc.nextInt();
			sc.nextLine();
		}
		String objetivo = nombres.get(op - 1);
		System.out.println("Aplicando carta a: " + objetivo);
		//jugarSobreJugador(jugadorActual, objetivo, carta);
		//esto va a parar sobre el modelo
		return objetivo;
	}
	public int menuElegirOro() {
		verTableroConPosiciones();
		Scanner sc = new Scanner(System.in);
		System.out.println("Elegí una Meta objetivo:");
		System.out.println(" 8 |  26 | 44 ");
		int op = sc.nextInt();
		sc.nextLine();
		while( op != 8 && op!=26 && op!=44 ) {
			System.out.println("Meta inválida.");
			op = sc.nextInt();
			sc.nextLine();
		}
		return op;
	}
	public int menuJugarEnTablero() {
		System.out.println("¿Qué querés hacer?");
		System.out.println("1) Colocar un camino... jijiji");
		System.out.println("2) Ver la meta...");
		System.out.print("Opción: ");
		Scanner sc = new Scanner(System.in);
		int accion = sc.nextInt();
		sc.nextLine();
		while( accion != 1 && accion!=2 ) {
			System.out.println("opcion inválida.");
			accion = sc.nextInt();
			sc.nextLine();
		}
		return accion;
	}
	public int introducirPosicion() {
		verTableroConPosiciones();
		System.out.println("Elegi entre 0 y 44");
		System.out.print("Posicion: ");
		Scanner sc = new Scanner(System.in);
		int accion = sc.nextInt();
		sc.nextLine();
		while( accion < 0 || accion > 44 ) {
			System.out.println("opcion inválida.");
			accion = sc.nextInt();
			sc.nextLine();
		}
		return accion;
	}
}
