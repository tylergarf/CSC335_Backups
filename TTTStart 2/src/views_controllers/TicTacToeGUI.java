package views_controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private OurObserver buttonView;
    //private OurObserver drawingView;

    private MenuItem newGame;
    private MenuItem randAI;
    private MenuItem intermediateAI;
    private MenuItem textAreaViewOption;
    private MenuItem buttonViewOption;
    private MenuItem drawingViewOption;

    private BorderPane window;
    public static final int width = 254;
    public static final int height = 360;

    public void start(Stage stage) {
    	stage.setTitle("Tic Tac Toe");
        window = new BorderPane();
        Scene scene = new Scene(window, width, height);
        initializeGameForTheFirstTime();
        initializeMenu(stage);

        textAreaView = new TextAreaView(theGame);
        buttonView = new ButtonView(theGame);
        // drawingView = new DrawingView(theGame); // Commented out to remove the initialization
        theGame.addObserver(textAreaView);
        theGame.addObserver(buttonView);
        // theGame.addObserver(drawingView); // Removed from being added as an observer
        setViewTo(textAreaView);

        stage.setScene(scene);
        stage.show();
    }

    public void initializeGameForTheFirstTime() {
        theGame = new TicTacToeGame();
        theGame.setComputerPlayerStrategy(new RandomAI());
    }

    private void initializeMenu(Stage stage) {
        MenuBar menuBar = new MenuBar();
        Menu optionsMenu = new Menu("Options");

        newGame = new MenuItem("New Game");
        randAI = new MenuItem("Random AI");
        intermediateAI = new MenuItem("Intermediate AI");
        textAreaViewOption = new MenuItem("TextArea");
        buttonViewOption = new MenuItem("Button");
        drawingViewOption = new MenuItem("Drawing View");

        newGame.setOnAction(new StartNewGame());
        randAI.setOnAction(new ChangeComputerAIRand());
        intermediateAI.setOnAction(new ChangeComputerAIGood());
        textAreaViewOption.setOnAction(new ChangeViewTextArea());
        buttonViewOption.setOnAction(new ChangeViewButtons());
      //  drawingViewOption = new MenuItem("Drawing View");

        
        
        optionsMenu.getItems().addAll(newGame, randAI, intermediateAI, textAreaViewOption, buttonViewOption, drawingViewOption);
        menuBar.getMenus().add(optionsMenu);
        window.setTop(menuBar);
    }

    private void setViewTo(OurObserver newView) {
    	
        currentView = newView;
        window.setCenter((Node) currentView);
    }

    private class StartNewGame implements javafx.event.EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            theGame.startNewGame();
            theGame.notifyObservers(theGame);
        }
    }

    private class ChangeComputerAIRand implements javafx.event.EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            RandomAI myRand = new RandomAI();
            theGame.setComputerPlayerStrategy(myRand);
            theGame.notifyObservers(theGame);
        }
    }

    private class ChangeComputerAIGood implements javafx.event.EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            IntermediateAI goodAI = new IntermediateAI();
            theGame.setComputerPlayerStrategy(goodAI);
            theGame.notifyObservers(theGame);
        }
    }

    private class ChangeViewTextArea implements javafx.event.EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            setViewTo(textAreaView);
        }
    }

    private class ChangeViewButtons implements javafx.event.EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            setViewTo(buttonView);
        }
    }

    /*
    private class ChangeViewDrawing implements javafx.event.EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            setViewTo(drawingView);
        }
    }
    */
}