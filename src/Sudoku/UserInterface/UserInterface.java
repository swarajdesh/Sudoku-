package Sudoku.UserInterface;

import Sudoku.problemdomain.Coordinates;
import Sudoku.problemdomain.SudokuGame;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.util.HashMap;

public class UserInterface implements IUserInterface.View,
        EventHandler<KeyEvent> {

       private final Stage stage; //Stage comes from JavaFx it is like background window
       private final Group group; //conatainer of some sort

    //how do we keep track of 81 text fields??
    //We use hashmap to  keep track of 81 text fields
        private HashMap<Coordinates, SudokuTextField> textFieldCoordinates;

        //Eventlistner class is like the controller of the presenter
        private IUserInterface.EventListner listner;

        private static final double WINDOW_Y = 732;
        private static final double WINDOW_X = 668;
        private static final double BOARD_PADDING = 50;
        private static final double BOARD_X_AND_Y = 576;

        private static final Color WINDOW_BACKGROUND_COLOR = Color.rgb(0,150,136);
        private static final Color BOARD_BACKGROUND_COLOR = Color.rgb(224,242,241);
        private static final String SUDOKU = "SUDOKU";

    public UserInterface(Stage stage) {
        this.stage = stage;
        this.group = new Group();
        this.textFieldCoordinates = new HashMap<>();
        initializeUserInterface();
    }

    private void initializeUserInterface() {
        drawBackground(group);
        drawTitle(group);
        drawSudokuBoard(group);
        drawTextFields(group);
        drawgridLines(group);
        stage.show();
    }
    private void drawTitle(Group group) {
        Text title = new Text(235,690,SUDOKU);
        title.setFill(Color.WHITE);
        Font titleFont = new Font(43);
        title.setFont(titleFont);
        group.getChildren().add(title);
    }

    private void drawTextFields(Group group) {
    final int Xorigin = 50;
    final int Yorigin = 50;

    final int XandYDelta = 64;

    //O(n^2)Runtime Complexity
        for(int xIndex = 0; xIndex < 9; xIndex++){
            for(int yIndex = 0;yIndex < 9; yIndex++){
                int x = Xorigin + xIndex * XandYDelta;
                int y = Yorigin + yIndex * XandYDelta;

                SudokuTextField tile = new SudokuTextField(xIndex,yIndex);


                styleSudokuTile(tile, x, y);

                tile.setOnKeyPressed(this);

                textFieldCoordinates.put(new Coordinates(xIndex,yIndex), tile);

                group.getChildren().add(tile);
            }
        }
    }

    private void styleSudokuTile(SudokuTextField tile, int x, int y) {
          Font numberFont = new Font(32);

          tile.setFont(numberFont);
          tile.setAlignment(Pos.CENTER);
          tile.setLayoutX(x);
          tile.setLayoutY(y);
          tile.setPrefHeight(64);
          tile.setPrefWidth(64);

          tile.setBackground(Background.EMPTY);

    }

    private void drawSudokuBoard(Group group) {

        Rectangle boardBackground = new Rectangle();
        boardBackground.setX(BOARD_PADDING);
        boardBackground.setY(BOARD_PADDING);

        boardBackground.setWidth(BOARD_X_AND_Y);
        boardBackground.setHeight(BOARD_X_AND_Y);

        boardBackground.setFill(BOARD_BACKGROUND_COLOR);

        group.getChildren().add(boardBackground);
    }

    private void drawgridLines(Group group) {
        int XandY = 114;
        int index = 0;
        while(index < 8){
            int thickness; //grid lines which separate the squares are more thicker
            if (index == 2 || index == 5 ){
                thickness = 3;
            }  else {
                thickness = 2;
            }

            Rectangle verticalLine = getLine(
                    XandY + 64*index,
                    BOARD_PADDING,
                    BOARD_X_AND_Y,
                    thickness
            );

            Rectangle horizontalLine = getLine(
                    XandY + 64*index,
                    BOARD_PADDING,
                    BOARD_X_AND_Y,
                    thickness
            );

            group.getChildren().addAll(
                    verticalLine,
                    horizontalLine
            );
            index++;
        }
    }

    private Rectangle getLine(double x,
                              double y,
                              double height,
                              double width) {
        Rectangle line = new Rectangle();

        line.setX(x);
        line.setY(y);
        line.setHeight(height);
        line.setWidth(width);

        line.setFill(Color.BLACK);
    return line;
    }

    private void drawBackground(Group group) {

        Scene scene = new Scene(group, WINDOW_X, WINDOW_Y);
        scene.setFill(WINDOW_BACKGROUND_COLOR);
        stage.setScene(scene);
    }

    @Override
    public void setListner(IUserInterface.EventListner eventListner) {
        this.listner =listner;
    }

    @Override
    public void updatSquare(int x, int y, int input) {
        SudokuTextField tile = textFieldCoordinates.get(new Coordinates(x,y));

        String value = Integer.toString(input);

        if (value.equals("0")) value = "";

        tile.textProperty().setValue(value);
    }

    @Override
    public void updateBoard(SudokuGame game) {
        for (int xIndex = 0; xIndex < 9; xIndex++) {
            for (int yIndex = 0; yIndex < 9; yIndex++) {
                TextField tile = textFieldCoordinates.get(new Coordinates(xIndex, yIndex));

                String value;
                value = Integer.toString(
                        game.getGridState()[xIndex][yIndex]
                );

                if (value.equals("")) {
                    tile.setStyle("-fx-opacity: 1;");
                    tile.setDisable(false);
                } else {
                    tile.setStyle("-fx-opacity:0.8;");
                    tile.setDisable(true);
                }
            }
        }
    }

    @Override
    public void showDialog(String message) {

        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        dialog.showAndWait();

        if (dialog.getResult() == ButtonType.OK) listner.onDialogClick();
    }

    @Override
    public void showError(String message) {

        Alert dialog = new Alert(Alert.AlertType.ERROR,message,ButtonType.OK);
        dialog.showAndWait();
    }

    @Override
    public void handle(KeyEvent keyEvent) {
            if (keyEvent.getEventType()==KeyEvent.KEY_PRESSED){
                if(
                        keyEvent.getText().matches("[0-9]")
                ){
                    int value = Integer.parseInt(keyEvent.getText());
                    handelInput(value, keyEvent.getSource());
                }else if (keyEvent.getCode() == KeyCode.BACK_SPACE){
                    handelInput(0, keyEvent.getSource());
                }else {
                    ((TextField) keyEvent.getSource()).setText("");
                }
            }
            keyEvent.consume();
    }

    private void handelInput(int value, Object source) {
        listner.onSudokuInput(
                ((SudokuTextField)source).getX(),
                ((SudokuTextField)source).getY(),
                value
        );
    }
}
