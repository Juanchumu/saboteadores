package saboteadores.clienteConsola;
import saboteadores.modelo.enums.CartaAccionTipo;
import saboteadores.modelo.Slots_tablero;
import saboteadores.modelo.Top;
import saboteadores.modelo.EstadoSala;
import saboteadores.modelo.mazo.cartas.Carta;
import saboteadores.modelo.mazo.cartas.CartaReparacion;
import saboteadores.modelo.mazo.cartas.CartaSabotaje;
import saboteadores.modelo.mazo.cartas.CartaSimple;
import saboteadores.modelo.mazo.cartas.Carta_Camino;
import saboteadores.modelo.Jugador;
import saboteadores.modelo.JugadorHistorico;

import java.util.ArrayList;
import java.util.Scanner;


import java.util.Optional;

import saboteadores.clienteConsola.ControladorConsola;

//Esto Controla LA VENTANA

public class VistaConsola {
	private ArrayList<String> nombres = new ArrayList<>();
	private final int MAX = 10;

	private int cartasRestantesMazo;
	private Jugador jugador;
	private String jugadorActual;
	private ArrayList<Jugador> adversarios;
	private Slots_tablero tableroJuego;
	private int largoTablero;
	private ControladorConsola controlador; //podria ser una interface realmente
	private String nombreJugador;

	private Top top;


	public VistaConsola(){
		System.out.println("Inicio la vista en la consola");
	}

	//Metodo totalmente legal, presente en el ejemplo rmimvc chat 
	public void setControlador(ControladorConsola controlador) {
		this.controlador = controlador;	
	}
	public void setLargo(int largo){
		this.largoTablero = largo;
	}

	public void clickListo() {
		if (controlador != null) {
			controlador.jugadorListo();
		}
	}


	//Metodo inicial para obtener un nombre:
	//
	private static final Scanner scanner = new Scanner(System.in);
	public String getNombre() {
    String nombre;
    do {
        System.out.print("Ingrese su nombre: ");
        nombre = scanner.nextLine().trim();
    } while (nombre.isEmpty());

    return nombre;
	}
	public void setNombreJugador(String nombre) {
		this.nombreJugador = nombre;
	}
	public void setTop(Top top){
		this.top = top;
	}
	public void setJugador(Jugador j){
		this.jugador = j;
	}
	public void setAdversarios(ArrayList<Jugador> adversarios){
		this.adversarios = adversarios;
	}
	//para mostrar errores:
	public void mostrarError(String mensaje) {
		System.out.println("Error, ocurrio un problema:");
		System.out.println(mensaje);
	}

	public boolean confirmarListo() {
    while (true) {
        System.out.print("¿Confirmar? (Y/N): ");
        String input = scanner.nextLine().trim().toUpperCase();
        if (input.equals("Y")) {
            return true;
        }
        if (input.equals("N")) {
            return false;
        }
        System.out.println("Entrada inválida. Escriba Y o N.");
    }
	}
	public void mostrarTop() {
        System.out.println("Top Historico");
		String historico = "";
		for(JugadorHistorico jh : this.top.getHistorico()){
			historico = historico +" "+ jh.getNombre()+" : "+jh.getPuntos()+" puntos\n";
		}
        System.out.println(historico);
	}

	public void loop(){
		Scanner sc = new Scanner(System.in);
		while(true){
			controlador.actualizarDatosVista();
			vistaPorDefecto();
			sc.nextLine();
		}
	}

	//pop up para mostrar estado de la sala 
	public void mostrarEstadoSala(EstadoSala estado) {
			System.out.println("Jugadores listos: "+ estado.listos+" / " + 
					estado.totalJugadores);
			if (!estado.faltan.isEmpty()) {
				System.out.println("Faltan:");
				for (String j : estado.faltan) {
					System.out.println(j);
				}
			}
	}

	public void mostrarTodosListos() {
		System.out.println("Todos están listos");
		System.out.println("La partida va a comenzar");
	}

	public void actualizarSala(int listos, int totalJugadores, java.util.List<String> faltan) {
		 System.out.println("Listos: " + listos + " / " + totalJugadores);
	}


	// Cambio de ventana de juego 
	//
	public void cambiarAEscenarioJuego() {
		System.out.println("A jugar!!!");
	}


	//GUI
	private int slotSeleccionado = 0;
	private void verTablero(){
		System.out.println("Tablero de Juego:");
		int columnas = largoTablero + 2;
		int filas = 5;
		int botonNro = 0;
		String textoBoton = "";
		for(int f = 0; f < filas; f++){
			for (int c = 0; c < columnas; c++){
				int botonNroLocal = botonNro; //Importante
				if(tableroJuego.getSlot(botonNro).taOcupao() == true){
					//esto realmente tiene que pedir el array de slots
					textoBoton = tableroJuego.getSlot(botonNro).getCartaAlojadaEnSlot().getForma();
					System.out.print(textoBoton);
				}else{
					textoBoton = " ";
					System.out.print(textoBoton);
				}
				botonNro++;
			}
			System.out.println("");
		} 
	}

	/// por cada interaccion, se muestra denuevo la vista
	public void vistaPorDefecto(){
		//this.espacioBlanco();
		this.verCartasJugador();
		this.verJugadorActual();
		this.verOtrosJugadores(); //incompleto 
		this.verOrosJugador();
		this.verRestriccionesJugador();
		this.verTablero();
		this.verCantidadDeCartasRestantes();
		this.opcionesBotones();
	}
	public void iniciarJuego(int largo){
		this.largoTablero = largo;
		System.out.println("Bienvenido al juego de los saboteadores");
	}


	public void actualizarTablero(Slots_tablero tablero){
		this.tableroJuego = tablero;
	}
	public void actualizarCartasRestantes(int cantidad){
		this.cartasRestantesMazo = cantidad;
	}
	public void jugadorSiguente(String j){
		this.jugadorActual = j;
	}
	private void verJugadorActual(){
		System.out.println("Estas controlando a :" + this.jugador.getNombre());
		System.out.println("Que tiene el rol de:" +this.jugador.getRol().name() );
	}
	private void verCartasJugador(){ 
		//las cartas tienen ID distinto al del modelo, no pasan por RMI
		int i = 0; //Identificador
		System.out.println("Mano de este jugador:");
		for(Carta c : jugador.getManoJugador()){
			System.out.println( i +" "+cartaAString(c) );
			i++;
		}
	}
	private void verOtrosJugadores(){
		System.out.println("Jugadores disponibles");
		int i = 0;
		//Primera Opcion es el propio jugador 
		System.out.println("Opcion "+ i+ " "+  jugador.getNombre() +" El propio jugador." );
		i++;
		for(Jugador j : this.adversarios){

			System.out.println("Opcion "+ i+ " "+  j.getNombre() );
			if(j.tieneRestricciones() == false ){
				System.out.println("Tiene restricciones");
				for(CartaSabotaje s: j.getRestriccionesJugador()){
					System.out.println( s.getAccion().toString());
				}
			}else{
				System.out.println("Sin Restricciones");
			}
			i++;
		}
	}

	private void verOrosJugador(){
		System.out.println("Oros vistos:");
		int i = 1;
		String aux = "";
		if(jugador.getOrosJugador().isEmpty()){
			System.out.println("Todavia no hay oros vistos");
		}else{
			System.out.println("Oros vistos");
			for(Carta_Camino c : jugador.getOrosJugador()){
				aux = i+ " "+ cartaAString(c) + c.getForma(); 
				if(c.getOro()){
					aux = aux +"Oro";
				}else{
					aux = aux +"Carbon";
				}
				System.out.println(aux);
				i++;
			}
		}
	}
	private void verRestriccionesJugador(){
		System.out.println("Usted Tiene las siguientes restricciones:");
		String aux = "";
		int i = 1;
		if(jugador.getRestriccionesJugador().isEmpty()){
			System.out.println("Todavia no hay restricciones");
		}else{
			System.out.println("Restricciones:");
			for(CartaSabotaje c : jugador.getRestriccionesJugador()){
				aux = i+ " " + c.getAccion().name(); 
				System.out.println(aux);
				i++;
			}
		}
	}
	private void verCantidadDeCartasRestantes(){
		System.out.println("Cantidad de cartas restantes: " +this.cartasRestantesMazo);
	}
	public void opcionesBotones(){
		if(this.jugadorActual.equals(this.jugador.getNombre())){
		System.out.println("Que Queres hacer?");
		System.out.println("1. Poner una carta sobre un jugador");
		System.out.println("2. Poner una carta en el tablero");
		System.out.println("3. Descartar una carta");
		int opcion = pedirOpcion(1, 3);
		int nroCarta = 0;
		int nroSlot = 0;
		String nombreJugadorSeleccionado = "";
		switch(opcion){
			case 1:
				System.out.println("Ingresa el nro de carta:");
				nroCarta = pedirOpcion(0, this.jugador.getManoJugador().size() -1 );
				nombreJugadorSeleccionado = pedirTexto("Ingresa el nombre del jugador:");
				controlador.ponerCartaSobreJugador(nroCarta, nombreJugadorSeleccionado);
				break;
			case 2:
				System.out.println("Ingresa el nro de carta:");
				nroCarta = pedirOpcion(0, this.jugador.getManoJugador().size() -1 );
				System.out.println("Ingresa del slot del tablero");
				nroSlot = (pedirOpcion(1, 50) + 0  ) ;
				controlador.ponerCartaSobreTablero(nroCarta, nroSlot);
				break;
			case 3:
				System.out.println("Ingresa el nro de la carta a Descartar");
				nroCarta = pedirOpcion(0, this.jugador.getManoJugador().size() -1);
				controlador.descartarCarta(nroCarta);
				break;

		}
		}else{
			System.out.println("No es mi turno, es turno de:"+ this.jugadorActual );
		}
	}
	private int pedirOpcion(int min, int max) {
		while (true) {
			System.out.print("Ingrese una opción (" + min + "-" + max + "): ");
			if (scanner.hasNextInt()) {
				int opcion = scanner.nextInt();
				scanner.nextLine(); // limpiar el enter pendiente
				if (opcion >= min && opcion <= max) {
					return opcion;
				}
			} else {
				scanner.nextLine(); // limpiar entrada inválida
			}
        System.out.println("Opción inválida. Intente nuevamente.");
		}
	}
	private String pedirTexto(String mensaje) {
	    String texto;
		while (true) {
			System.out.print(mensaje + ": ");
			texto = scanner.nextLine().trim();
			if (!texto.isEmpty()) {
				return texto;
			}
			System.out.println("El texto no puede estar vacío. Intente nuevamente.");
		}
	}

	private String cartaAString(Carta c){
		String salida = "error";
		// dependiendo de carta, sera el string 
		if(c instanceof Carta_Camino){
			//camino o callejon 
			Carta_Camino camino = (Carta_Camino) c;
			salida = camino.getForma() ;
		}
		if(c instanceof CartaSimple){
			CartaSimple simple = (CartaSimple) c;
			if(simple.esVista()){
				salida = "Ojo";
			}else{
				salida = "Derrumbe";
			}
		}
		if(c instanceof CartaSabotaje){
			CartaSabotaje sabos = (CartaSabotaje) c;
			salida = "Sabotaje: "+ sabos.getAccion(); 
		}
		if(c instanceof CartaReparacion){
			CartaReparacion repa = (CartaReparacion) c;
			salida = "Reparacion: "+ repa.getReparacion1(); 
			if(repa.getReparacion2() != CartaAccionTipo.VACIO){
				salida = salida + " y "+ repa.getReparacion2();
			}
		}
		return salida;
	}
	public void mostrarFinDeJuego(String mensaje) {
		System.out.println("Fin del juego");
		System.out.println("La partida terminó");
		System.out.println(mensaje);
        System.exit(0);
	}

}
