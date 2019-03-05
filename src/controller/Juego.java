package controller;

import static controller.FXMLviewController.jugador1;
import static controller.FXMLviewController.jugador2;
import java.util.Arrays;


public class Juego {

    private int nojugador;
    private int[][] opcionesganar;
    private int[] casillasDisponibles;
    private int totalClick;

    public Juego() {
        opcionesganar = new int[][]{
            {0, 1, 2},
            {10, 11, 12},
            {20, 21, 22},
            {0, 10, 20},
            {1, 11, 21},
            {2, 12, 22},
            {0, 11, 22},
            {20, 11, 2}};
        totalClick = 0;
        casillasDisponibles = new int [] {50, 51, 52, 53, 54, 55, 56};
    }

    /**
     * Verifica los aciertos que ha tenido el jugador
     *
     * @param espacios_jugados
     * @return boolean
     */
    public boolean compruebaAciertos(int[] espacios_jugados) {

        int filas = opcionesganar.length;
        int columnas = opcionesganar[0].length;
        int longitud = espacios_jugados.length;
        int aciertos = 0;
        boolean es_ganador = false;

        comprobar:
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                for (int k = 0; k < longitud; k++) {
                    if (espacios_jugados[k] == opcionesganar[i][j]) {
                        aciertos++;
                        System.out.println("Aciertos : " + aciertos);
                        if (aciertos == 3) {
                            es_ganador = true;
                            break comprobar;
                        }
                    }
                }
            }
            aciertos = 0;
        }

        return es_ganador;
    }

    /**
     * Identifica la marca que se pondra en el tablero
     * El jugador1 es el numberjugador = 0
     * El jugador2 es el numberjugador = 1
     * Antes en el condicional, si numberjudador era 0 se le asignaba al 2ndo y 
     * viceversa.
     * @param numberjugador
     * @param vacio
     * @param espaciojugado
     * @return String
     */
    public String setMarca(int numberjugador, boolean vacio, String espaciojugado) {
        if (vacio) {
            if (numberjugador == 1) {
                nojugador = 0;
                totalClick++;
                jugador2.setEspacioJugado(espaciojugado + ",");
                return "X";
            } else {
                nojugador = 1;
                totalClick++;
                jugador1.setEspacioJugado(espaciojugado + ",");
                return "O";
            }
        } else {
            nojugador = numberjugador;
            return "";
        }
    }

    /**
     * Obtiene el numero del jugador que ha dado click
     *
     * @return int
     */
    public int getNoJugador() {
        return nojugador;
    }

    /**
     * Comprueba si existe ganador,empate, o ninguno de los anteriores
     *
     * @return String
     */
    public String estadoJuego() {
        if (totalClick >= 7) {
            if (compruebaAciertos(parsearEspacios(jugador1.getEspacioJugados()))) {
                return "Ganador " + jugador1.getNombreJugador();
            }
            if (compruebaAciertos(parsearEspacios(jugador2.getEspacioJugados()))) {
                return "Ganador " + jugador2.getNombreJugador();
            }
        }
        if (totalClick == 42) {
            return "Empate";
        }
        return "Ninguno";
    }

    /**
     * Pasa la cadena que contiene los espacios que tiene el jugador a un
     * arreglo de enteros para poder compararlos con la matriz de posibilidades
     *
     * @param espacios
     * @return int[]
     */
    public int[] parsearEspacios(String espacios) {
        String[] espacios_array = espacios.split(",");
        int length = espacios_array.length;
        int[] espacios_int = new int[length];
        for (int i = 0; i < length; i++) {
            espacios_int[i] = Integer.parseInt(espacios_array[i]);
        }
        return espacios_int;
    }
    /**
     * Simula la gravedad: Cuando el usuario clica en cualquier columna situa
     * la ficha en la posición mas baja. Si en una columna no quedan casillas
     * devuelve XY 
     * @param requestCasilla casilla en la que se solicita poner una ficha
     * @return 
     */
    public String gravedad (String requestCasilla){
        Integer intCasilla = Integer.parseInt(requestCasilla);
        // encontrar columna
        int decenas = intCasilla / 10;
        int columna = Math.abs(decenas * 10 - intCasilla);
        // hay posiciones disponibles en esta columna?
        int ultimaPosicion = casillasDisponibles[columna];
        if(ultimaPosicion >= 0){
            // si hay devulve la posicion que toca
            intCasilla = ultimaPosicion;
            requestCasilla = String.format("%02d", intCasilla);
            casillasDisponibles[columna] -= 10;
            return requestCasilla;
        }
        
        // si no devuelve XY
        return "XY";
    }

}
