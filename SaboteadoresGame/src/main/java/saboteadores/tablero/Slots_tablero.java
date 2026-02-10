package saboteadores.tablero;

import saboteadores.enums.CartaTipo;
import saboteadores.mazo.cartas.Carta_Camino;
import saboteadores.mazo.cartas.Carta_Oro;
import saboteadores.mazo.Mazo_oros;
import saboteadores.tablero.Slot;

import java.util.ArrayList;
public class Slots_tablero { 
	private ArrayList<Slot> slots; // en cada slot, hay una carta,
	private ArrayList<Carta_Camino> mazo_oro; // en cada slot, hay una carta,

	public Slots_tablero(){
		this.mazo_oro = new Mazo_oros().get_mazo_oros();
		this.crear_slots();
		this.Enlazar_Slots();
	}
	//Primero se crean los slots y despues se enlazan unos con otros
	


	private void crear_slots(){
		slots = new ArrayList<Slot>();
		//Primera Fila
		// ARRIBA ABAJO IZQUIERDA DERECHA
		for(int i = 0; i < 8; i++ ){
			// Ultimo slot 
			slots.add( new Slot());
		}
		slots.add( new Slot(mazo_oro.get(0)));
		//Segunda Fila 
		for(int i = 0; i < 9; i++ ){
			slots.add( new Slot());
		}
		//Tercera Fila 
		//Entrada
		slots.add( new Slot(
					new Carta_Camino(false, true, true, true, true, "#")));
		for(int i = 0; i < 7; i++ ){
			slots.add( new Slot());
		}
		slots.add( new Slot(mazo_oro.get(1)));

		//Cuarta Fila 
		for(int i = 0; i < 9; i++ ){
			slots.add( new Slot());
		}
		//Quinta Fila 
		for(int i = 0; i < 8; i++ ){
			slots.add( new Slot());
		}
		slots.add( new Slot(mazo_oro.get(2)));
	}
	private void Enlazar_Slots(){
		int posicion = 0;
		//Primera Fila
		this.getSlot(posicion).Slot_Set_Derecha(this.getSlot(posicion+1));
		this.getSlot(posicion).Slot_Set_Abajo(this.getSlot(posicion+10));
		posicion++;

		for(int i = 0; i < 7; i++){
			this.getSlot(posicion).Slot_Set_Izquierda(
					this.getSlot(posicion-1)
					);
			this.getSlot(posicion).Slot_Set_Derecha(
					this.getSlot(posicion+1)
					);
			this.getSlot(posicion).Slot_Set_Abajo(
					this.getSlot(posicion+10)
					);
			posicion++;
		}
		//la ultima es del oro 
		this.getSlot(posicion).Slot_Set_Abajo(this.getSlot(posicion+10));
		this.getSlot(posicion).Slot_Set_Izquierda(this.getSlot(posicion-1));
		posicion++;

		//Segunda Fila (3 filas intermedias)
		for(int intermedia = 0; intermedia < 3; intermedia++){
			for(int i = 0; i < 8; i++){
				this.getSlot(posicion).Slot_Set_Arriba(
						this.getSlot(posicion-10)
						);
				this.getSlot(posicion).Slot_Set_Abajo(
						this.getSlot(posicion+10)
						);
				this.getSlot(posicion).Slot_Set_Izquierda(
						this.getSlot(posicion-1)
						);
				this.getSlot(posicion).Slot_Set_Derecha(
						this.getSlot(posicion+1)
						);
				posicion++;
			}
			this.getSlot(posicion).Slot_Set_Arriba(this.getSlot(posicion-10));
			this.getSlot(posicion).Slot_Set_Abajo(this.getSlot(posicion+10));
			this.getSlot(posicion).Slot_Set_Izquierda(this.getSlot(posicion-1));
			posicion++;
		}
		//Quinta Fila 
		this.getSlot(posicion).Slot_Set_Arriba(this.getSlot(posicion-10));
		this.getSlot(posicion).Slot_Set_Derecha(this.getSlot(posicion+1));
		posicion++;
		for(int i = 0; i < 7; i++){
			this.getSlot(posicion).Slot_Set_Izquierda(
					this.getSlot(posicion-1)
					);
			this.getSlot(posicion).Slot_Set_Derecha(
					this.getSlot(posicion+1)
					);
			this.getSlot(posicion).Slot_Set_Arriba(
					this.getSlot(posicion-10)
					);
			posicion++;
		}
		//la ultima es del oro 
		this.getSlot(posicion).Slot_Set_Arriba(this.getSlot(posicion-10));
		this.getSlot(posicion).Slot_Set_Izquierda(this.getSlot(posicion-1));
		posicion++;

	}
	public Slot getSlot(int pos){
		return this.slots.get(pos);
	}
}
