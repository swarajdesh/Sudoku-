package Sudoku.problemdomain;

import Sudoku.constants.GameState;

import java.io.Serializable;

public class SudokuGame implements Serializable {

    private final GameState gameState;
    private  final int[][] gridState;

    public static final int GRID_BOUNDARY = 9;


    public SudokuGame(GameState gameState,int[][] gridState) {
        this.gridState = gridState;
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int[][] getGridState() {
        return GameUtilities.copyToNewArray(gridState);
        //returning copy of gridState this protects sudoku game object to mess with
    }
}
