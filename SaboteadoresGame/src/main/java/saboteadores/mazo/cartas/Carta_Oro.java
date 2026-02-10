package saboteadores.mazo.cartas;
//import sab.Posiciones;

//import saboteadores.enums.CartaTipo;
//import saboteadores.mazo.cartas.CartaFijaEnTablero;

public class Carta_Oro extends CartaFijaEnTablero {
	private boolean oro;
	private String forma;
	public Carta_Oro(
			//CartaTipo tipo, //callejon sin salida o camino 
			boolean ar, 
			boolean ab,
			boolean iz,
			boolean de, String forma, boolean oro){
		//super(tipo, ar, ab, iz, de, "X");
		this.oro = oro;
		this.forma = forma;
	}
	public boolean getOro(){
		return this.oro;
	}
	public String getForma(){
		return this.forma;
	}
}
