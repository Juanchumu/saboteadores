package sab;

import java.util.ArrayList;

public class Mazo {
	private ArrayList<Carta>  mazo;

	public Mazo(){
		//se crea el mazo
		this.addCallejonesYCaminos();
		this.addAcciones();
	}
	public void addCallejonesYCaminos(){
		//arriba abajo , izquierda derecha
		mazo.add(new Carta_Camino(CartaTipo.CALLEJON,false,true,false,false));
		mazo.add(new Carta_Camino(CartaTipo.CALLEJON,false,false,true,false));

		mazo.add(new Carta_Camino(CartaTipo.CAMINO,true,true,false,false));
		mazo.add(new Carta_Camino(CartaTipo.CAMINO,true,true,false,false));
		mazo.add(new Carta_Camino(CartaTipo.CAMINO,true,true,false,false));
		mazo.add(new Carta_Camino(CartaTipo.CAMINO,true,true,false,false));

		mazo.add(new Carta_Camino(CartaTipo.CAMINO,false,false,true,true));
		mazo.add(new Carta_Camino(CartaTipo.CAMINO,false,false,true,true));
		mazo.add(new Carta_Camino(CartaTipo.CAMINO,false,false,true,true));


		mazo.add(new Carta_Camino(CartaTipo.CAMINO,false,true,false,true));
		mazo.add(new Carta_Camino(CartaTipo.CAMINO,false,true,false,true));
		mazo.add(new Carta_Camino(CartaTipo.CAMINO,false,true,false,true));
		mazo.add(new Carta_Camino(CartaTipo.CAMINO,false,true,false,true));

		mazo.add(new Carta_Camino(CartaTipo.CAMINO,false,true,true,false));
		mazo.add(new Carta_Camino(CartaTipo.CAMINO,false,true,true,false));
		mazo.add(new Carta_Camino(CartaTipo.CAMINO,false,true,true,false));
		mazo.add(new Carta_Camino(CartaTipo.CAMINO,false,true,true,false));
		mazo.add(new Carta_Camino(CartaTipo.CAMINO,false,true,true,false));


		//Arriba izquierda abajo x5
		mazo.add(new Carta_Camino(CartaTipo.CAMINO,true,true,false,true));
		mazo.add(new Carta_Camino(CartaTipo.CAMINO,true,true,false,true));
		mazo.add(new Carta_Camino(CartaTipo.CAMINO,true,true,false,true));
		mazo.add(new Carta_Camino(CartaTipo.CAMINO,true,true,false,true));
		mazo.add(new Carta_Camino(CartaTipo.CAMINO,true,true,false,true));

		for(int i = 0; i < 5; i++){
			mazo.add(new Carta_Camino(CartaTipo.CAMINO,true,false,true,true));
		}
		for(int i = 0; i < 5; i++){
			mazo.add(new Carta_Camino(CartaTipo.CAMINO,true,true,true,true));
		}

		//segundo nivel 
		mazo.add(new Carta_Camino(CartaTipo.CALLEJON,true,true,false,false));		
		mazo.add(new Carta_Camino(CartaTipo.CALLEJON,false,false,true,true));		
		mazo.add(new Carta_Camino(CartaTipo.CALLEJON,false,true,false,true));		
		mazo.add(new Carta_Camino(CartaTipo.CALLEJON,false,true,true,false));		

		//tercer nivel 
		mazo.add(new Carta_Camino(CartaTipo.CALLEJON,true,true,false,true));
		mazo.add(new Carta_Camino(CartaTipo.CALLEJON,true,false,true,true));
		
		mazo.add(new Carta_Camino(CartaTipo.CALLEJON,true,true,true,true));
	}

	public void addAcciones(){

		mazo.add(new CartaSabotaje(CartaTipo.SABOTAJE, CartaAccionTipo.MINECART));
		mazo.add(new CartaSabotaje(CartaTipo.SABOTAJE, CartaAccionTipo.MINECART));
		mazo.add(new CartaSabotaje(CartaTipo.SABOTAJE, CartaAccionTipo.MINECART));


		mazo.add(new CartaSabotaje(CartaTipo.SABOTAJE, CartaAccionTipo.ANTORCHA));
		mazo.add(new CartaSabotaje(CartaTipo.SABOTAJE, CartaAccionTipo.ANTORCHA));
		mazo.add(new CartaSabotaje(CartaTipo.SABOTAJE, CartaAccionTipo.ANTORCHA));
		
		mazo.add(new CartaSabotaje(CartaTipo.SABOTAJE, CartaAccionTipo.PICO));
		mazo.add(new CartaSabotaje(CartaTipo.SABOTAJE, CartaAccionTipo.PICO));
		mazo.add(new CartaSabotaje(CartaTipo.SABOTAJE, CartaAccionTipo.PICO));


		mazo.add(new CartaReparacion(CartaTipo.REPARACION, CartaAccionTipo.PICO, CartaAccionTipo.VACIO));
		mazo.add(new CartaReparacion(CartaTipo.REPARACION, CartaAccionTipo.PICO, CartaAccionTipo.VACIO));
		mazo.add(new CartaReparacion(CartaTipo.REPARACION, CartaAccionTipo.ANTORCHA, CartaAccionTipo.VACIO));
		mazo.add(new CartaReparacion(CartaTipo.REPARACION, CartaAccionTipo.ANTORCHA, CartaAccionTipo.VACIO));
		mazo.add(new CartaReparacion(CartaTipo.REPARACION, CartaAccionTipo.MINECART, CartaAccionTipo.VACIO));
		mazo.add(new CartaReparacion(CartaTipo.REPARACION, CartaAccionTipo.MINECART, CartaAccionTipo.VACIO));


		mazo.add(new CartaReparacion(
					CartaTipo.REPARACION,
					CartaAccionTipo.PICO,
					CartaAccionTipo.MINECART
					));
		mazo.add(new CartaReparacion(CartaTipo.REPARACION, CartaAccionTipo.PICO, CartaAccionTipo.ANTORCHA));
		mazo.add(new CartaReparacion(CartaTipo.REPARACION, CartaAccionTipo.ANTORCHA, CartaAccionTipo.MINECART));

		
		for(int i = 0; i < 6; i++){
			mazo.add( new Carta(CartaTipo.VISTA));
		}
		for(int i = 0; i < 4; i++){
			mazo.add( new Carta(CartaTipo.DERRUMBE));
		}

	}

	public ArrayList<Carta> getMazo(){
		return this.mazo;
	}
}
