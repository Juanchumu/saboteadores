package saboteadores;
import java.util.ArrayList;
import saboteadores.mazo.cartas.Carta;
import saboteadores.mazo.cartas.CartaReparacion;
import saboteadores.mazo.cartas.CartaSabotaje;
import saboteadores.mazo.cartas.Carta_Camino;
import saboteadores.mazo.cartas.Carta_Oro;
import saboteadores.enums.CartaAccionTipo;
import saboteadores.enums.CartaTipo;
import saboteadores.enums.Rol;
import java.util.Iterator;

public class Jugador {
	private String nombre;
	private ArrayList<Carta> mano;
	private ArrayList<CartaSabotaje> restricciones;
	private ArrayList<Carta_Camino> orosVistos;
	private Rol rol;
	private int puntos;
	public Jugador(String n, ArrayList<Carta> m, Rol r){
		this.puntos = 0; //puntos iniciales en la creacion del jugador 
		this.nombre = n;
		this.mano = m; 
		this.rol = r ;
		this.restricciones = new ArrayList<CartaSabotaje>();
		this.orosVistos = new ArrayList<Carta_Camino>();
	}
	public ArrayList<Carta> getManoJugador(){
		return this.mano;
	}
	public ArrayList<Carta_Camino> getOrosJugador(){
		return this.orosVistos;
	}
	public ArrayList<CartaSabotaje> getRestriccionesJugador(){
		return this.restricciones;
	}
	public String getNombre(){
		return this.nombre;
	}
	public Rol getRol(){
		return this.rol;
	}
	public void sumarPuntosJugador(int puntos){
		this.puntos = this.puntos + puntos;
	}
	public int getPuntos(){
		return this.puntos;
	}
	public boolean agregarRestriccion(Carta carta){
		boolean estado = false;
		if(carta instanceof CartaSabotaje ){
			CartaSabotaje cartita = (CartaSabotaje) carta;
			this.restricciones.add(cartita);
			estado = true;
		}
		return estado;
	}
	public boolean quitarRestriccion(Carta carta){
		boolean estado = false;
		if(carta instanceof CartaReparacion ){
			CartaReparacion cartita = (CartaReparacion) carta;
			Iterator<CartaSabotaje> it = this.restricciones.iterator();
			while(it.hasNext()){
				CartaSabotaje c = it.next();
				if(cartita.getReparacion1() == c.getAccion()){
					it.remove();   // ← elimina de forma segura
					estado = true;
					break; // si solo querés sacar la primera coincidencia
				}
				if(cartita.getReparacion2() != CartaAccionTipo.VACIO){
					if(cartita.getReparacion2() == c.getAccion()){
						it.remove();   // ← elimina de forma segura
						estado = true;
						break; // si solo querés sacar la primera coincidencia
					}

				}
			}
		}
		return estado;
	}
	public boolean tieneRestricciones(){
		return this.restricciones.isEmpty();
	}
	public void agregarOrosVistos(Carta carta){
		if(carta instanceof Carta_Camino){
			Carta_Camino oritolindo = (Carta_Camino) carta;
			this.orosVistos.add(oritolindo);
		}
	}

}
