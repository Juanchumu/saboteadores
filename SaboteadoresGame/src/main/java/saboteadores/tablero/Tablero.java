package saboteadores.tablero;
import java.util.ArrayList;
import java.util.List;

import saboteadores.enums.Rol;
import saboteadores.enums.CartaTipo;
import saboteadores.mazo.cartas.Carta_Camino;
import saboteadores.mazo.cartas.Carta_Oro;
import saboteadores.mazo.Mazo;
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

	public Tablero(){
		this.observadores = new ArrayList<Observador>();
		this.incializar();
	}
	public void agregarJugadores(ArrayList<String> jugadoresNuevos){
		this.jugadoresNuevos = jugadoresNuevos;
		this.inicializarJugadores(jugadoresNuevos);
		this.iniciarJuego();
	}
	public void incializar(){
		slots = new Slots_tablero();
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
		if(this.mazo.getCantidadRestanteMazo() == 0 | this.jugable == false){
			System.out.println("El juego termino");
		}else{ 
			turnoActual = (turnoActual+1)%jugadores.size();
			this.jugadorActual = jugadores.get(turnoActual);
		}
	}
	public void ganarPartida(){
		if(jugadorActual.getRol() == Rol.ENANO){
			this.jugable = false;
			System.out.println("El juego termino");
			System.out.println("El jugador "+jugadorActual.getNombre()+" Gano");
		}
	}

	public Slots_tablero getTableroConSlots(){
		return this.slots;
	}



	public void quieroVerOro(Carta carta, int pos, String NombreJugadorJugando ){
		// Se devuelven 2 cartas
		//if la carta es de ver
		//devuelve una copia de la carta meta 
		//osea, el jugador puede guardarla que es
		//si no es, deberia devolver error, como jugada imposible
		//
		boolean fallido = true; 
		if(carta instanceof CartaSimple){
			CartaSimple cartita = (CartaSimple) carta;
			if ( pos == 8 | pos == 26 | pos == 44 ){
				if(cartita.esVista()){
					for(Jugador j: jugadores){
						if(j.getNombre() == NombreJugadorJugando){

							//Primer Carta devuelta 
							j.agregarOrosVistos(this.slots.getSlot(pos).getCartaAlojadaEnSlot() );
							//HUBO CAMBIOS
							//Segunda Carta Devuelta
							borrarCartaYDarUnaNueva(carta,NombreJugadorJugando);
							siguienteTurno();
							notificarObservadores();
							fallido = false;
						}
					}
				}
			}
		}

		if(fallido){
			//intento fallido 
			borrarCartaYDarUnaNueva(carta,NombreJugadorJugando);
			siguienteTurno();
			notificarObservadores();
		}
	}

	public void ponerCartaTablero(Carta carta, int pos, String quien){
		boolean fallido = true; 
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
				//si es carbon se sigue
				fallido = false;
				borrarCartaYDarUnaNueva(carta, quien);
				siguienteTurno();
				notificarObservadores();
			}else{
				System.out.println(" NO sepudoponer");
			}
		}
		if(fallido){	
			System.out.println("ingreso fallido");
			borrarCartaYDarUnaNueva(carta, quien);
			siguienteTurno();
			notificarObservadores();
		}
	}
	public void derrumbar(Carta carta, int pos, String quien){
		boolean fallido = true;
		if(carta instanceof CartaSimple){
			CartaSimple cartita = (CartaSimple) carta; 
		if( (pos != 18)&&(pos !=8)&&(pos !=26)&&(pos !=44) ){
			if(cartita.esDerrumbe() == true ){
				//se elimina la carta en este slot
				this.slots.getSlot(pos).eliminarCartaAlojada();	
				fallido = false;
			}
		}
		}

		if(fallido){	
			System.out.println("ingreso fallido");
			borrarCartaYDarUnaNueva(carta, quien);
			siguienteTurno();
			notificarObservadores();
		}
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
					Carta nueva = mazo.getCarta();
					mano.add(nueva);
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
