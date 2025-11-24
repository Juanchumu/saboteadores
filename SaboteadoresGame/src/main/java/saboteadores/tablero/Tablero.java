package saboteadores.tablero;
import java.util.ArrayList;
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

public class Tablero implements Observable {
	private boolean jugable;
	//private ArrayList<Carta> mazo;
	private Mazo mazo;
	private ArrayList<Carta_Oro> mazo_oro;
	private Slots_tablero slots;
	private ArrayList<Jugador> jugadores;
	private ArrayList<String> jugadoresNuevos;

	public Tablero(ArrayList<String> jugadoresNuevos){
		this.incializar();
		this.jugadoresNuevos = jugadoresNuevos;
		this.inicializarJugadores(this.jugadoresNuevos);
	}
	public void incializar(){
		slots = new Slots_tablero();
		//mazo.clear();
		//mazo = new Mazo().getMazo();
		mazo = new Mazo();
		this.jugable = true;
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



	public Carta quieroVerOro(Carta carta, int pos){
		//if la carta es de ver
		//devuelve una copia de la carta meta 
		//osea, el jugador puede guardarla que es
		//si no es, deberia devolver error, como jugada imposible
		if ( pos == 7 | pos == 21 | pos == 31 ){
			if(carta.getTipo() == CartaTipo.VISTA){
				return this.slots.getSlot(pos).getCartaAlojadaEnSlot();
				//carta jugada, recibir otra
			}
		}else{
			return carta; 
		}
		return carta;
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
	public Jugador getJugador(int nro){
		return jugadores.get(nro);
	}
	public int getCantidadRestanteMazo(){
		return this.mazo.getCantidadRestanteMazo();
	}

	@Override
	public void agregarObservador(Observador observador){

	}
	@Override
	public void quitarObservador(Observador observador){

	}
	@Override
	public void notificarObservadores(){

	}

}
