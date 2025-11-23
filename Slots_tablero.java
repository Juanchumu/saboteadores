package sab;

import sab.Mazo_oros;

import java.util.ArrayList;
public class Slots_tablero { 
	private ArrayList<Slot> slots; // en cada slot, hay una carta,
	private ArrayList<Carta_Oro> mazo_oro; // en cada slot, hay una carta,
								   
	public Slots_tablero(){
		this.mazo_oro = new Mazo_oros().get_mazo_oros();
		this.crear_slots();
	}
	private void crear_slots(){
		slots = new ArrayList<Slot>();
		slots.add( new Slot(false,true,false,true));
		for(int i = 0; i < 7; i++ ){
			slots.add( new Slot(false,true,true,true));
		}
		slots.add( new Slot(mazo_oro.get(0), false,true,true,false));
		slots.add( new Slot(true,false,false,true));
		for(int i = 0; i < 9; i++ ){
			slots.add( new Slot(true,true,true,true));
		}
		slots.add( new Slot(false,false,true,false));
		//Entrada
		slots.add( new Slot( 
					new Carta_Camino(CartaTipo.CAMINO, true, true, true, true),
					true,true,false,true
					));
		slots.add( new Slot(true,true,false,true));
		for(int i = 0; i < 5; i++ ){
			slots.add( new Slot(true,true,true,true));
		}
		slots.add( new Slot(true,true,true,false));
		slots.add( new Slot(mazo_oro.get(1),true,true,true,false));
		slots.add( new Slot(false,true,false,true));
		for(int i = 0; i < 6; i++ ){
			slots.add( new Slot(true,true,true,true));
		}
		slots.add( new Slot(true,true,true,false));
		slots.add( new Slot(true,false,false,true));
		for(int i = 0; i < 5; i++ ){
			slots.add( new Slot(true,false,true,true));
		}
		slots.add( new Slot(mazo_oro.get(2), true,false,true,false ));
	}
	public ArrayList<Slot> getSlots(){
		return this.slots;
	}
}
