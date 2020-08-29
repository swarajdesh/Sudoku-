package Sudoku.computationalLogic;

import Sudoku.constants.GameState;
import Sudoku.constants.Rows;
import Sudoku.problemdomain.SudokuGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static Sudoku.problemdomain.SudokuGame.GRID_BOUNDARY;

public class GameLogic {

    public static SudokuGame getNewGame(){
        return new SudokuGame(
                GameState.NEW,
                GameGenerator.getNewGameGrid()
        );
    }

    public static GameState checkForCompletion(int[][] grid){
        if (sudokuIsInvalid(grid)) return GameState.ACTIVE;
        if (tilesAreNotFilled(grid)) return GameState.ACTIVE;
        return GameState.COMPLETE;
    }

    protected static boolean sudokuIsInvalid(int[][] grid) {
        if (rowsAreInvalid(grid)) return true;
        if (columnsAreInvalid(grid)) return true;
        if (squaresAreInvalid(grid)) return true;
         else return false;
    }

    private static boolean rowsAreInvalid(int[][] grid) {
        for (int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++){
            List<Integer> row = new ArrayList<>();
            for (int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++){
                row.add(grid[xIndex][yIndex]);
            }
            if (collectionHasRepeats(row)) return true;
        }
        return false;
    }



    private static boolean columnsAreInvalid(int[][] grid) {
        for (int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++){
            List<Integer> row = new ArrayList<>();
            for (int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++){
                 row.add(grid[xIndex][yIndex]);
            }
            if (collectionHasRepeats(row)) return true;
        }
        return false;
    }

    private static boolean squaresAreInvalid(int[][] grid) {
        if (rowOfSquaresIsInvalid(Rows.TOP, grid)) return true;

        if (rowOfSquaresIsInvalid(Rows.MIDDLE, grid)) return true;

        if (rowOfSquaresIsInvalid(Rows.END, grid)) return true;

        return false;
    }

    private static boolean rowOfSquaresIsInvalid(Rows top, int[][] grid) {
        switch (top){
            case TOP:
                if (squaresIsInvalid(0, 0, grid)) return true;
                if (squaresIsInvalid(0, 3, grid)) return true;
                if (squaresIsInvalid(0, 6, grid)) return true;
                return false;
            case MIDDLE:
                if (squaresIsInvalid(3, 0, grid)) return true;
                if (squaresIsInvalid(3, 3, grid)) return true;
                if (squaresIsInvalid(3, 6, grid)) return true;
                return false;

            case END:
                if (squaresIsInvalid(6, 0, grid)) return true;
                if (squaresIsInvalid(6, 3, grid)) return true;
                if (squaresIsInvalid(6, 6, grid)) return true;
                return false;

            default:
                return false;
        }
    }

    private static boolean squaresIsInvalid(int i, int i1, int[][] grid) {
        int yIndexEnd = i1 + 3;
        int xIndexEnd = i + 3;

        List<Integer> square = new ArrayList<>();

        while (i1 < yIndexEnd){
            while (i < xIndexEnd){
                square.add(
                        grid[i][i1]
                );
                i++;
            }
            i -= 3;

            i1++;
        }
        if (collectionHasRepeats(square)) return true;
        return false;
    }

    private static boolean collectionHasRepeats(List<Integer> square) {
        for (int index = 0; index <= GRID_BOUNDARY ; index++) {
            if (Collections.frequency(square, index) > 1) return true;
        }
        return false;
    }

    private static boolean tilesAreNotFilled(int[][] grid) {
        for (int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++) {
            for (int yIndex = 0; yIndex < GRID_BOUNDARY ; yIndex++) {
                if (grid[xIndex][yIndex] == 0) return true;
            }
        }
        return false;
    }


}
