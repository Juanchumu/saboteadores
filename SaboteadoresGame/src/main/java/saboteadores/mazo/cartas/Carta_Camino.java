package saboteadores.mazo.cartas;
//import sab.Posiciones;

import saboteadores.enums.CartaTipo;
public class Carta_Camino extends Carta {
	private String forma;
	private boolean arriba;
	private boolean abajo;
	private boolean izquierda;
	private boolean derecha;
	public Carta_Camino(
			CartaTipo tipo, //callejon sin salida o camino 
			boolean ar, 
			boolean ab,
			boolean iz,
			boolean de,
			String f){
		super(tipo);
		this.forma = f;
		this.arriba = ar;
		this.abajo = ab;
		this.izquierda = iz;
		this.derecha = de;
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
	public String getForma(){
		return this.forma;
	}
}
