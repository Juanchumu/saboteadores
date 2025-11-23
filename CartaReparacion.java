package sab;

public class CartaReparacion extends Carta{
	private CartaAccionTipo reparacion1;
	private CartaAccionTipo reparacion2;
	public CartaReparacion(CartaTipo tipo, CartaAccionTipo re1, CartaAccionTipo re2){
		super(tipo);
		this.reparacion1 = re1;
		this.reparacion2 = re2;
	}
}
