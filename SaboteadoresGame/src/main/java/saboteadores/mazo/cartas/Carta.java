package saboteadores.mazo.cartas;
import saboteadores.enums.CartaTipo;
public class Carta {
	private CartaTipo tipo;
	public Carta(CartaTipo tipo){
		this.tipo = tipo;
	}
	public CartaTipo getTipo(){
		return this.tipo;
	}
}
