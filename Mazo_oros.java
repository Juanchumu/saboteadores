package sab;

import java.util.ArrayList;

//import sab.Carta_Oro;

public class Mazo_oros {
	private ArrayList<Carta_Oro> mazo_oro;
	public Mazo_oros(){
		this.oros();
	}
	private void oros(){
		this.mazo_oro = new ArrayList<Carta_Oro>(); 
		this.mazo_oro.add(new Carta_Oro(CartaTipo.CAMINO, true, true, true, true, false));
		this.mazo_oro.add(new Carta_Oro(CartaTipo.CAMINO, true, true, true, true, false));
		this.mazo_oro.add(new Carta_Oro(CartaTipo.CAMINO, true, true, true, true, true));
		//mezclarthis.mazo_oro
	}
	public ArrayList<Carta_Oro> get_mazo_oros(){
		return this.mazo_oro;
	}
}
