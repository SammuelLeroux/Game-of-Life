package com.gameoflife;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(final Stage primaryStage) throws Exception {
        // set title for the stage
        primaryStage.setTitle("Game of Life");

        // creation de l'input
        TextField input = new TextField();
        input.setPromptText("Entrez une dimension");

        Label label = new Label("");

        // Création du bouton pour valider l'entrée
        Button button = new Button("Valider");

        // au clic
        button.setOnAction(e -> {
            String dimension = input.getText();
            if (!dimension.isEmpty()) {
                label.setText("Dimension entrée : " + dimension);
                GameOfLife game = new GameOfLife(dimension);
                try {
                    game.start(primaryStage);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } else {
                label.setText("Veuillez entrer une valeur !");
            }
        });

        // Disposition des élméents dans un VBox
        VBox layout = new VBox(10, input, button, label);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Création de la scene et affichage
        Scene scene = new Scene(layout, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
