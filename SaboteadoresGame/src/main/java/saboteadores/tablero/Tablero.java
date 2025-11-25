package saboteadores.tablero;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

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
import saboteadores.mazo.cartas.CartaSabotaje;

public class Tablero implements Observable {

	private List<Observador> observadores;

	private boolean jugable;
	private boolean esperandoAccion = false;
	//private ArrayList<Carta> mazo;
	private Mazo mazo;
	private Slots_tablero slots;
	private ArrayList<Jugador> jugadores;
	private Jugador jugadorActual;
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
		int turnoActual = 0;
		while(jugable){
			this.jugadorActual = jugadores.get(turnoActual);
			// acciones
			notificarObservadores();
			esperandoAccion = true;
			while(esperandoAccion && jugable){
				try {
					Thread.sleep(50);  // evita usar CPU a lo loco
				}catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			notificarObservadores();
			if(!jugable) break;
			turnoActual = (turnoActual+1)%jugadores.size();
		}
		System.out.println("El juego termino");
	}



	public Carta quieroVerOro(Carta carta, int pos, String quien ){
		// Se devuelven 2 cartas
		//if la carta es de ver
		//devuelve una copia de la carta meta 
		//osea, el jugador puede guardarla que es
		//si no es, deberia devolver error, como jugada imposible
		Carta cartita = carta;
		if ( pos == 8 | pos == 26 | pos == 44 ){
			if(carta.getTipo() == CartaTipo.VISTA){
				for(Jugador j: jugadores){
					if(j.getNombre() == quien){
						j.agregarOrosVistos(this.slots.getSlot(pos).getCartaAlojadaEnSlot() );
						cartita = mazo.getCarta();
						//HUBO CAMBIOS
						notificarObservadores();
					}
				}
			}
		}
		return cartita;
	}
	public Slot getSlot(int pos){
		return this.slots.getSlot(pos);
	}

	public boolean sePuedePoner(Carta_Camino carta, int pos){

		//chequeo la posicion, los arriba y abajo de las cartas aledañas
		//
		return true;
	}
	public boolean taOcupao(int pos){
		return slots.getSlot(pos).taOcupao();
	}
	public void jugarCarta(Carta_Camino carta){
		//si se puede jugar la carta en el tablero, 
		//se coloca la carta y se devuelve otra del mazo
		//
		//return mazo.get(0);
	}
	public Jugador getJugadorActual(){
		return this.jugadorActual;
	}
	public int getCantidadRestanteMazo(){
		return this.mazo.getCantidadRestanteMazo();
	}

	public Carta ponerCartaSobreJugador(Carta carta, String jugadorObjetivo){
		Carta devuelve = carta;
		if(carta.getTipo() == CartaTipo.SABOTAJE && carta instanceof CartaSabotaje){
			CartaSabotaje cartita = (CartaSabotaje) carta;
			for(Jugador j: jugadores){
				if(j.getNombre() == jugadorObjetivo){
					j.agregarRestriccion(cartita);
					devuelve = mazo.getCarta();
					//Hubo cambios
					notificarObservadores();
				}
			}
		}
		return devuelve;
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
