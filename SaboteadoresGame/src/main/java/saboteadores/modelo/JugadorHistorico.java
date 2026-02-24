package saboteadores.modelo;

import java.io.Serializable;

public class JugadorHistorico implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nombre;
	private int puntos;

	public JugadorHistorico(){
		this.nombre = "";
		this.puntos = 0;
	}

	public void sumarPuntos(int cantidad){
		this.puntos = this.puntos + cantidad;
	}
	public void setNombre(String nombre){
	this.nombre = nombre;
	}
	public String getNombre(){
		return this.nombre;
	}
	public int getPuntos(){
		return this.puntos;
	}
}
