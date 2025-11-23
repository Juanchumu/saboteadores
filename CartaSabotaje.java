package sab;

public class CartaSabotaje extends Carta{
	private CartaAccionTipo sabotaje;
	public CartaSabotaje(CartaTipo tipo, CartaAccionTipo sabotaje){
		super(tipo);
		this.sabotaje = sabotaje;
	}
	public CartaAccionTipo getAccion(){
		return this.sabotaje;
	}
}
