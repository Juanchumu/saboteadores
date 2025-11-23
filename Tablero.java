package sab;
import java.util.ArrayList;
import java.util.Collections;

import sab.Carta_Camino;
import sab.Carta_Oro;
import sab.Mazo;
import sab.Slots_tablero;

public class Tablero {
	private boolean jugable;
	private ArrayList<Carta> mazo;
	private ArrayList<Carta_Oro> mazo_oro;
	private ArrayList<Slot> slots;

	public Tablero(){
		mazo = new Mazo().getMazo();
		slots = new Slots_tablero().getSlots();
		this.incializar();
	}
	public void incializar(){
		slots.clear();
		mazo.clear();
		jugable = true;
	
	}


	
	public Carta quieroVerOro(Carta carta, int pos){
		//if la carta es de ver
		//devuelve una copia de la carta meta 
		//osea, el jugador puede guardarla que es
		//si no es, deberia devolver error, como jugada imposible
	}

	public boolean sePuedePoner(Carta_Camino carta, int pos){
		
		//chequeo la posicion, los arriba y abajo de las cartas aledañas
		//
		return true;
	}
	public boolean taOcupao(int pos){
		return slots.get(pos).taOcupao();
	}
	public Carta jugarCarta(Carta_Camino carta){
		//si se puede jugar la carta en el tablero, 
		//se coloca la carta y se devuelve otra del mazo
		//
		return mazo.get(0);
	}
	
}
