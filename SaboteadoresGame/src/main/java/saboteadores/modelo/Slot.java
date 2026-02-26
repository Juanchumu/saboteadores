package saboteadores.modelo;

import saboteadores.modelo.mazo.cartas.Carta;
import saboteadores.modelo.mazo.cartas.CartaSimple;
import saboteadores.modelo.mazo.cartas.Carta_Camino;
import saboteadores.modelo.enums.CartaTipo;
import saboteadores.modelo.enums.CartaOroTipo;


import java.io.Serializable;
public class Slot implements Serializable {
	private static final long serialVersionUID = 1L;
	//puede recibir cualquier tipo de carta 
	private Carta_Camino carta_alojada;
	private Slot arriba;
	private Slot abajo;
	private Slot izquierda;
	private Slot derecha;
	private String forma;
	private boolean estoyConectadoConLaMeta = false;


	public void eliminarCartaAlojada(){
		carta_alojada = null;
		this.forma = "";
	}

	// Cada slot tiene que recibir los slots adyacentes (conectables)
	// si no hay nada, queda como NULL 
	//
	// los slots adyacentes tienen que setearse durante o despues de la creacion 

	//Constructor sin carta alojada
	public Slot(){this.forma = " ";}
	public Slot(Slot ar,Slot ab,Slot iz,Slot de){
		this.forma = " ";
		this.arriba = ar;
		this.abajo = ab;
		this.izquierda = iz;
		this.derecha = de;
	}
	//Constructor con carta alojada 
	public Slot(Carta_Camino carta){
		this.forma = carta.getForma();
		this.carta_alojada = carta;
	}
	public Slot(Carta_Camino carta,Slot ar,Slot ab,Slot iz,Slot de){
		this.forma = carta.getForma();
		this.carta_alojada = carta;
		this.arriba = ar;
		this.abajo = ab;
		this.izquierda = iz;
		this.derecha = de;
	}
	//Seters para slots adyacentes
	public void Slot_Set_Arriba(Slot slot){this.arriba = slot;}
	public void Slot_Set_Abajo(Slot slot){this.abajo = slot;}
	public void Slot_Set_Izquierda(Slot slot){this.izquierda = slot;}
	public void Slot_Set_Derecha(Slot slot){this.derecha = slot;}
	public void SlotSetForma(String formaNueva){this.forma = formaNueva;}
	

	//Geters
	
	public Carta_Camino getCartaAlojadaEnSlot(){
		// Si no hay carta alojada, devuelve NULL
		return this.carta_alojada;
	}
	public boolean taOcupao(){
		boolean estado =false;
		if(this.carta_alojada != null){
			estado = true;
		}
		return estado;
	}
	public boolean slotConectadoALaMeta(){
		return estoyConectadoConLaMeta;
	}

	//FuncionesSeters

	public boolean alojarCarta(Carta cartaRecibida){
		//verificar si es una instancia de carta camino
		//
		//verificar si es una 
		//carta camino 
		//      Entrada
		//		Meta (Carbon u oro)
		//		Derrumbe
		if(cartaRecibida instanceof CartaSimple){
			CartaSimple cartita = (CartaSimple) cartaRecibida;
			if(cartita.esDerrumbe()){
			this.eliminarCartaAlojada();
			return true;
			}
		}

		if(cartaRecibida instanceof Carta_Camino){
			//System.out.println("es de instancia camino");
		}else{
			return false;
		}
		Carta_Camino carta = ((Carta_Camino) cartaRecibida);
		//verificamos los conectores de la carta que se quiere alojar y de ahi los slots 
		//si se puede unir con alguno, entonces la carta se añade
		boolean conexion = false;
		if(carta.getArriba() && 
				this.arriba != null && 
				this.arriba.getCartaAlojadaEnSlot() != null && 
				this.arriba.getCartaAlojadaEnSlot().esUnCallejon() == false && 
				this.arriba.getCartaAlojadaEnSlot().getAbajo()){
			conexion = true;
			if(carta.esUnCallejon() == false){
			checkConexionMeta(this.arriba);
			}
		}
		if(carta.getAbajo() && 
				this.abajo != null && 
				this.abajo.getCartaAlojadaEnSlot() != null && 
				this.abajo.getCartaAlojadaEnSlot().esUnCallejon() == false && 
				this.abajo.getCartaAlojadaEnSlot().getArriba()){
			conexion = true;
			if(carta.esUnCallejon() == false){
			checkConexionMeta(this.abajo);
			}
		}
		if(carta.getIzquierda() && 
				this.izquierda != null && 
				this.izquierda.getCartaAlojadaEnSlot()  != null && 
				this.izquierda.getCartaAlojadaEnSlot().esUnCallejon() == false && 
				this.izquierda.getCartaAlojadaEnSlot().getDerecha()){
			conexion = true;
			if(carta.esUnCallejon() == false){
			checkConexionMeta(this.izquierda);
			}
		}
		if(carta.getDerecha() && 
				this.derecha != null && 
				this.derecha.getCartaAlojadaEnSlot() != null && 
				this.derecha.getCartaAlojadaEnSlot().esUnCallejon() == false && 
				this.derecha.getCartaAlojadaEnSlot().getIzquierda()){
			conexion = true;
			System.out.println("Slot test");
			if(carta.esUnCallejon() == false){
				System.out.println("Slot test2");
				checkConexionMeta(this.derecha);
			}
		}
		System.out.println("Slot test6");
		//almacena la carta 
		if(conexion){
			this.carta_alojada = carta;
			this.forma = carta.getForma();
		}
		return conexion;
	}

	private void checkConexionMeta(Slot slot){

		if(slot.getCartaAlojadaEnSlot().esMeta() ){
				System.out.println("Slot test3");
			//Carta oro tiene 2 posibles resultados, ORO y CARBON 
			if( slot.getCartaAlojadaEnSlot().getOro() ){
				estoyConectadoConLaMeta = true;
				//Cambio la forma del slot 
				//(osea descubro la carta)
				System.out.println("Slot test4");
				slot.getCartaAlojadaEnSlot().setForma("O");
				System.out.println("Slot test5");

			}else{
				//si es un carbon, se descubre 
				slot.getCartaAlojadaEnSlot().setForma("C");
				//slot.SlotSetForma("C");
			}
		}
	}

}
