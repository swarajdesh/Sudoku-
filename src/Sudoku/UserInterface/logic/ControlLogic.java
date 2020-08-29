package Sudoku.UserInterface.logic;

import Sudoku.UserInterface.IUserInterface;
import Sudoku.constants.GameState;
import Sudoku.constants.Messages;
import Sudoku.problemdomain.Storage;
import Sudoku.problemdomain.SudokuGame;

import java.io.IOException;

public class ControlLogic implements IUserInterface.EventListner {

    private Storage storage;

    private IUserInterface.View view;

    public ControlLogic(Storage storage, IUserInterface.View view) {
        this.storage = storage;
        this.view = view;
    }

    @Override
    public void onSudokuInput(int x, int y, int input) {
        try{
            SudokuGame gameData = storage.getGameData();
            int[][] newGridState = gameData.getGridState();
            newGridState[x][y] = input;

         gameData = new SudokuGame(
                 GameLogic.checkForCompletion(newGridState),
                 newGridState
         );

         storage.updateGameData(gameData);

         view.updatSquare(x,y,input);

         if (gameData.getGameState() == GameState.COMPLETE){
             view.showDialog(Messages.GAME_COMPLETE);
         }
        } catch (IOException e) {
            e.printStackTrace();
            view.showError(Messages.ERROR);
        }
    }

    @Override
    public void onDialogClick() {
        try{
            storage.updateGameData(
                    GameLogic.getNewGame()
            );
            view.updateBoard(storage.getGameData());
        } catch (IOException e) {
            e.printStackTrace();
            view.showError(Messages.ERROR);
        }

    }
}
