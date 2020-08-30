package Sudoku.buildlogic;

import Sudoku.UserInterface.IUserInterface;
import Sudoku.UserInterface.logic.ControlLogic;
import Sudoku.computationalLogic.GameLogic;
import Sudoku.persistence.LocalStorageImpl;
import Sudoku.problemdomain.Storage;
import Sudoku.problemdomain.SudokuGame;

import java.io.IOException;

public class SudokuBuildLogic {

    public static void build(IUserInterface.View userInterface) throws IOException {

        SudokuGame initialState;
        Storage storage = new LocalStorageImpl();

        try{
              initialState = storage.getGameData();
        }catch (IOException e){
            initialState = GameLogic.getNewGame();
            storage.updateGameData(initialState);
        }

        IUserInterface.EventListner uiLogic = new ControlLogic(storage , userInterface);

        userInterface.setListner(uiLogic);
        userInterface.updateBoard(initialState);
    }
}
