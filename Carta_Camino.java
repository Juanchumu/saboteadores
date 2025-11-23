package sab;
//import sab.Posiciones;

public class Carta_Camino extends Carta {
	private boolean arriba;
	private boolean abajo;
	private boolean izquierda;
	private boolean derecha;
	public Carta_Camino(
			CartaTipo tipo, //callejon sin salida o camino 
			boolean ar, 
			boolean ab,
			boolean iz,
			boolean de){
		super(tipo);
		this.arriba = ar;
		this.arriba = ab;
		this.arriba = iz;
		this.arriba = de;
	}
	public boolean getArriba(){
		return this.arriba;
	}
	public boolean getAbajo(){
		return this.abajo;
	}
	public boolean getIzquierda(){
		return this.izquierda;
	}
	public boolean getDerecha(){
		return this.derecha;
	}
}
