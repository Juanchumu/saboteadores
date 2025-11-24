package saboteadores;
import java.util.ArrayList;
import saboteadores.mazo.cartas.Carta;
import saboteadores.enums.Rol;

public class Jugador {
	private String nombre;
	private ArrayList<Carta> mano;
	private Rol rol;
	public Jugador(String n, ArrayList<Carta> m, Rol r){
		this.nombre = n;
		this.mano = m; 
		this.rol = r ;
	}
	public ArrayList<Carta> getManoJugador(){
		return this.mano;
	}
}
