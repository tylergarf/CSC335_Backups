//Author: Tyler Garfield

package model;

import java.util.Scanner;

public class TicTacToeConsoleGame {
	public static void main(String[] args) {
		TicTacToeGame myGame = new TicTacToeGame();
		myGame.startNewGame();
		IntermediateAI myHarderAI = new IntermediateAI();
		myGame.setComputerPlayerStrategy(myHarderAI);
		Scanner playerInput = new Scanner(System.in);
		boolean gameOver = false;
		System.out.print("Enter row and column:");
		System.out.print("");
		while (!gameOver) {

			boolean nextValidMove = false;
			int colInt = 0;
			int rowInt = 0;

			while (!nextValidMove) {
				String row = playerInput.next();
				String col = playerInput.next();
				// String[] rowCol = humanMoveCur.split(" ");
				try {
					rowInt = Integer.parseInt(row);
					colInt = Integer.parseInt(col);

					boolean validCorrdinates = ((rowInt >= 0) && (rowInt < 3)) && ((colInt >= 0) && (colInt < 3));

					if (validCorrdinates == false) {
						System.out.println("Invalid coordinates");
						System.out.print("\nEnter row and column:");
					} else {
						boolean moveRowCol = myGame.available(rowInt, colInt);

						if (moveRowCol) {
							nextValidMove = true;
						} else {
							System.out.println("Square taken, try again.");
							System.out.print("\nEnter row and column:");
						}
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid coordinates");
					System.out.print("\nEnter row and column:");
				}

			}
			myGame.humanMove(rowInt, colInt, false);
			System.out.println("");
			System.out.println(myGame.toString());
			System.out.println("");
			System.out.print("Enter row and column:");

			if (myGame.stillRunning() == false) {
				gameOver = true;
			} else {
				gameOver = false;
			}
		}
		System.out.println("");
		System.out.println(myGame.toString());
		System.out.println("");
		if (myGame.didWin('X')) {
			System.out.println("X wins!");
		} else if (myGame.didWin('O')) {
			System.out.println("O wins!");
		} else if (myGame.tied()) {
			System.out.println("Sorry nobody won");
		}
		playerInput.close();
	}

}
