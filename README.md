# 🎮 Saboteadores

Juego multijugador hecho en Java.

✅ Probado en **Lubuntu 24.04 LTS**

---

## 🚀 Uso

Ubicarse dentro del directorio del proyecto:

```bash
cd SaboteadoresGame/
```

---

## 🖥️ Levantar el servidor

```bash
mvn exec:java -Dexec.mainClass=saboteadores.servidor.ServidorLocal
```

---

## 👤 Cliente con interfaz gráfica

```bash
mvn exec:java -Dexec.mainClass=saboteadores.cliente.AppCliente
```

---

## ⌨️ Cliente por consola

```bash
mvn exec:java -Dexec.mainClass=saboteadores.cliente.consola.AppClienteConsola
```

---

## 📌 Notas importantes

- Cada jugador debe ingresar con un **nombre distinto**.
  - Si un nombre ya está siendo utilizado, se tomará el control de ese jugador existente.

- Los jugadores solamente pueden unirse:
  - **antes de que comience la partida**.
  - Durante el lobby de espera.

- La función de **Top** solo puede utilizarse desde el lobby.

- La serialización del juego se guarda en:
  - `tablero.dat`
  - `top.dat`

  Ambos archivos pueden borrarse sin problemas.

- Cuando una partida finaliza:
  - todos los jugadores reciben la notificación,
  - y se informa quiénes fueron los ganadores.

---

## 🛠️ Tecnologías utilizadas

- Java
- Maven
- Arquitectura Cliente/Servidor 
- Serialización Java
- JavaFX

---
