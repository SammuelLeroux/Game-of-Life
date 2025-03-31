package com.gameoflife;

import javafx.util.Duration;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameOfLife extends Application {
    public static Grid grid;
    private GridPane gridPane;

    public GameOfLife(String gridDimension) {
        GameOfLife.grid = new Grid(gridDimension);
    }

    private void changeState() {
        int dimension = GameOfLife.grid.getDimension();
        Grid newGrid = new Grid(dimension, false);

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int currentState = GameOfLife.grid.getCell(i, j);
                int newState = getChangeState(currentState, i, j);
                newGrid.setCell(i, j, newState);
            }
        }
        GameOfLife.grid.grid = newGrid.grid;
    }

    public boolean gameOver() {
        if (GameOfLife.grid.getNbLivingCells() == 0) {
            System.out.println("GAME OVER");
            return true;
        }

        return false;
    }

    @Override
    public void start(final Stage stage) throws Exception {
        // set title for the stage
        stage.setTitle("Game of Life");

        this.gridPane = createGridPane();
        VBox layout = new VBox(10, this.gridPane);
        Scene scene = new Scene(layout, 500, 500);
        stage.setScene(scene);
        stage.show();

        // Set up the timeline to update the grid every 10 seconds
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(2), event -> {
                if (gameOver())return;

                // Change the grid state
                changeState();

                // Update the grid pane on the JavaFX application thread
                Platform.runLater(this::updateGridPane);
            })
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Keep running indefinitely
        timeline.play();
    }

    public GridPane createGridPane() {
        int cellSize = 20; // Taille des cellules en pixels
        
        GridPane gridPane = new GridPane();
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

    private void updateGridPane() {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Rectangle) {
                Rectangle rect = (Rectangle) node;
                Integer rowObj = GridPane.getRowIndex(rect);
                Integer colObj = GridPane.getColumnIndex(rect);
    
                // Utiliser 0 si la valeur est null (JavaFX peut ne pas stocker ces indices)
                int row = (rowObj != null) ? rowObj : 0;
                int col = (colObj != null) ? colObj : 0;
    
                int state = GameOfLife.grid.getCell(row, col);
                rect.setFill(state == 1 ? Color.BLACK : Color.WHITE);
            }
        }
    }       
}
