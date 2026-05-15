# saboteadores
juego de java


### Uso:

pararse en el directorio /SaboteadoresGame/

#### Levantar el Servidor: 
mvn exec:java -Dexec.mainClass=saboteadores.servidor.ServidorLocal

#### Levantar un cliente de vista grafica:
mvn exec:java -Dexec.mainClass=saboteadores.cliente.AppCliente

#### Levantar un cliente de vista grafica consola:
mvn exec:java -Dexec.mainClass=saboteadores.cliente.consola.AppCliente

### Notas: 

* los jugadores tienen que tener distintos nombres
(en caso contrario, se tomara el control de un jugador que esta jugando).
* Las incorporaciones en el juego unicamente tienen que ser al inicio de la partida, ya que es el unico momento en donde el modelo espera nuevos jugadores.

* El top solo se puede desde el lobby de espera.
