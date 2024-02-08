package views_controllers;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.IntermediateAI;
import model.OurObserver;
import model.TicTacToeGame;

public class ButtonView extends BorderPane implements OurObserver {

	// private TicTacToeGame theGame;
	private Button[][] buttons = new Button[3][3];
	private EventHandler<ActionEvent> handleButtonClicked = new ButtonGotClicked();
	private GridPane gameBoard = new GridPane();
	Font fontOne =  Font.font("Mono Space",FontWeight.BOLD,32);
	Font fontTwo =  Font.font("Mono Space",FontWeight.BOLD,20);



	private TicTacToeGame theGame;
	private Label gameProgress;
	

	public ButtonView(TicTacToeGame theGame) {
		this.theGame = theGame;
		//theGame.notifyObservers(theGame);
		gameBoard.setPadding(new Insets(2));
	
		
		gameProgress = new Label();
		gameProgress.setText("   Game  is  in  progress  !!");
		gameProgress.setFont(fontTwo);
		gameProgress.setTextFill(Color.CADETBLUE);
		
		gameBoard.add(gameProgress,0, 4, 4,4);
		theGame.setComputerPlayerStrategy(new IntermediateAI());
		initializeButtonView();
		
	}

	private void initializeButtonView() {
		
		
		

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Button button = new Button("_");
				button.setFont(fontOne);
				button.setOnAction(handleButtonClicked);
				buttons[i][j] = button;
				gameBoard.add(button, j, i);
				
				
				button.setMinSize(75.2,75.2); 
				button.setMaxSize(75.2,75.2); 
			}
		}
		
		theGame.notifyObservers(theGame);
		
		gameBoard.setHgap(12);
		gameBoard.setVgap(13);

		
		
		this.setCenter(gameBoard);
	}

	@Override
	public void update(Object theObserved) {
		   TicTacToeGame observedGame = (TicTacToeGame) theObserved;
		    char[][] board = observedGame.getTicTacToeBoard();

		    gameBoard.getChildren().clear();

		    for (int i = 0; i < 3; i++) {
		        for (int j = 0; j < 3; j++) {
		            Button button = new Button();
		            button.setOnAction(handleButtonClicked);
		            buttons[i][j] = button;
		            button.setText(Character.toString(board[i][j]));
		            button.setFont(fontOne);
		            gameBoard.add(button, j, i);
		            button.setMinSize(75.2, 75.2);
		            button.setMaxSize(75.2, 75.2);
		        }
		    }

		    // Set the game progress label text based on the game state
		    if (theGame.stillRunning()) {
		        gameProgress.setText("Game is in progress");
		    } else if (theGame.didWin('X')) {
		        gameProgress.setText("X Won !!!!");
		    } else if (theGame.didWin('O')) {
		        gameProgress.setText("O won !!");
		    } else if (theGame.tied()) {
		        gameProgress.setText("You tied");
		    }

		    // Set font and text color
		    gameProgress.setFont(fontTwo);
		    gameProgress.setTextFill(Color.CADETBLUE);

		    // Add the game progress label to the grid pane
		    gameBoard.add(gameProgress, 0, 4, 4, 4);
		
		
		
	}
	
	

	private class ButtonGotClicked implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent arg0) {
			Button buttonClicked = (Button) arg0.getSource();
			

			for (int row = 0; row < 3; row++) {
				for (int col = 0; col < 3; col++) {
					if (buttons[row][col] == buttonClicked) {
						if(theGame.stillRunning()) {
							theGame.humanMove(row, col, false);
						}
						else {
							theGame.notifyObservers(theGame);
						}
					}
				}
			}

			theGame.notifyObservers(theGame);
		}
	}
}
