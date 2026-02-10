package saboteadores.mazo.cartas;
public class CartaSimple extends CartaJugableEnTablero {
	private boolean derrumbe;
	private boolean vista;

	//constructor para las 2 cartas mas simples
	public CartaSimple(boolean vista){
		if(vista){
			this.vista = true;
			this.derrumbe = false;
		}else{
			this.vista= false;
			this.derrumbe = true;
		}
	}

	public boolean esVista(){
		return this.vista;
	}
	public boolean esDerrumbe(){
		return this.derrumbe;
	}
}
