package saboteadores.modelo.mazo.cartas;
import saboteadores.modelo.enums.CartaAccionTipo;


import java.io.Serializable;

public class CartaSabotaje extends CartaJugableEnJugadores implements Serializable {
	private static final long serialVersionUID = 1L;
	private CartaAccionTipo sabotaje;
	public CartaSabotaje(CartaAccionTipo sabotaje){
		this.sabotaje = sabotaje;
	}
	public CartaAccionTipo getAccion(){
		return this.sabotaje;
	}
}
