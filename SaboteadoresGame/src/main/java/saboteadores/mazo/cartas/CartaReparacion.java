package saboteadores.mazo.cartas;
import saboteadores.enums.CartaAccionTipo;
public class CartaReparacion extends CartaJugableEnJugadores{
	private CartaAccionTipo reparacion1;
	private CartaAccionTipo reparacion2;
	public CartaReparacion(CartaAccionTipo re1, CartaAccionTipo re2){
		this.reparacion1 = re1;
		this.reparacion2 = re2;
	}

	public CartaAccionTipo getReparacion1(){
		return this.reparacion1;
	}
	public CartaAccionTipo getReparacion2(){
		return this.reparacion2;
	}
}
