package saboteadores.observer;

public interface Observable {
	void agregarObservador(Observador observador);
	void quitarObservador(Observador observador);
	void notificarObservadores();
}
