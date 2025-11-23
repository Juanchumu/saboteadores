package sab;
import java.util.ArrayList;

public class Jugador {
	private String nombre;
	private ArrayList<Carta> mano;
	private Rol rol;
	public Jugador(String n, ArrayList<Carta> m, Rol r){
		this.nombre = n;
		this.mano = m; 
		this.rol = r ;
	}
}
