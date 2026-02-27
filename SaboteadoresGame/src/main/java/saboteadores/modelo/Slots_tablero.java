package saboteadores.modelo;

import saboteadores.modelo.enums.CartaTipo;
import saboteadores.modelo.mazo.cartas.Carta_Camino;
import saboteadores.modelo.mazo.Mazo_oros;
import saboteadores.modelo.Slot;



import java.io.Serializable;

import java.util.ArrayList;
public class Slots_tablero implements Serializable {
	private static final long serialVersionUID = 1L;

	private ArrayList<Slot> slots; // en cada slot, hay una carta,
	private ArrayList<Carta_Camino> mazo_oro; // en cada slot, hay una carta,
	private int largoTablero = 7; //MAX 7 MIN 1

	public Slots_tablero(int largo){
		this.largoTablero = largo;
		this.mazo_oro = new Mazo_oros().get_mazo_oros();
		this.crear_slots();
		this.Enlazar_Slots();
	}
	//Primero se crean los slots y despues se enlazan unos con otros
	


	private void crear_slots(){
		slots = new ArrayList<Slot>();
		//Primera Fila
		// ARRIBA ABAJO IZQUIERDA DERECHA
		for(int i = 0; i < largoTablero + 1 ; i++ ){
			// Ultimo slot 
			slots.add( new Slot());
		}
		slots.add( new Slot(mazo_oro.get(0)));
		//Segunda Fila 
		for(int i = 0; i < largoTablero + 2; i++ ){
			slots.add( new Slot());
		}
		//Tercera Fila 
		//Entrada
		slots.add( new Slot(
					new Carta_Camino(false, true, true, true, true, "#")));
		for(int i = 0; i < largoTablero; i++ ){
			slots.add( new Slot());
		}
		slots.add( new Slot(mazo_oro.get(1)));

		//Cuarta Fila 
		for(int i = 0; i < largoTablero + 2; i++ ){
			slots.add( new Slot());
		}
		//Quinta Fila 
		for(int i = 0; i < largoTablero + 1; i++ ){
			slots.add( new Slot());
		}
		slots.add( new Slot(mazo_oro.get(2)));
	}
	private void Enlazar_Slots(){
		int posicion = 0;
		//Primera Fila
		this.getSlot(posicion).Slot_Set_Derecha(this.getSlot(posicion+1));
		this.getSlot(posicion).Slot_Set_Abajo(this.getSlot(posicion+ (largoTablero+2)  ));
		posicion++;

		for(int i = 0; i < largoTablero; i++){
			this.getSlot(posicion).Slot_Set_Izquierda(
					this.getSlot(posicion-1)
					);
			this.getSlot(posicion).Slot_Set_Derecha(
					this.getSlot(posicion+1)
					);
			this.getSlot(posicion).Slot_Set_Abajo(
					this.getSlot(posicion+ (largoTablero+2)  )
					);
			posicion++;
		}
		//la ultima es del oro 
		this.getSlot(posicion).Slot_Set_Abajo(this.getSlot(posicion+ (largoTablero+2)  ));
		this.getSlot(posicion).Slot_Set_Izquierda(this.getSlot(posicion-1));
		posicion++;

		//Segunda Fila (3 filas intermedias)
		for(int intermedia = 0; intermedia < 3; intermedia++){
			this.getSlot(posicion).Slot_Set_Arriba(this.getSlot(posicion- (largoTablero+2)  ));
			this.getSlot(posicion).Slot_Set_Abajo(this.getSlot(posicion+ (largoTablero+2)  ));
			this.getSlot(posicion).Slot_Set_Derecha(this.getSlot(posicion+1));
			posicion++;
			for(int i = 0; i < largoTablero; i++){
				this.getSlot(posicion).Slot_Set_Arriba(
						this.getSlot(posicion- (largoTablero+2)  )
						);
				this.getSlot(posicion).Slot_Set_Abajo(
						this.getSlot(posicion+ (largoTablero+2)  )
						);
				this.getSlot(posicion).Slot_Set_Izquierda(
						this.getSlot(posicion-1)
						);
				this.getSlot(posicion).Slot_Set_Derecha(
						this.getSlot(posicion+1)
						);
				posicion++;
			}
			this.getSlot(posicion).Slot_Set_Arriba(this.getSlot(posicion- (largoTablero+2)   ));
			this.getSlot(posicion).Slot_Set_Abajo(this.getSlot(posicion+ (largoTablero+2)  ));
			this.getSlot(posicion).Slot_Set_Izquierda(this.getSlot(posicion-1));
			posicion++;
		}
		//Quinta Fila 
		this.getSlot(posicion).Slot_Set_Arriba(this.getSlot(posicion- (largoTablero+2)  ));
		this.getSlot(posicion).Slot_Set_Derecha(this.getSlot(posicion+1));
		posicion++;
		for(int i = 0; i < largoTablero; i++){
			this.getSlot(posicion).Slot_Set_Izquierda(
					this.getSlot(posicion-1)
					);
			this.getSlot(posicion).Slot_Set_Derecha(
					this.getSlot(posicion+1)
					);
			this.getSlot(posicion).Slot_Set_Arriba(
					this.getSlot(posicion-9)
					);
			posicion++;
		}
		//la ultima es del oro 
		this.getSlot(posicion).Slot_Set_Arriba(this.getSlot(posicion-(largoTablero +2)));
		this.getSlot(posicion).Slot_Set_Izquierda(this.getSlot(posicion-1));
		posicion++;

	}
	public Slot getSlot(int pos){
		return this.slots.get(pos);
	}
}
