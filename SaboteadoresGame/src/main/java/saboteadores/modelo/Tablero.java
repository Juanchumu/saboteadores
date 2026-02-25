package saboteadores.modelo;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import saboteadores.modelo.enums.Rol;
import saboteadores.modelo.enums.CartaTipo;
import saboteadores.modelo.mazo.cartas.Carta_Camino;
import saboteadores.modelo.mazo.cartas.Carta_Oro;
import saboteadores.modelo.mazo.Mazo;
import saboteadores.modelo.mazo.MazoPepitas;
import saboteadores.modelo.Slot;
import saboteadores.modelo.Slots_tablero;
import saboteadores.modelo.observer.Observable;
import saboteadores.modelo.observer.Observador;
import saboteadores.modelo.Jugador;


import saboteadores.modelo.mazo.cartas.Carta;
import saboteadores.modelo.mazo.cartas.CartaReparacion;
import saboteadores.modelo.mazo.cartas.CartaSabotaje;
import saboteadores.modelo.mazo.cartas.CartaSimple;


import ar.edu.unlu.rmimvc.observer.ObservableRemoto;

import java.io.*;
public class Tablero extends ObservableRemoto implements ITablero, Serializable {
	private static final long serialVersionUID = 1L;

	//private List<Observador> observadores;

	private boolean jugable;
	private Mazo mazo;
	private Slots_tablero slots;
	private ArrayList<Jugador> jugadores;
	private Jugador jugadorActual;
	private int turnoActual = 0;
	private ArrayList<String> jugadoresNuevos;
	private ArrayList<String> jugadoresListos;

	//Cartas de oros para los mineros 
	private MazoPepitas mazoPepitasMineros;

	private int largoTablero;

	private Top top;

	private void reiniciarElJuego(){
		System.out.println("Se reinicio el juego.");
		this.incializar();
		this.jugadores.clear();
		this.jugadoresListos.clear(); 
		this.jugadoresNuevos.clear();
	}

	public Tablero(Top top ){
		this.top = top;
		jugadoresListos = new ArrayList<>();
		jugadoresNuevos = new ArrayList<>();
		//Configuracion de pruebas 
		this.largoTablero = 2; //MAX 7 MIN 1 
		//this.descartarMuchasCartas(45); //Descarta 45 cartas  MAX 45 MIN 1
		this.jugadoresListos.add("Carlos");
		this.jugadoresListos.add("Juan");
		this.jugadoresListos.add("Thiago");
		this.jugadoresNuevos.add("Carlos");
		this.jugadoresNuevos.add("Juan");
		this.jugadoresNuevos.add("Thiago");

		//this.observadores = new ArrayList<Observador>();
		//try{
		//this.incializar();
		//}catch (RemoteException e){e.printStackTrace();}
	}
	public int getLargo(
			) throws RemoteException {
		return this.largoTablero;
	}
	public boolean esJugable(
			) throws RemoteException {
		return this.jugable;
	}
	private void descartarMuchasCartas(int cantidad){
		for(int i = 0; i<cantidad;i++){
			this.mazo.eliminarCarta();
		}
	}
	public Top getTop() throws RemoteException{
		return this.top;
	}
	public Jugador devolverJugador(String nombre) throws RemoteException {
		Jugador aux = new Jugador("Steve", null, null);
		for(Jugador j : this.jugadores){
			//System.out.println("ciclo:"+ j.getNombre() );
			if(j.getNombre().equals(nombre)){
				aux = j;
			}
		}
		//System.out.println("Se devolvio a:"+ aux.getNombre() );
		return aux;
	}
	public ArrayList<Jugador> getListaAdversarios(String quien) throws RemoteException {
		ArrayList<Jugador> adversarios = new ArrayList<Jugador>();
		for(Jugador j : this.jugadores){
			if(j.getNombre().equals(quien) == false ){
				adversarios.add(j);
			}
		}
		return adversarios;
	}
	public boolean hayJugadores(
			) throws RemoteException {
		//Hay jugadores = true 
		return( this.jugadores != null );
	}
	public boolean estoyLogeado(String nombre) throws RemoteException{
		return this.jugadoresNuevos.contains(nombre);
	}
	public void agregarJugador(String nombre) throws RemoteException {
		if (nombre == null || nombre.isBlank()) {
			throw new IllegalArgumentException("El nombre no puede estar vacío");
		}
		if (jugadoresNuevos.contains(nombre)) {
			throw new IllegalArgumentException("Ese nombre ya está en uso");
		}
		jugadoresNuevos.add(nombre);
		System.out.println(nombre+ " se unio a la partida");
	}

	public void agregarJugadores(ArrayList<String> jugadoresNuevos
			) throws RemoteException {
		this.jugadoresNuevos = jugadoresNuevos;

		//estos 2 se necesitan
		
	}
	public void incializar(){
		this.jugable = false;
		slots = new Slots_tablero(largoTablero);
		mazo = new Mazo();
	}
	public EstadoSala marcarListo(String nombre) throws RemoteException {
		jugadoresListos.add(nombre);
		List<String> faltan = new ArrayList<>();
		for (String j : jugadoresNuevos) {
			if (!jugadoresListos.contains(j)) {
				faltan.add(j);
			}
		}
		return new EstadoSala(jugadoresNuevos.size(), jugadoresListos.size(), faltan);
	}

	private void inicializarJugadores(ArrayList<String> jug){
		this.jugadores = new ArrayList<Jugador>();
		int nroCartas = 0;
		int nroMineros = 0;
		int nroSaboteadores = 0;
		switch(jug.size()){
			case 3:
				nroCartas = 6;
				nroSaboteadores = 1;
				nroMineros = 2;
				break;
			case 4:
				nroCartas = 6;
				nroSaboteadores = 1;
				nroMineros = 3;
				break;
			case 5:
				nroCartas = 6;
				nroSaboteadores = 2;
				nroMineros = 3;
				break;
			case 6:
				nroCartas = 5;
				nroSaboteadores = 2;
				nroMineros = 4;
				break;
			case 7:
				nroCartas = 5;
				nroSaboteadores = 3;
				nroMineros = 4;
				break;
			case 8:
				nroCartas = 4;
				nroSaboteadores = 3;
				nroMineros = 5;
				break;
			case 9:
				nroCartas = 4;
				nroSaboteadores = 3;
				nroMineros = 6;
				break;
			case 10:
				nroCartas = 4;
				nroSaboteadores = 4;
				nroMineros = 6;
				break;
		}
		//Generacion del mazoPepitasMineros
		mazoPepitasMineros = new MazoPepitas(nroMineros);

		//ArrayList<Carta> mano = new ArrayList<Carta>();
		ArrayList<Carta> mano;
		int contador = 0;
		for(int i = 0; i < nroSaboteadores; i++ ){
			mano = new ArrayList<Carta>();
			//mano.clear();
			for(int j = 0; j < nroCartas; j++){
				if(mazo.hayCartasEnElMazo() == false){
					break;
				}
				mano.add(mazo.getCarta());
			}
			this.jugadores.add(new Jugador(jug.get(contador), mano, Rol.IMPOSTOR));
			contador++;
		}
		for(int i = 0; i < nroMineros; i++ ){
			mano = new ArrayList<Carta>();
			//mano.clear();
			for(int j = 0; j < nroCartas; j++){
				if(mazo.hayCartasEnElMazo() == false){
					break;
				}
				mano.add(mazo.getCarta());
			}
			this.jugadores.add(new Jugador(jug.get(contador), mano, Rol.ENANO));
			contador++;
		}
	}
	public void intentarArrancarElJuego() throws RemoteException{
		System.out.println("Se intenta arrancar el juego");
		Collections.sort(this.jugadoresListos);
		Collections.sort(this.jugadoresNuevos);
		//tiene que haber 4 jugadores
		if(this.jugadoresListos.get(3) != null){
		if (this.jugadoresListos.equals( this.jugadoresNuevos )) {
			System.out.println("Ya estan todos listos");
			this.iniciarJuego();
		}
		}
	}
	public void iniciarJuego() throws RemoteException {
		System.out.println("El Juego INICIO");
		this.inicializarJugadores(jugadoresNuevos);
		System.out.println("Se repartieron los roles");
		this.jugable = true;
		this.jugadorActual = jugadores.get(turnoActual);
		guardarEstado();
		notificarObservadores();//esto tiene que cambiar la pantalla de juego
	}
	private void siguienteTurno(){
		if(this.mazo.getCantidadRestanteMazo() == 0){
			ganaronLosImpostores();
		}else{ 
			turnoActual = (turnoActual+1)%jugadores.size();
			this.jugadorActual = jugadores.get(turnoActual);
		}
	}
	private void ganaronLosImpostores(){
		System.out.println("No hay mas cartas");
		System.out.println("Ganaron los impostores");
		this.jugable = false;
		//encontrar al impostor 
		for(Jugador j: jugadores){
			if(j.getRol()== Rol.IMPOSTOR ){
				System.out.print("El jugador ");
				System.out.print(j.getNombre());
				System.out.println(" Gano 3 puntos");
				j.sumarPuntosJugador(3);
				this.top.agregarPuntosHistorico(j.getNombre(), 3);
				System.out.println(" Ahora tiene "+j.getPuntos()+" puntos");

			}
		}
		this.guardarTop();
		this.reiniciarElJuego();
	}
	private String partidaGanada = "";
	public String getPartidaGanada() throws RemoteException {
		return this.partidaGanada;
	}

	private void ganarPartida(){
		if(jugadorActual.getRol() == Rol.ENANO){
			this.jugable = false;
			int puntuacion = 0; 
			System.out.println("El juego termino");
			this.partidaGanada = "El juego termino\n";
			for(Jugador j: jugadores){
				if(j.getRol() == Rol.ENANO){
					if(j.getNombre().equalsIgnoreCase(jugadorActual.getNombre())){
						this.partidaGanada = this.partidaGanada + "El jugador "+j.getNombre()+" Gano \n";	
						System.out.print("El jugador "+j.getNombre()+" Gano ");	
						puntuacion = mazoPepitasMineros.getMaxPepita();

						this.partidaGanada = this.partidaGanada + puntuacion +" Puntos\n";
						System.out.println( puntuacion +" Puntos");
						j.sumarPuntosJugador(puntuacion);

						//Guardar en el historial
						this.top.agregarPuntosHistorico(j.getNombre(), puntuacion);

						this.partidaGanada = this.partidaGanada +" Ahora tiene "+j.getPuntos()+" puntos\n";
						System.out.println(" Ahora tiene "+j.getPuntos()+" puntos");
					}else{
						this.partidaGanada = this.partidaGanada +"El jugador "+j.getNombre()+" Gano\n";	
						System.out.print("El jugador "+j.getNombre()+" Gano ");	
						puntuacion = mazoPepitasMineros.getSobras();
						this.partidaGanada = this.partidaGanada + puntuacion +" Puntos\n";
						System.out.println( puntuacion +" Puntos");
						j.sumarPuntosJugador(puntuacion);
						//Guardar en el historial
						this.top.agregarPuntosHistorico(j.getNombre(), puntuacion);
						this.partidaGanada = this.partidaGanada + puntuacion +" Ahora tiene "+j.getPuntos()+" puntos\n";
						System.out.println(" Ahora tiene "+j.getPuntos()+" puntos");
					}
				}
			}
			this.guardarTop();
			this.reiniciarElJuego();
		}
	}

	public Slots_tablero getTableroConSlots(
			) throws RemoteException {
		return this.slots;
	}
	private Carta buscarCarta(int posCarta, String jugador){
		Carta encontrada = new Carta() ; //una carta nueva que no existe
		for(Jugador j : this.jugadores){
			if(j.getNombre().equals(jugador)){
				encontrada = j.getManoJugador().get(posCarta);
			}
		}
		return encontrada;

	}


	private boolean quieroVerOro(Carta carta, int pos, String NombreJugadorJugando ){
		//System.out.println("El jugador: "+NombreJugadorJugando+" esta viendo la meta"+
		//		pos);
		// Se devuelven 2 cartas
		//if la carta es de ver
		//devuelve una copia de la carta meta 
		//osea, el jugador puede guardarla que es
		//si no es, deberia devolver error, como jugada imposible
		//
		//
		boolean sePudoVerUnaMeta = false;
		//System.out.println("1");
		if(carta instanceof CartaSimple){
			//System.out.println("2");
			CartaSimple cartita = (CartaSimple) carta;
			if ( pos == (largoTablero +1) | 
					pos == (largoTablero*3 + 5) | 
					pos == (largoTablero *5 +9) ){ //44 
				//System.out.println("3");
				if(cartita.esVista()){
					for(Jugador j: jugadores){
						if(j.getNombre().equals(NombreJugadorJugando)){
							//System.out.println("4");

							//Primer Carta devuelta 
							j.agregarOrosVistos(this.slots.getSlot(pos).getCartaAlojadaEnSlot() );
							//System.out.println("5");
							//HUBO CAMBIOS
							//Segunda Carta Devuelta
							/*
							borrarCartaYDarUnaNueva(carta,NombreJugadorJugando);
							siguienteTurno();
							notificarObservadores();
							*/
							sePudoVerUnaMeta = true;
						}
					}
				}
			}
		}
		return sePudoVerUnaMeta;
	}

	private boolean checkLibreDeRestricciones(String NombreJugadorJugando){
		boolean estado = false;
		for(Jugador j: jugadores){
			if (j.getNombre().equalsIgnoreCase(NombreJugadorJugando)) {
				if(j.getRestriccionesJugador().isEmpty() ){
					estado = true;
				}
			}
		}
		return estado;
	}

	public void ponerCartaTablero(int posCarta, int pos, String quien
			) throws RemoteException {
		System.out.println("El jugador: " +quien+ " esta jugando una carta en la posicion "+ pos);
		Carta carta = buscarCarta(posCarta, quien);
		//System.out.println("Carta"+ posCarta );
		//si el jugador tiene una restriccion, no deberia poder
		//realizar la accion de colocar una carta 
		boolean descarte = true; 
		if(checkLibreDeRestricciones(quien)){
			
			//Carta de derrumbe
			if(this.derrumbar(carta, pos, quien)){
				System.out.println("y pudo derrumbar un camino");
			}
			//Carta de vista 
			if(this.quieroVerOro(carta, pos, quien)){
				System.out.println("y pudo ver una meta");
			}

			if(carta instanceof Carta_Camino){
				Carta_Camino camino = (Carta_Camino) carta;
				//si se puede poner, se pone
				if(this.slots.getSlot(pos).alojarCarta(camino) ){
					//se pudo poner 
					System.out.println("y logro colocar un camino");
					//this.slots.getSlot(pos).alojarCarta(camino);
					//se chequea si conecta con la meta
					if(this.slots.getSlot(pos).slotConectadoALaMeta()){
						ganarPartida();
					}
					if(this.jugable){
						//si es carbon se sigue
						borrarCartaYDarUnaNueva(carta, quien);
						siguienteTurno();
						guardarEstado();
						notificarObservadores();
					}else{
						guardarEstado();
						notificarObservadores();
					}
					descarte = false;
				}else{
					System.out.println("y no se pudo jugar la carta");
				}
			}
		}
		if(descarte){	
			System.out.println("Se descarta y se da una carta nueva");
			borrarCartaYDarUnaNueva(carta, quien);
			siguienteTurno();
			guardarEstado();
			notificarObservadores();
		}
	}
	private boolean derrumbar(Carta carta, int pos, String quien){
		boolean seDerrumboElCamino = false;
		//System.out.println("1se ingreso la posicion "+pos+" tanto");
		if(carta instanceof CartaSimple){
			//System.out.println("2se ingreso la posicion "+pos+" tanto");
			CartaSimple cartita = (CartaSimple) carta;
			//System.out.println("3se ingreso la posicion "+pos+" tanto");

			if( (pos !=  (largoTablero*2+4)  )&&
					(pos != (largoTablero+1)   )&&
					(pos != (largoTablero*3+5) )&&
					(pos != (largoTablero*5+9)  ) ){
				if(cartita.esDerrumbe() == true ){
					//se elimina la carta en este slot
					this.slots.getSlot(pos).eliminarCartaAlojada();	
					seDerrumboElCamino = true;
				}
			}
		}
		return seDerrumboElCamino;
		/*
		if(fallido){	
			System.out.println("ingreso fallido");
			borrarCartaYDarUnaNueva(carta, quien);
			siguienteTurno();
			notificarObservadores();
		}
		*/
	}
	public void descartarCarta(int posCarta, String quien
			) throws RemoteException {
		System.out.println("El jugador :" +quien+ " Descarto una carta.");
		Carta carta = buscarCarta(posCarta, quien);
		borrarCartaYDarUnaNueva(carta, quien);
		siguienteTurno();
		guardarEstado();
		notificarObservadores();
	}
	public String getJugadorSiguiente(
			) throws RemoteException {
		return this.jugadorActual.getNombre();
	}
	public int getCantidadRestanteMazo(
			) throws RemoteException {
		return this.mazo.getCantidadRestanteMazo();
	}
	private void borrarCartaYDarUnaNueva(Carta carta, String jugador){
		//si no hay cartas en el mazo, solo se borra la carta 
		//System.out.print("Carta a borrar:");
		//System.out.println(carta);
		for (Jugador j : jugadores) {
			if (j.getNombre().equalsIgnoreCase(jugador)) {
				ArrayList<Carta> mano = j.getManoJugador();
				// Buscamos la carta por ObjectId
				Carta cartaEncontrada = null;
				for (Carta c : mano) {
					//System.out.print("Carta iterada:");
					//System.out.println(c);
					if (c == carta ) { //MISMA INSTANCIA
						cartaEncontrada = c;
						break;
					}
				}
				if (cartaEncontrada != null) {
					// borrar la carta
					mano.remove(cartaEncontrada);
					// darle una nueva
					if(this.mazo.getCantidadRestanteMazo() == 0 ){
						System.out.println("No hay mas cartas");
					}else{
						Carta nueva = mazo.getCarta();
						mano.add(nueva);
					}
				}else{
					//Se recibio una carta que no esta en el mazo
					System.out.println("El jugador"+jugador+"Hizo trampa.");
					this.jugable = false;
				}
				//System.out.println("Carta Borrada");
				return;  // ya encontramos al jugador, salimos del método
			}
		}
	}


	public void ponerCartaSobreJugador(
			int posCarta,
			String jugadorObjetivo,
			String quien
			) throws RemoteException {
		Carta carta = buscarCarta(posCarta, quien);
		System.out.print("Poniendo carta sobre" + jugadorObjetivo+ " " );
		if(carta instanceof CartaSabotaje){
			System.out.println(" de Sabotaje");
			CartaSabotaje cartita = (CartaSabotaje) carta;
			for(Jugador j: jugadores){
				if(j.getNombre().equals(jugadorObjetivo)  ){
					j.agregarRestriccion(cartita);
					//Hubo cambios
					borrarCartaYDarUnaNueva(carta,quien);
					siguienteTurno();
					guardarEstado();
					notificarObservadores();
				}
			}
		}else{
			System.out.println(" de Reparacion");
			if(carta instanceof CartaReparacion){
				CartaReparacion cartita = (CartaReparacion) carta;
				for(Jugador j: jugadores){
					if(j.getNombre().equals(jugadorObjetivo) ){
						j.quitarRestriccion(cartita);
						//Hubo cambios
						borrarCartaYDarUnaNueva(carta,quien);
						siguienteTurno();
						guardarEstado();
						notificarObservadores();
					}
				}
			}else{
				System.out.println("  .. fallo.");
				//intento fallido
				borrarCartaYDarUnaNueva(carta,quien);
				siguienteTurno();
				guardarEstado();
				notificarObservadores();
			}
		}

	}
	private void guardarEstado() {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("tablero.dat"))) {
        out.writeObject(this);
    } catch (IOException e) {
        e.printStackTrace();}
    }
	private void guardarTop() {
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("top.dat"))) {
        out.writeObject(this.top);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

}
