package com.gameoflife;

import javafx.util.Duration;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameOfLife extends Application {
    public static Grid grid;

    public GameOfLife(String gridDimension) {
        GameOfLife.grid = new Grid(gridDimension);
    }

    private void changeState() {
        int dimension = GameOfLife.grid.getDimension();
        Grid newGrid = new Grid(dimension);

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                newGrid.setCell(i, j, getChangeState(GameOfLife.grid.getCell(i, j), i, j));
            }
        }
        GameOfLife.grid.grid = newGrid.grid;
    }

    @Override
    public void start(final Stage stage) throws Exception {
        // set title for the stage
        stage.setTitle("Game of Life");

        GridPane gridPane = createGridPane();
        // Set up the timeline to update the grid every 10 seconds
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(5), event -> {
                // Change the grid state
                changeState();

                // Update the grid pane on the JavaFX application thread
                Platform.runLater(() -> {
                    // Recreate the grid with updated states
                    GridPane newGridPane = createGridPane();
                    VBox layout = new VBox(10, newGridPane); // Re-create layout with new grid
                    Scene scene = new Scene(layout, 500, 500);
                    stage.setScene(scene); // Set new scene with updated grid
                    stage.show();
                });
            })
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Keep running indefinitely
        timeline.play();
        
        // Création de la scene et affichage
        VBox layout = new VBox(10, gridPane);
        Scene scene = new Scene(layout, 500, 500);
        stage.setScene(scene);
        stage.show();
    }

    public GridPane createGridPane() {
        GridPane gridPane = new GridPane();

        int cellSize = 20; // Taille des cellules en pixels

        for (int i = 0; i < GameOfLife.grid.gridDimension; i++) {
            for (int j = 0; j < GameOfLife.grid.gridDimension; j++) {
                Rectangle cell = new Rectangle(cellSize, cellSize);
                cell.setFill(GameOfLife.grid.getCell(i, j) == 1 ? Color.BLACK : Color.WHITE);
                cell.setStroke(Color.GRAY); // Pour voir les bordures des cellules

                gridPane.add(cell, j, i); // (colonne, ligne)
            }
        }

        return gridPane;
    }

    private boolean isAlive(int cell) {
        if (cell == 1) return true;
        else return false;
    }

    private int getChangeState(int cell, int i, int j) {
        int left = GameOfLife.grid.getCell(i, j-1);
        int right = GameOfLife.grid.getCell(i, j+1);
        int up = GameOfLife.grid.getCell(i-1, j);
        int down = GameOfLife.grid.getCell(i+1, j);
        int upLeft = GameOfLife.grid.getCell(i-1, j-1);
        int upRight = GameOfLife.grid.getCell(i-1, j+1);
        int downLeft = GameOfLife.grid.getCell(i+1, j-1);
        int downRigth = GameOfLife.grid.getCell(i+1, j+1);

        int score = (upLeft + up + upRight) + (left + right) + (downLeft + down + downRigth);
        
        if (score == 3) return 1;
        else if (score == 2) return cell;
        else if (score < 2 || score > 3) return 0;

        return cell;  // Si aucune condition n'est remplie (ce qui ne devrait pas arriver)
    }
}
