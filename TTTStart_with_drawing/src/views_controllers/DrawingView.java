package views_controllers;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.OurObserver;
import model.TicTacToeGame;

public class DrawingView extends BorderPane implements OurObserver {
    private Canvas canvas = new Canvas(800, 600);
    private BorderPane window = new BorderPane();
    private TicTacToeGame theGame;

    public DrawingView(TicTacToeGame theGame) {
        this.theGame = theGame;
        setCenter(window);
        window.setCenter(canvas);
        gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(5);
        registerHandlers(window, canvas);
        gc.strokeLine(25, 10, 25, 280);
        gc.strokeLine(25, 10, 230, 10);
        gc.strokeLine(230, 10, 230, 280);
        gc.strokeLine(230, 280, 25, 280);
        //gc.setStroke(Color.BLUE);
    }

    @Override
    public void update(Object theObserved) {
        // Handle updates from the observed object (the game)
    	gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.strokeLine(25, 10, 25, 280);
        gc.strokeLine(25, 10, 230, 10);
        gc.strokeLine(230, 10, 230, 280);
        gc.strokeLine(230, 280, 25, 280);
        
    }

    private GraphicsContext gc;
    private boolean drawing = false;
    private double oldX, oldY;

    private void registerHandlers(BorderPane window, Canvas canvas) {
       

        window.setOnMousePressed(event -> {
            drawing = !drawing;
            oldX = event.getSceneX();
            oldY = event.getSceneY();
            if (theGame != null) {
                theGame.notifyObservers(theGame);
            }
        });

        window.setOnMouseMoved(event -> {
            if (drawing) {
                double currentX = event.getSceneX();
                double currentY = event.getSceneY();
                gc.strokeLine(oldX, oldY, currentX, currentY);
                oldX = currentX;
                oldY = currentY;
            }
        });
    }
}