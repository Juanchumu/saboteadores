package sab;
//import sab.Posiciones;

import sab.Carta_Camino;

public class Carta_Oro extends Carta_Camino {
	private boolean oro;
	public Carta_Oro(
			CartaTipo tipo, //callejon sin salida o camino 
			boolean ar, 
			boolean ab,
			boolean iz,
			boolean de, boolean oro){
		super(tipo, ar, ab, iz, de);
		this.oro = oro;
	}
	public boolean getOro(){
		return this.oro;
	}
}
