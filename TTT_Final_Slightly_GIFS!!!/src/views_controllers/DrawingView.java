package views_controllers;

///import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.OurObserver;
import model.TicTacToeGame;

public class DrawingView extends BorderPane implements OurObserver {
	private Canvas canvas = new Canvas(800, 600);
	private BorderPane window = new BorderPane();
	private TicTacToeGame theGame;

	private Image bigSquareX = new Image(getClass().getResourceAsStream("big_square.png"), 55, 72, false, false);
	private Image bigDash = new Image(getClass().getResourceAsStream("dash_og.png"), 55, 72, false, false);
	private Image goodO = new Image(getClass().getResourceAsStream("O.png"), 55, 72, false, false);

	private Label gameState = new Label();
	private int xChoice, yChoice = 0;

	public DrawingView(TicTacToeGame theGame) {
		Font fontOne = Font.font("Mono Space", FontWeight.BOLD, 22);

		this.theGame = theGame;
		setCenter(window);
		window.setCenter(canvas);
		gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.CORAL);
		gc.setLineWidth(5);
		registerHandlers(window, canvas);
		gc.strokeLine(25, 10, 25, 280); // vert line left
		gc.setFont(fontOne);

		gc.strokeLine(230, 10, 230, 280); // vert line right
		gc.strokeLine(230, 280, 25, 280); // line horz bottom
		gc.strokeLine(25, 100, 230, 100); // line horz 2
		gc.strokeLine(25, 200, 230, 200); // line horz 3
		// gc.strokeLine(25, 10, 230, 10);
		gc.strokeLine(25, 10, 230, 10); // horz line top

		gc.strokeLine(95, 10, 95, 280); // vert line int 1
		gc.strokeLine(160, 10, 160, 280); // vert line int 2

		// row col

		/*
		 * gc.drawImage(bigSquareX, 31, 22); // square number 0,0
		 * gc.drawImage(bigSquareX, 99, 22); // square number 0,1
		 * gc.drawImage(bigSquareX, 167, 22); // square number 0,3
		 * gc.drawImage(bigSquareX, 167, 110); // square number 1,0
		 * gc.drawImage(bigDash, 31, 110); // square number 1,1 gc.drawImage(goodO, 99,
		 * 110); // square number 1,2 gc.drawImage(bigSquareX, 167, 205); // square
		 * number 2,0 gc.drawImage(bigSquareX, 31, 205); // square number 2,1
		 * gc.drawImage(goodO, 99, 205); // square number 2,2
		 */
		
		
		refreshSquares();
		

	}

	@Override
	public void update(Object theObserved) {
		// Handle updates from the observed object (the game)
		gc = canvas.getGraphicsContext2D();
		refreshSquares();

	}

	public void refreshSquares() {
		gc = canvas.getGraphicsContext2D();
	    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	    
	    gc.strokeLine(25, 10, 25, 280); // vert line left
		

		gc.strokeLine(230, 10, 230, 280); // vert line right
		gc.strokeLine(230, 280, 25, 280); // line horz bottom
		gc.strokeLine(25, 100, 230, 100); // line horz 2
		gc.strokeLine(25, 200, 230, 200); // line horz 3
		// gc.strokeLine(25, 10, 230, 10);
		gc.strokeLine(25, 10, 230, 10); // horz line top

		gc.strokeLine(95, 10, 95, 280); // vert line int 1
		gc.strokeLine(160, 10, 160, 280); // vert line int 2
		char[][] boardCur = theGame.getTicTacToeBoard();
		

		double[] squareCoordinatesX = {32, 100, 167};
	    double[] squareCoordinatesY = {15, 110, 203};
	    
	    for (int i = 0; i < 3; i++) {
	        for (int j = 0; j < 3; j++) {
	            // Calculate the exact coordinates for the current square
	            double x = squareCoordinatesX[j];
	            double y = squareCoordinatesY[i];

	            if (boardCur[i][j] == 'O') {
	                gc.drawImage(goodO, x, y);
	            }
	            if (boardCur[i][j] == '_') {
	                gc.drawImage(bigDash, x, y);
	            }
	            if (boardCur[i][j] == 'X') {
	                gc.drawImage(bigSquareX, x, y);
	            }
	            
	            
	            
	            
	        }
	    }
	    
	    
	    if (theGame.didWin('X')) {
			gc.fillText("X wins", 20, 310);
			
			
		} else if (theGame.didWin('O')) {
			gc.fillText("O wins", 20, 310);
			
			
		} else if (theGame.tied()) {
			gc.fillText("You tied", 20, 310);
			
			
		}
		else {
			gc.fillText("Game in progress", 20, 310);
		}
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
				if (theGame.stillRunning()) {

					if (oldX >= 25 && oldX <= (87) && oldY <= 107 && oldY >= (35)) { // square 0,0
						// theGame.humanMove(0, 0, false);

						xChoice = 0;
						yChoice = 0;
						System.out.println("You are in square 0,0");
					} else if (oldX >= 25 && oldX <= (87) && oldY <= 210 && oldY >= (120)) { // square 1,0
						// theGame.humanMove(1, 0, false);
						xChoice = 0;
						yChoice = 1;
						System.out.println("You are in square 1,0");
					} else if (oldX >= 94 && oldX <= (149) && oldY <= 107 && oldY >= (35)) { // square 0,1
						// theGame.humanMove(0, 1, false);
						xChoice = 1;
						yChoice = 0;
						System.out.println("You are in square 0,1");
					} else if (oldX >= 163 && oldX <= (216) && oldY <= 107 && oldY >= (35)) { // square 0,1
						// theGame.humanMove(0, 2, false);
						xChoice = 2;
						yChoice = 0;
						System.out.println("You are in square 0,2");
					} else if (oldX >= 25 & oldX <= (87) && oldY <= 280 && oldY >= (228)) { // square 0,1\
						// theGame.humanMove(2, 0, false);
						xChoice = 0;
						yChoice = 2;
						System.out.println("You are in square 2,0");
					} else if (oldX >= 94 & oldX <= (149) && oldY <= 210 && oldY >= (120)) { // square 0,1
						// theGame.humanMove(1, 1, false);
						xChoice = 1;
						yChoice = 1;
						System.out.println("You are in square 1,1");
					} else if (oldX >= 163 & oldX <= (216) && oldY <= 210 && oldY >= (120)) { // square 0,1
						// theGame.humanMove(1, 2, false);
						xChoice = 2;
						yChoice = 1;
						System.out.println("You are in square 1,2");
					} else if (oldX >= 94 & oldX <= (149) && oldY <= 280 && oldY >= (228)) { // square 0,1
						// theGame.humanMove(2, 1, false);
						xChoice = 1;
						yChoice = 2;
						System.out.println("You are in square 2,1");
					} else if (oldX >= 163 & oldX <= (216) && oldY <= 280 && oldY >= (228)) { // square 0,1
						// theGame.humanMove(2, 2, false);
						xChoice = 2;
						yChoice = 2;
						System.out.println("You are in square 2,2");
					}

					// Reset chosen to false at the end of the event handler
					

				}
				
				
				theGame.humanMove(yChoice, xChoice, false);
				
				theGame.notifyObservers(theGame);

			}
		});

		window.setOnMouseMoved(event -> {
			if (drawing) {
				double currentX = event.getSceneX();
				double currentY = event.getSceneY();
				System.out.println("x: " + currentX + " / y: " + currentY);

				boolean chosen = false;
				drawing = true;

				// 99 147

				// gc.strokeLine(oldX, oldY, currentX, currentY);

				chosen = false;
				oldX = currentX;
				oldY = currentY;
			}
		});
	}
}