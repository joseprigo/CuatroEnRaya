
package model;


public class Jugador {

    private String nombreJugador;
    private String espaciosJugados;

    public Jugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
        this.espaciosJugados = "";
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setEspacioJugado(String espacio) {
        espaciosJugados += espacio;
    }

    public String getEspacioJugados() {
        return espaciosJugados;
    }
}
