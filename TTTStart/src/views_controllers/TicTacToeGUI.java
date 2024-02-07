
package views_controllers;

/**
 * Play TicTacToe the computer that can have different AIs to beat you. 
 * Select the Options menus to begin a new game, switch strategies for 
 * the computer player (BOT or AI), and to switch between the two views.
 * 
 * This class represents an event-driven program with a graphical user 
 * interface as a controller between the view and the model. It has 
 * event handlers to mediate between the view and the model.
 * 
 * This controller employs the Observer design pattern that updates two 
 * views every time the state of the Tic Tac Toe game changes:
 * 
 *  1) whenever you make a move by clicking a button or an area of either view
 *  2) whenever the computer AI makes a move
 *  3) whenever there is a win or a tie
 *    
 * You can also select two different strategies to play against from the menus
 * 
 * @author Rick Mercer and Tyler Garfield
 */
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.IntermediateAI;
import model.OurObserver;
import model.RandomAI;
import model.TicTacToeGame;

public class TicTacToeGUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private TicTacToeGame theGame;

	private OurObserver currentView;
	private OurObserver textAreaView;
	// TBA:
	private OurObserver buttonView;
	private OurObserver drawingView;

	private MenuItem newGame = new MenuItem("new game");
	private MenuItem randAI = new MenuItem();
	private MenuItem intermediateAI = new MenuItem();

	private Menu viewOptions = new Menu("options");

	private BorderPane window;
	public static final int width = 254;
	public static final int height = 360;

	public void start(Stage stage) {
		stage.setTitle("Tic Tac Toe");
		window = new BorderPane();
		Scene scene = new Scene(window, width, height);
		initializeGameForTheFirstTime();

		// TBA: Set up the views in Sprint 2
		// buttonView = new ButtonView(theGame);
		// drawingView = new DrawingView(theGame);
		// theGame.addObserver(buttonView);
		// theGame.addObserver(drawingView);
		textAreaView = new TextAreaView(theGame);
		theGame.addObserver(textAreaView);
		setViewTo(textAreaView);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Set the game to the default of an empty board and the random AI.
	 */
	public void initializeGameForTheFirstTime() {
		theGame = new TicTacToeGame();
		// This event driven program will always have
		// a computer player who takes the second turn
		theGame.setComputerPlayerStrategy(new RandomAI());
	}

	private void setViewTo(OurObserver newView) {
		MenuBar optionsMenu = new MenuBar();
		window.setCenter(null);
		window.setTop(optionsMenu);
		currentView = newView;
		window.setCenter((Node) currentView);

		EventHandler<ActionEvent> changeToRandom = new changeComputerAIRand();
		randAI.setOnAction(changeToRandom);
		randAI.setText("random ai");

		EventHandler<ActionEvent> changeToBetterAI = new changeComputerAIGood();
		intermediateAI.setOnAction(changeToBetterAI);
		intermediateAI.setText("intermediate ai");

		Menu AIChoices = new Menu("cahnge AI");

		EventHandler<ActionEvent> StartNewGame = new StartNewGame();
		newGame.setOnAction(StartNewGame);

		viewOptions.getItems().add(AIChoices);

		viewOptions.getItems().add(newGame);
		AIChoices.getItems().add(intermediateAI);
		AIChoices.getItems().add(randAI);

		newGame.setText("new game");

		optionsMenu.setAccessibleText("options");
		optionsMenu.getMenus().add(viewOptions);
	}

	public class changeComputerAIRand implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			RandomAI myRand = new RandomAI();
			theGame.setComputerPlayerStrategy(myRand);
			theGame.notifyObservers(theGame);
		}
	}

	public class changeComputerAIGood implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			IntermediateAI goodAI = new IntermediateAI();
			theGame.setComputerPlayerStrategy(goodAI);
			theGame.notifyObservers(theGame);

		}
	}

	public class StartNewGame implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			theGame.startNewGame();
			theGame.notifyObservers(theGame);
		}

	}

}