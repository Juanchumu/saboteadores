package saboteadores.modelo.mazo;

import java.util.ArrayList;
import saboteadores.modelo.enums.CartaAccionTipo;
//import saboteadores.enums.CartaTipo;
import saboteadores.modelo.mazo.cartas.Carta;
//import saboteadores.mazo.cartas.Carta_Accion;
import saboteadores.modelo.mazo.cartas.Carta_Camino;
import saboteadores.modelo.mazo.cartas.CartaReparacion;
import saboteadores.modelo.mazo.cartas.CartaSabotaje;
import saboteadores.modelo.mazo.cartas.CartaSimple;

import java.util.Collections;

public class Mazo {
	private ArrayList<Carta>  mazo;

	public Mazo(){
		//se crea el mazo
		mazo = new ArrayList<Carta>();
		this.addCallejonesYCaminos();
		this.addAcciones();
		Collections.shuffle(mazo);
	}
	private void addCallejonesYCaminos(){
		//arriba abajo , izquierda derecha
		mazo.add(new Carta_Camino(true,false,true,false,false,"^"));
		mazo.add(new Carta_Camino(true,false,false,true,false, ">"));

		mazo.add(new Carta_Camino(false,true,true,false,false,"║"));
		mazo.add(new Carta_Camino(false,true,true,false,false,"║"));
		mazo.add(new Carta_Camino(false,true,true,false,false,"║"));
		mazo.add(new Carta_Camino(false,true,true,false,false,"║"));

		mazo.add(new Carta_Camino(false,false,false,true,true,"═"));
		mazo.add(new Carta_Camino(false,false,false,true,true,"═"));
		mazo.add(new Carta_Camino(false,false,false,true,true,"═"));


		mazo.add(new Carta_Camino(false,false,true,false,true,"╔"));
		mazo.add(new Carta_Camino(false,false,true,false,true,"╔"));
		mazo.add(new Carta_Camino(false,false,true,false,true,"╔"));
		mazo.add(new Carta_Camino(false,false,true,false,true,"╔"));

		mazo.add(new Carta_Camino(false,false,true,true,false,"╗"));
		mazo.add(new Carta_Camino(false,false,true,true,false,"╗"));
		mazo.add(new Carta_Camino(false,false,true,true,false,"╗"));
		mazo.add(new Carta_Camino(false,false,true,true,false,"╗"));
		mazo.add(new Carta_Camino(false,false,true,true,false,"╗"));


		//Arriba izquierda abajo x5
		mazo.add(new Carta_Camino(false,true,true,false,true,"╠"));
		mazo.add(new Carta_Camino(false,true,true,false,true,"╠"));
		mazo.add(new Carta_Camino(false,true,true,false,true,"╠"));
		mazo.add(new Carta_Camino(false,true,true,false,true,"╠"));
		mazo.add(new Carta_Camino(false,true,true,false,true,"╠"));

		for(int i = 0; i < 5; i++){
			mazo.add(new Carta_Camino(false,true,false,true,true,"╩"));
		}
		for(int i = 0; i < 5; i++){
			mazo.add(new Carta_Camino(false,true,true,true,true,"╬"));
		}

		//segundo nivel 
		mazo.add(new Carta_Camino(true ,true,true,false,false,"│"));		
		mazo.add(new Carta_Camino(true,false,false,true,true,"─"));		
		mazo.add(new Carta_Camino(true,false,true,false,true,"└"));		
		mazo.add(new Carta_Camino(true,false,true,true,false,"┘"));		

		//tercer nivel 
		mazo.add(new Carta_Camino(true,true,true,false,true,"├"));
		mazo.add(new Carta_Camino(true,true,false,true,true,"┴"));
		
		mazo.add(new Carta_Camino(true,true,true,true,true,"┼"));
	}

	private void addAcciones(){
		for(int i = 0; i<3; i++){
		mazo.add(new CartaSabotaje(CartaAccionTipo.MINECART));
		}

		for(int i = 0; i<3; i++){
		mazo.add(new CartaSabotaje(CartaAccionTipo.ANTORCHA));
		}		
		for(int i = 0; i<3; i++){
		mazo.add(new CartaSabotaje(CartaAccionTipo.PICO));
		}

		mazo.add(new CartaReparacion(CartaAccionTipo.PICO, CartaAccionTipo.VACIO));
		mazo.add(new CartaReparacion(CartaAccionTipo.PICO, CartaAccionTipo.VACIO));
		mazo.add(new CartaReparacion(CartaAccionTipo.ANTORCHA, CartaAccionTipo.VACIO));
		mazo.add(new CartaReparacion(CartaAccionTipo.ANTORCHA, CartaAccionTipo.VACIO));
		mazo.add(new CartaReparacion(CartaAccionTipo.MINECART, CartaAccionTipo.VACIO));
		mazo.add(new CartaReparacion(CartaAccionTipo.MINECART, CartaAccionTipo.VACIO));

		mazo.add(new CartaReparacion(CartaAccionTipo.PICO,CartaAccionTipo.MINECART));
		mazo.add(new CartaReparacion(CartaAccionTipo.PICO, CartaAccionTipo.ANTORCHA));
		mazo.add(new CartaReparacion(CartaAccionTipo.ANTORCHA, CartaAccionTipo.MINECART));


		//Cartas Vista
		for(int i = 0; i < 6; i++){
			mazo.add( new CartaSimple(true));
		}
		//Cartas Derrumbe
		for(int i = 0; i < 4; i++){
			mazo.add( new CartaSimple(false));
		}

	}

	public ArrayList<Carta> getMazo(){
		return this.mazo;
	}
	public boolean hayCartasEnElMazo(){
		boolean estado = true;
		if(this.mazo.size() == 0){
			estado = false;
		}
		return estado;
	}
	public Carta getCarta(){
		Carta devolver = this.mazo.get(this.mazo.size() -1 );
		this.mazo.remove(this.mazo.size() - 1);  // elimina el último 
		return devolver;
	}
	public int getCantidadRestanteMazo(){
		return this.mazo.size();
	}
	public void eliminarCarta(){
		this.mazo.remove(this.mazo.size() - 1);  // elimina el último 
	}
}
