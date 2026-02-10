package saboteadores.mazo;
import java.util.ArrayList;
import saboteadores.mazo.cartas.Carta_Camino;

//import sab.Carta_Oro;

public class Mazo_oros {
	private ArrayList<Carta_Camino> mazo_oro;
	public Mazo_oros(){
		this.oros();
	}
	private void oros(){
		this.mazo_oro = new ArrayList<Carta_Camino>(); 
		this.mazo_oro.add(new Carta_Camino(true,false));
		this.mazo_oro.add(new Carta_Camino(true,false));
		this.mazo_oro.add(new Carta_Camino(true,true));
		//mezclarthis.mazo_oro
	}
	public ArrayList<Carta_Camino> get_mazo_oros(){
		return this.mazo_oro;
	}
}
