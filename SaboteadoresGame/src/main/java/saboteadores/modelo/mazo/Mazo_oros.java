package saboteadores.modelo.mazo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import saboteadores.modelo.mazo.cartas.Carta_Camino;

//import sab.Carta_Oro;

public class Mazo_oros implements Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<Carta_Camino> mazo_oro;
	public Mazo_oros(){
		this.oros();
	}
	private void oros(){
		this.mazo_oro = new ArrayList<Carta_Camino>(); 
		this.mazo_oro.add(new Carta_Camino(true,false));
		this.mazo_oro.add(new Carta_Camino(true,false));
		this.mazo_oro.add(new Carta_Camino(true,true));
		Collections.shuffle(this.mazo_oro);
	}
	public ArrayList<Carta_Camino> get_mazo_oros(){
		return this.mazo_oro;
	}
}
