package saboteadores.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Clase utilizada para persistir el historico
 * de los jugadores.
 */

public class Top implements Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<JugadorHistorico> top; 
	public Top(){
		this.top = new ArrayList<>();
	}
	/**
	 * Agrega puntos a un jugador dentro de este historico.
	 */
	public void agregarPuntosHistorico(String name, int cantidad){
		boolean presente = false;
		for(JugadorHistorico j : this.top){
			if(j.getNombre().equals(name)){
				j.sumarPuntos(cantidad);
				presente = true;
			}
		}
		if(presente == false){
			JugadorHistorico nuevo = new JugadorHistorico();
			nuevo.setNombre(name);
			nuevo.sumarPuntos(cantidad);
			this.top.add(nuevo);
		}
		//ordenarlos cada vez que se agregan
		top.sort(Comparator.comparingInt(JugadorHistorico::getPuntos).reversed());
	}
	/** Devuelve una lista con los jugadores y sus puntos.
	 */
	public ArrayList<JugadorHistorico> getHistorico(){
		return this.top;
	}
}
