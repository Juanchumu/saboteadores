package saboteadores.tablero;
import java.util.ArrayList;
import java.util.List;

import saboteadores.enums.Rol;
import saboteadores.enums.CartaTipo;
import saboteadores.mazo.cartas.Carta_Camino;
import saboteadores.mazo.cartas.Carta_Oro;
import saboteadores.mazo.Mazo;
import saboteadores.mazo.MazoPepitas;
import saboteadores.tablero.Slot;
import saboteadores.tablero.Slots_tablero;
import saboteadores.observer.Observable;
import saboteadores.observer.Observador;
import saboteadores.Jugador;


import saboteadores.mazo.cartas.Carta;
import saboteadores.mazo.cartas.CartaReparacion;
import saboteadores.mazo.cartas.CartaSabotaje;
import saboteadores.mazo.cartas.CartaSimple;

public class Tablero implements Observable {

	private List<Observador> observadores;

	private boolean jugable;
	private Mazo mazo;
	private Slots_tablero slots;
	private ArrayList<Jugador> jugadores;
	private Jugador jugadorActual;
	private int turnoActual = 0;
	private ArrayList<String> jugadoresNuevos;

	//Cartas de oros para los mineros 
	private MazoPepitas mazoPepitasMineros;

	private int largoTablero; 

	public Tablero(){
		//Configuracion de pruebas 
		this.largoTablero = 2; //MAX 7 MIN 1 
		this.observadores = new ArrayList<Observador>();
		this.incializar();
		//this.descartarMuchasCartas(45); //Descarta 45 cartas  MAX 45 MIN 1
	}
	public int getLargo(){
		return this.largoTablero;
	}
	public boolean esJugable(){
		return this.jugable;
	}
	private void descartarMuchasCartas(int cantidad){
		for(int i = 0; i<cantidad;i++){
			this.mazo.eliminarCarta();
		}
	}

	public void agregarJugadores(ArrayList<String> jugadoresNuevos){
		this.jugadoresNuevos = jugadoresNuevos;
		this.inicializarJugadores(jugadoresNuevos);
		this.iniciarJuego();
	}
	public void incializar(){
		slots = new Slots_tablero(largoTablero);
		//mazo.clear();
		//mazo = new Mazo().getMazo();
		mazo = new Mazo();
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

		ArrayList<Carta> mano = new ArrayList<Carta>();
		int contador = 0;
		for(int i = 0; i < nroSaboteadores; i++ ){
			mano.clear();
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
			mano.clear();
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
	public void iniciarJuego(){
		this.jugable = true;
		this.jugadorActual = jugadores.get(turnoActual);
		notificarObservadores();
	}
	public void siguienteTurno(){
		if(this.mazo.getCantidadRestanteMazo() == 0){
			ganaronLosImpostores();
		}else{ 
			turnoActual = (turnoActual+1)%jugadores.size();
			this.jugadorActual = jugadores.get(turnoActual);
		}
	}
	public void ganaronLosImpostores(){
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
				System.out.println(" Ahora tiene "+j.getPuntos()+" puntos");

			}
		}
	}
	public void ganarPartida(){
		if(jugadorActual.getRol() == Rol.ENANO){
			this.jugable = false;
			int puntuacion = 0; 
			System.out.println("El juego termino");
			for(Jugador j: jugadores){
				if(j.getRol() == Rol.ENANO){
					if(j.getNombre().equalsIgnoreCase(jugadorActual.getNombre())){
						System.out.print("El jugador "+j.getNombre()+" Gano ");	
						puntuacion = mazoPepitasMineros.getMaxPepita();
						System.out.println( puntuacion +" Puntos");
						j.sumarPuntosJugador(puntuacion);
						System.out.println(" Ahora tiene "+j.getPuntos()+" puntos");
					}else{
						System.out.print("El jugador "+j.getNombre()+" Gano ");	
						puntuacion = mazoPepitasMineros.getSobras();
						System.out.println( puntuacion +" Puntos");
						j.sumarPuntosJugador(puntuacion);
						System.out.println(" Ahora tiene "+j.getPuntos()+" puntos");
					}
				}
			}
		}
	}

	public Slots_tablero getTableroConSlots(){
		return this.slots;
	}
	public boolean quieroVerOro(Carta carta, int pos, String NombreJugadorJugando ){
		// Se devuelven 2 cartas
		//if la carta es de ver
		//devuelve una copia de la carta meta 
		//osea, el jugador puede guardarla que es
		//si no es, deberia devolver error, como jugada imposible
		//
		//
		boolean sePudoVerUnaMeta = false; 
		if(carta instanceof CartaSimple){
			CartaSimple cartita = (CartaSimple) carta;
			if ( pos == (largoTablero +1) | 
					pos == (largoTablero*3 + 5) | 
					pos == (largoTablero *5 +9) ){ //44 
				if(cartita.esVista()){
					for(Jugador j: jugadores){
						if(j.getNombre() == NombreJugadorJugando){

							//Primer Carta devuelta 
							j.agregarOrosVistos(this.slots.getSlot(pos).getCartaAlojadaEnSlot() );
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
		/*
		if(fallido){
			//intento fallido 
			borrarCartaYDarUnaNueva(carta,NombreJugadorJugando);
			siguienteTurno();
			notificarObservadores();
		}
		*/
	}

	public boolean checkLibreDeRestricciones(String NombreJugadorJugando){
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

	public void ponerCartaTablero(Carta carta, int pos, String quien){
		//si el jugador tiene una restriccion, no deberia poder
		//realizar la accion de colocar una carta 
		boolean descarte = true; 
		if(checkLibreDeRestricciones(quien)){
			
			//Carta de derrumbe
			if(this.derrumbar(carta, pos, quien)){
				System.out.println("Se pudo derrumbar");
			}
			//Carta de vista 
			if(this.quieroVerOro(carta, pos, quien)){
				System.out.println("Se pudo ver");
			}


			if(carta instanceof Carta_Camino){
				Carta_Camino camino = (Carta_Camino) carta;
				//si se puede poner, se pone
				if(this.slots.getSlot(pos).alojarCarta(camino) ){
					//se pudo poner 
					System.out.println("sepudoponer");
					//this.slots.getSlot(pos).alojarCarta(camino);
					//se chequea si conecta con la meta
					if(this.slots.getSlot(pos).slotConectadoALaMeta()){
						ganarPartida();
					}
					if(this.jugable){
						//si es carbon se sigue
						borrarCartaYDarUnaNueva(carta, quien);
						siguienteTurno();
						notificarObservadores();
					}else{
						notificarObservadores();
					}
					descarte = false;
				}else{
					System.out.println(" NO sepudoponer");
				}
			}
		}
		if(descarte){	
			System.out.println("Se descarta y se da una carta nueva");
			borrarCartaYDarUnaNueva(carta, quien);
			siguienteTurno();
			notificarObservadores();
		}
	}
	public boolean derrumbar(Carta carta, int pos, String quien){
		boolean seDerrumboElCamino = false;
		System.out.println("1se ingreso la posicion "+pos+" tanto");
		if(carta instanceof CartaSimple){
			System.out.println("2se ingreso la posicion "+pos+" tanto");
			CartaSimple cartita = (CartaSimple) carta;
			System.out.println("3se ingreso la posicion "+pos+" tanto");

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
	public void descartarCarta(Carta carta, String quien){
		borrarCartaYDarUnaNueva(carta, quien);
		siguienteTurno();
		notificarObservadores();
	}
	public Jugador getJugadorActual(){
		return this.jugadorActual;
	}
	public int getCantidadRestanteMazo(){
		return this.mazo.getCantidadRestanteMazo();
	}
	public void borrarCartaYDarUnaNueva(Carta carta, String jugador){
		//si no hay cartas en el mazo, solo se borra la carta 

		for (Jugador j : jugadores) {
			if (j.getNombre().equalsIgnoreCase(jugador)) {
				ArrayList<Carta> mano = j.getManoJugador();
				// Buscamos la carta por ObjectId
				Carta cartaEncontrada = null;
				for (Carta c : mano) {
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
				return;  // ya encontramos al jugador, salimos del método
			}
		}
	}


	public void ponerCartaSobreJugador(Carta carta, String jugadorObjetivo, String quien){
		if(carta instanceof CartaSabotaje){
			CartaSabotaje cartita = (CartaSabotaje) carta;
			for(Jugador j: jugadores){
				if(j.getNombre() == jugadorObjetivo){
					j.agregarRestriccion(cartita);
					//Hubo cambios
					borrarCartaYDarUnaNueva(carta,quien);
					siguienteTurno();
					notificarObservadores();
				}
			}
		}else{
			if(carta instanceof CartaReparacion){
				CartaReparacion cartita = (CartaReparacion) carta;
				for(Jugador j: jugadores){
					if(j.getNombre() == jugadorObjetivo){
						j.quitarRestriccion(cartita);
						//Hubo cambios
						borrarCartaYDarUnaNueva(carta,quien);
						siguienteTurno();
						notificarObservadores();
					}
				}
			}else{
				//intento fallido
				borrarCartaYDarUnaNueva(carta,quien);
				siguienteTurno();
				notificarObservadores();
			}
		}

	}

	@Override
	public void agregarObservador(Observador observador){
		observadores.add(observador);
	}
	@Override
	public void quitarObservador(Observador observador){
		observadores.remove(observador);
	}
	@Override
	public void notificarObservadores(){
		for(Observador observador: observadores){
			observador.actualizar();
		}
	}

}
