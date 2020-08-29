package Sudoku.computationalLogic;

import Sudoku.problemdomain.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static Sudoku.problemdomain.SudokuGame.GRID_BOUNDARY;

public class GameGenerator {
    public static int[][] getNewGameGrid(){
        return unsolveGame(getSolvedGame());
    }

    private static int[][] unsolveGame(int[][] solvedGame){
        Random random = new Random(System.currentTimeMillis());

        boolean solvable = false;
        int[][] solvableArray =new int[GRID_BOUNDARY][GRID_BOUNDARY];

        while (solvable == false){
            SudokuUtilities.copySudokuArrayValues(solvedGame, solvableArray);

            int index = 0;

            while (index < 40){
                int xCoordinate = random.nextInt(GRID_BOUNDARY);
                int yCoordinate = random.nextInt(GRID_BOUNDARY);

                if (solvableArray[xCoordinate][yCoordinate] != 0){
                    solvableArray[xCoordinate][yCoordinate] = 0;
                    index++;
                }
            }

            int[][] toBeSolved = new int[GRID_BOUNDARY][GRID_BOUNDARY];
            SudokuUtilities.copySudokuArrayValues(solvableArray, toBeSolved);

            solvable = SudokuSolver.puzzleIsSolvable(toBeSolved);

        }
        return solvableArray ;
    }


    private static int[][] getSolvedGame() {
        Random random = new Random(System.currentTimeMillis());
        int[][] newGrid =new int[GRID_BOUNDARY][GRID_BOUNDARY];

        for (int value = 1; value <= GRID_BOUNDARY ; value++) {
            int alloc = 0; //tracks the number of times the number of given value is allocated
            int interrupt = 0; // number of times we attempt to allocate the number

            List<Coordinates> allocTracker = new ArrayList<>();

            int attempts = 0;

            while (alloc < GRID_BOUNDARY){
                if (interrupt > 200){         //Backtraking....
                    allocTracker.forEach(coord ->{
                        newGrid[coord.getX()][coord.getY()] = 0;
                    });

                    interrupt = 0;
                    alloc = 0;
                    allocTracker.clear();
                    attempts++;

                    if (attempts > 500){
                        clearArray(newGrid);
                        attempts = 0;
                        value = 1;
                    }
                }
                int xCoordinate = random.nextInt(GRID_BOUNDARY);
                int yCoordinate = random.nextInt(GRID_BOUNDARY);

                if (newGrid[xCoordinate][yCoordinate] == 0){
                    newGrid[xCoordinate][yCoordinate] = value; //we allocate the value to it

                    if (GameLogic.sudokuIsInvalid(newGrid)){ //we immediately check if game is valid or not
                        newGrid[xCoordinate][yCoordinate] = 0;
                        interrupt++;
                    }else {
                        allocTracker.add(new Coordinates(xCoordinate,yCoordinate));
                        alloc++;
                    }
                }
            }
        }
        return newGrid;
    }

    private static void clearArray(int[][] newGrid) {
        for (int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex++){
            for (int yIndex = 0; yIndex < GRID_BOUNDARY; yIndex++){
                newGrid[xIndex][yIndex] = 0;
            }
        }
    }
}

