package sab;

import sab.Carta_Camino;

public class Slot {
	private Carta_Camino carta_alojada;
	private boolean arriba;
	private boolean abajo;
	private boolean izquierda;
	private boolean derecha;
	private boolean ocupao; 
	public Slot(boolean ar,boolean ab,boolean iz,boolean de){
		this.ocupao = false;
		this.arriba = ar;
		this.abajo = ab;
		this.izquierda = iz;
		this.derecha = de;
	}
	public Slot(Carta_Camino carta, boolean ar,boolean ab,boolean iz,boolean de){
		this.ocupao = true;
		this.carta_alojada = carta;
		this.arriba = ar;
		this.abajo = ab;
		this.izquierda = iz;
		this.derecha = de;
	}
	public CartaTipo getTipo(){ return this.carta_alojada.getTipo();}
	public boolean taOcupao(){
		return this.ocupao;
	}
}
