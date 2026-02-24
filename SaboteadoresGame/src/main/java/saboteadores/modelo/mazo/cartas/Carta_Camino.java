package saboteadores.modelo.mazo.cartas;


import java.io.Serializable;

public class Carta_Camino extends CartaJugableEnTablero implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean callejon;
	private String forma;
	private boolean arriba;
	private boolean abajo;
	private boolean izquierda;
	private boolean derecha;
	
	private boolean meta;
	private boolean oro;
	//Constructor para una carta Meta 
	public Carta_Camino(boolean meta, boolean oro){
		//this.forma = "#";
		this.meta = meta;
		this.oro = oro;
		this.arriba = true;
		this.abajo = true;
		this.izquierda = true;
		this.derecha = true;
		if(oro){
			this.forma = "O";
		}else{
			this.forma = "C";
		}
	}
	//Constructor para una carta Camino 
	public Carta_Camino(
			boolean callejon,
			boolean ar, 
			boolean ab,
			boolean iz,
			boolean de,
			String f
			){
		this.forma = f;
		this.arriba = ar;
		this.abajo = ab;
		this.izquierda = iz;
		this.derecha = de;
		this.callejon = callejon;
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
	public boolean esUnCallejon(){
		return this.callejon;
	}
	public boolean esMeta(){
		return this.meta;
	}
	public boolean getOro(){
		return this.oro;
	}

}
