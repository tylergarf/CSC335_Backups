/**
 * Rick suggests, the IntermediateAI first check to stop a win of the opponent, 
 * then look for its own win. If neither is found, select any other open
 * spot randomly. You may use any other strategy as long as it beats RandomAI.
 * 
 * @authors Rick Mercer and Tyler Garfield
 */
package model;

public class IntermediateAI implements TicTacToeStrategy {

	@Override
	public OurPoint desiredMove(TicTacToeGame theGame) {
		char[][] boardCur = theGame.getTicTacToeBoard();

		int rowChoice = 2;
		int colChoice = 2;
		boolean selectedChoice = false;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (theGame.available(i, j)) {
					boardCur[i][j] = 'O';

					if (theGame.didWin('O')) {
						boardCur[i][j] = '_';
						rowChoice = i;
						colChoice = j;
						selectedChoice = true;
						break;
					} else {
						boardCur[i][j] = '_';
					}
				}
			}
		}

		if (!selectedChoice) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (theGame.available(i, j)) {
						boardCur[i][j] = 'X';

						if (theGame.didWin('X')) {
							boardCur[i][j] = '_';
							rowChoice = i;
							colChoice = j;
							selectedChoice = true;
							break;
						} else {
							boardCur[i][j] = '_';
						}
					}
				}
			}
		}

		if (!selectedChoice) {
			RandomAI randomChoice = new RandomAI();
			return randomChoice.desiredMove(theGame);
		}

		OurPoint retPoint = new OurPoint(rowChoice, colChoice);
		return retPoint;

	}
}
