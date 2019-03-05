/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import controller.Juego;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

import model.Jugador;


public class FXMLviewController implements Initializable {

    private Juego juego;
    static Jugador jugador1;
    static Jugador jugador2;

    @FXML
    private AnchorPane ventana;

    /**
     * Identifica si un Pane se encuentra vacio y pone la marca "X" o "O"
     * 
     * @param e
     */
    @FXML
    protected void ponerMarca(MouseEvent e) {
        Pane pane = (Pane) e.getSource();
        boolean vacio;
        String espacioJugado;
        String espacioOriginal = pane.getId();
        
        espacioOriginal = espacioOriginal.substring(4);
        espacioJugado = juego.gravedad(espacioOriginal);
        if(!espacioJugado.equals("XY")){
            
            // Si se ha detectado que la ficha ha 'caido' a otra casilla de vuelve a instanciar la casilla
            if (!espacioJugado.equals(espacioOriginal)){
                pane = (Pane) ventana.lookup("#pane" + espacioJugado);
            }
            
            vacio = pane.getChildren().isEmpty();
            String marca = juego.setMarca(juego.getNoJugador(), vacio, espacioJugado);

            if (marca.equals("X") || marca.equals("O")) {
                Text textX = new Text(marca);
                setEstilosText(textX, pane);
                pane.getChildren().add(textX);
            }
            muestraAlertaEstado(juego.estadoJuego());
        }
        
    }

    /**
     * Muestra un Alert en caso de que exista un ganador o un empate entre los
     * jugadores
     *
     * @param estado
     */
    public void muestraAlertaEstado(String estado) {
        if (!estado.equals("Ninguno")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Felicidades !!!");
            alert.setContentText(estado);
            alert.showAndWait();
            ventana.setDisable(true);
            nuevoJuego();
        }
    }

    /**
     * Pregunta si se desea continuar jugando abriendo una nueva, o cerrando la
     * actaual.
     */
    public void nuevoJuego() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quieres volver a jugar ?");
        alert.setContentText("Jugar de nuevo");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {

            Stage ventana_actual = (Stage) ventana.getScene().getWindow();
            ventana_actual.close();

            try {
                Stage ventana_nueva = new Stage();
                Parent parent = FXMLLoader.load(getClass().getResource("/view/FXMLview.fxml"));
                Scene scene = new Scene(parent);
                ventana_nueva.setScene(scene);
                ventana_nueva.show();
            } catch (IOException ex) {

            }
        } else {
            Stage ventana_actual = (Stage) ventana.getScene().getWindow();
            ventana_actual.close();
        }
    }

    /**
     * Otorga propiedades de estilo al texto que se pondra en el pane
     *
     * @param textX
     * @param pane
     */
    private void setEstilosText(Text textX, Pane pane) {
        textX.setFont(Font.font("System", 96));
        textX.setFill(Paint.valueOf("#bdf271"));
        textX.setLayoutY(pane.getHeight() / 2 + 30);
        textX.setLayoutX(pane.getWidth() / 2 - 30);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String nombre_jugador_1 = JOptionPane.showInputDialog(null, "Nombre jugador 1 (O): ");
        String nombre_jugador_2 = JOptionPane.showInputDialog(null, "Nombre jugador 2 (X): ");
        juego = new Juego();
        jugador1 = new Jugador(nombre_jugador_1);
        jugador2 = new Jugador(nombre_jugador_2);
    }
}
