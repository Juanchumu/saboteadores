package saboteadores.mazo.cartas;
import saboteadores.enums.CartaAccionTipo;
public class CartaSabotaje extends CartaJugableEnJugadores{
	private CartaAccionTipo sabotaje;
	public CartaSabotaje(CartaAccionTipo sabotaje){
		this.sabotaje = sabotaje;
	}
	public CartaAccionTipo getAccion(){
		return this.sabotaje;
	}
}
