package saboteadores.modelo;

import java.io.Serializable;
import java.util.List;

public class EstadoSala 
	implements Serializable {
	private static final long serialVersionUID = 1L;
    public int totalJugadores;
    public int listos;
    public List<String> faltan;

    public EstadoSala(int total, int listos, List<String> faltan) {
        this.totalJugadores = total;
        this.listos = listos;
        this.faltan = faltan;
    }
}
