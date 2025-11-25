package saboteadores.tablero;

import saboteadores.mazo.cartas.Carta_Camino;
import saboteadores.enums.CartaTipo;

public class Slot {
	private Carta_Camino carta_alojada;
	private boolean arriba;
	private boolean abajo;
	private boolean izquierda;
	private boolean derecha;
	private boolean ocupao;
	private String forma;
	public Slot(boolean ar,boolean ab,boolean iz,boolean de){
		this.forma = " ";
		this.ocupao = false;
		this.arriba = ar;
		this.abajo = ab;
		this.izquierda = iz;
		this.derecha = de;
	}
	public Slot(Carta_Camino carta, boolean ar,boolean ab,boolean iz,boolean de){
		this.forma = carta.getForma();
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
	public Carta_Camino getCartaAlojadaEnSlot(){
		return this.carta_alojada;
	}
	public void alojarCarta(Carta_Camino carta){
		this.carta_alojada = carta;
		this.ocupao = true;
	}
	public void eliminarCarta(){
		this.carta_alojada = null;
		this.ocupao = false;
	}
	public boolean gA(){return this.arriba;}
	public boolean gB(){return this.abajo;}
	public boolean gI(){return this.izquierda;}
	public boolean gD(){return this.derecha;}
}
