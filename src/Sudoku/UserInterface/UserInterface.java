package Sudoku.UserInterface;

import Sudoku.problemdomain.SudokuGame;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class UserInterface implements IUserInterface.View,
        EventHandler<KeyEvent> {

    @Override
    public void setListner(IUserInterface.EventListner eventListner) {

    }

    @Override
    public void updatSquare(int x, int y, int input) {

    }

    @Override
    public void updateBoard(SudokuGame game) {

    }

    @Override
    public void showDialog(String message) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void handle(KeyEvent keyEvent) {

    }
}
