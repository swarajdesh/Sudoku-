package Sudoku.UserInterface;

import Sudoku.problemdomain.SudokuGame;

public interface IUserInterface {

    interface EventListner {
        void onSudokuInput(int x,int y,int input);
        void onDialogClick();
    }

    interface View {
        void setListner(IUserInterface.EventListner eventListner);
        void updatSquare(int x, int y, int input);
        void updateBoard(SudokuGame game);
        void showDialog(String message);
        void showError(String message);
    }
}
