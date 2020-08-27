package Sudoku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SudokuApplication extends Application {

    private UserInterface.View uiImpl;
    @Override
    public void start(Stage primaryStage) throws Exception{

        uiImpl = new UserInterface(primaryStage);
        try{
            SudokuLogic.build(uiImpl);
        }catch (IOException e){
            e.printStackTrace();
            throw e;
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
