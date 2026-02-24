package saboteadores.modelo.mazo;

import java.util.ArrayList;
import java.util.Collections;

public class MazoPepitas {
	private ArrayList<Integer> pepitas;
	private ArrayList<Integer> pepitasDeUnaRonda;
	public MazoPepitas(int cantidadDeJugadores){
		//se crean las pepitas
		pepitas = new ArrayList<Integer>();
		pepitasDeUnaRonda = new ArrayList<Integer>();
		this.generarPepitas();
		this.generarPepitasRonda(cantidadDeJugadores);
	}
	private void generarPepitas(){
		for(int i = 0; i<4;i++){ this.pepitas.add(3); }
		for(int i = 0; i<8;i++){ this.pepitas.add(2); }
		for(int i = 0; i<16;i++){ this.pepitas.add(1); }
		Collections.shuffle(pepitas);
	}
	private void generarPepitasRonda(int cantidadDeJugadores){
		for(int i=0;i<cantidadDeJugadores;i++){
			this.pepitasDeUnaRonda.add(this.getPepita());
		}
	}
	public int getMaxPepita(){
		//almaceno el maximo 
		Integer maximo = Collections.max(pepitasDeUnaRonda);
		//remuevo el maximo de las pepitasDeUnaRonda
		pepitasDeUnaRonda.remove(maximo);
		return maximo;
	}
	public int getSobras(){
		Integer sobra = this.pepitasDeUnaRonda.getFirst();
		this.pepitasDeUnaRonda.remove(sobra);
		return sobra;
	}
	private int getPepita(){
		Integer pepa = this.pepitas.getFirst();
		this.pepitas.remove(pepa);
		return pepa;
	}
}
