//Author: Tyler Garfield

package views_controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

/**
 * This is the beginning of one view of a Tic Tac Toe game using
 * two TextField objects and one TextArea. The other two views
 * of ButtonView and DrawingView follow the same structure as this.
 * 
 * @author Rick Mercer and Tyler Garfield
 */
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.OurObserver;
import model.TicTacToeGame;

public class TextAreaView extends BorderPane implements OurObserver {

	private TicTacToeGame theGame;
	private Menu viewOptions = new Menu("options");
	private TextField rowInput = new TextField();
	private TextField colInput = new TextField();
	private Button makeMove = new Button();
	private TextArea gameBoard = new TextArea();
	private Label somoneWon = new Label();

	Font fontOne = Font.font("Mono Space Center", FontWeight.BOLD, 32);

	public TextAreaView(TicTacToeGame theModel) {
		theGame = theModel;
		gameBoard.setFont(fontOne);
		gameBoard.setCenterShape(true);
		gameBoard.setStyle("-fx-alignment: CENTER;");

		initializePanel();
	}

	private void initializePanel() {

		GridPane inputPane = new GridPane();
		Label rowLabel = new Label();
		Label colLabel = new Label();

		MenuBar optionsMenu = new MenuBar();
		optionsMenu.setAccessibleText("options");
		optionsMenu.getMenus().add(viewOptions);

		rowLabel.setText("row");
		colLabel.setText("colum");
		// viewOptions.setTe\;
		makeMove.setText("Make Move");
		inputPane.setPrefSize(254, 200);
		gameBoard.setPrefSize(254, 160);
		somoneWon.setText("The game is in progress");
		inputPane.setHgap(10);
		inputPane.setHgap(5);
		inputPane.setPadding(new Insets(20));
		inputPane.add(colLabel, 3, 6);
		inputPane.add(rowLabel, 3, 1);
		inputPane.add(rowInput, 2, 1);
		inputPane.add(colInput, 2, 6);
		inputPane.add(makeMove, 2, 8);
		inputPane.add(somoneWon, 2, 9);

		gameBoard.setStyle("-fx-font-alignment: center;");
		Font font = new Font("Courier", 35);
		gameBoard.setFont(font);
		gameBoard.setText(theGame.toString());
		gameBoard.snapPositionX(127);
		gameBoard.snapPositionY(80);

		this.setCenter(inputPane);
		this.setBottom(gameBoard);

		EventHandler<ActionEvent> moveButtonPressed = new ActionButtonHandler();
		makeMove.setOnAction(moveButtonPressed);
	}

	// This method is called by Observable's notifyObservers()
	@Override
	public void update(Object observable) {
		System.out.println("update called from OurObservable TicTacToeGame " + theGame);

		gameBoard.setText(theGame.toString());
		rowInput.setText("");
		colInput.setText("");
		if (theGame.stillRunning()) {
			makeMove.setText("Make Move");
			somoneWon.setText("game in progress");
		}

		else {

			if (theGame.didWin('X')) {
				somoneWon.setText("X wins");
				rowInput.setText("");
				colInput.setText("");
				makeMove.setText("Start new game in options");
				gameBoard.setText(theGame.toString());
				return;
			} else if (theGame.didWin('O')) {
				somoneWon.setText("O wins");
				rowInput.setText("");
				colInput.setText("");
				makeMove.setText("Start new game in options");
				gameBoard.setText(theGame.toString());
				return;
			} else if (theGame.tied()) {
				somoneWon.setText("You tied");
				rowInput.setText("");
				colInput.setText("");
				makeMove.setText("Start new game in options");
				gameBoard.setText(theGame.toString());
				return;
			}
		}

	}

	private class ActionButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			if (theGame.stillRunning()) {
				int row = 0;
				int col = 0;
				try {
					row = Integer.parseInt(rowInput.getText());
					col = Integer.parseInt(colInput.getText());
				} catch (NumberFormatException e) {
					makeMove.setText("Invalid int given");
					rowInput.setText("");
					colInput.setText("");
					return;
				}

				if (row < 0 || row > 2 || col < 0 || col > 2) {
					makeMove.setText("point out of bounds");
					rowInput.setText("");
					colInput.setText("");
					return;
				} else if (!theGame.available(row, col)) {
					makeMove.setText("invalid choice");
					rowInput.setText("");
					colInput.setText("");
					return;
				} else {
					theGame.humanMove(row, col, false);
					if (theGame.didWin('X')) {
						somoneWon.setText("X wins");
						rowInput.setText("");
						colInput.setText("");
						makeMove.setText("Start new game in options");
						gameBoard.setText(theGame.toString());
						return;
					} else if (theGame.didWin('O')) {
						somoneWon.setText("O wins");
						rowInput.setText("");
						colInput.setText("");
						makeMove.setText("Start new game in options");
						gameBoard.setText(theGame.toString());
						return;
					} else if (theGame.tied()) {
						somoneWon.setText("You tied");
						rowInput.setText("");
						colInput.setText("");
						makeMove.setText("Start new game in options");
						gameBoard.setText(theGame.toString());
						return;
					}
					gameBoard.setText(theGame.toString());
					rowInput.setText("");
					colInput.setText("");
					makeMove.setText("Make Move");
					somoneWon.setText("game in progress");
				}
				theGame.notifyObservers(theGame);
			} else {
				makeMove.setText("Start new game in options");
				rowInput.setText("");
				colInput.setText("");
				theGame.notifyObservers(theGame);
			}

		}
	}

}