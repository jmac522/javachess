import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
// TODO: Methods/logic for making tiny piece icons of captured pieces, 2X2 gridpane for view, arraylist of captured
// piece types for data representation 
public class GameDriver extends Application {
    // TODO: Extract as much as possible into an object to keep track of the GUI elements throughout a game
    public static BoardSquare selectedPieceSquare = null;
    public static Board activeGameBoard = Board.createInitialPosition(false);
    public static Side sideToMove = activeGameBoard.getCurrentPlayer().getSide();
    public static GridPane pane;
    public static GridPane movesPane = new GridPane();
    public static int moveCounter = 1;
    public static Text bottomText;
    public static Alert mateAlert = new Alert(Alert.AlertType.CONFIRMATION);
    public static Alert newGameConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
    public static Alert playerInCheckMessage = new Alert(Alert.AlertType.WARNING);
    public static RadioMenuItem selectedMode;
    public static RadioMenuItem selectedDifficulty;
    public static ToggleGroup difficultyToggleGroup;
    public static ToggleGroup modeToggleGroup;
    public static boolean isVsComputer = false;

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException{
        setup();
        // Grid pane holding the active gameboard
        pane = new GridPane();
        for (int i = 0; i < 64; i++) {
            pane.add(activeGameBoard.getBoardSquare(i), (i % 8), (i / 8));
        }
        pane.setPadding(new Insets(5));


        // Grid pane which will hold the list of moves that have been executed so far

//        for (int i = 0; i < 32; i++) {
//            movesPane.add(new MovesTableCell(), (i % 2), (i / 2));
//        }

        movesPane.setPadding(new Insets(5, 5, 5, 5));


        // Border pane to hold board in center, moves on left, options on top, text info on bottom
        BorderPane borderPane = new BorderPane();
        // Center will hold the game board
        borderPane.setCenter(pane);


        // left will hold the list of moves
        borderPane.setLeft(movesPane);




        // top will have menu items for new game, etc.
        MenuBar menuBar = new MenuBar();
        Menu mainMenu = new Menu("Menu");
        // submenu holding game mode
        Menu modeSubMenu = new Menu("Game Mode");
        // submenu holding difficulty
        Menu difficultySubMenu = new Menu("Computer Difficulty");
        // menu item to start a new game
        MenuItem newGame = new MenuItem("New Game");
        newGame.setOnAction(e -> newGame());


        // Mode Selection items and Toggle group
        // TODO: refactor strings into enums with toString or Pretty print method
        RadioMenuItem humanVsHuman = new RadioMenuItem("Human vs Human");
        humanVsHuman.setSelected(true);
        humanVsHuman.setOnAction(e -> modeSelected(humanVsHuman));
        selectedMode = humanVsHuman;
        RadioMenuItem humanVsComputer = new RadioMenuItem("Human vs Computer");
        humanVsComputer.setOnAction(e -> modeSelected(humanVsComputer));
        RadioMenuItem computerVsComputer = new RadioMenuItem("Computer vs Computer");
        computerVsComputer.setOnAction(event -> modeSelected(computerVsComputer));

        modeToggleGroup = new ToggleGroup();
        modeToggleGroup.getToggles().add(humanVsHuman);
        modeToggleGroup.selectToggle(humanVsHuman);
        modeToggleGroup.getToggles().add(humanVsComputer);
        modeToggleGroup.getToggles().add(computerVsComputer);


        // Difficulty selection menu items and toggle group
        RadioMenuItem lowDifficulty = new RadioMenuItem("Low");
        lowDifficulty.setSelected(true);
        selectedDifficulty = lowDifficulty;
        RadioMenuItem mediumDifficulty = new RadioMenuItem("Medium");
        RadioMenuItem highDifficulty = new RadioMenuItem("High");

        difficultyToggleGroup = new ToggleGroup();
        difficultyToggleGroup.getToggles().add(lowDifficulty);
        difficultyToggleGroup.getToggles().add(mediumDifficulty);
        difficultyToggleGroup.getToggles().add(highDifficulty);

        modeSubMenu.getItems().addAll(humanVsHuman, humanVsComputer, computerVsComputer);
        difficultySubMenu.getItems().addAll(lowDifficulty, mediumDifficulty, highDifficulty);
        mainMenu.getItems().addAll(newGame, modeSubMenu, difficultySubMenu);
        menuBar.getMenus().add(mainMenu);
        menuBar.getStyleClass().add("top-menu");
        borderPane.setTop(menuBar);




        // Bottom box will hold text info on side to move, etc.
        HBox bottomBox = new HBox();
        bottomBox.setAlignment(Pos.CENTER_LEFT);
        bottomBox.setPadding(new Insets(10));
        bottomText = new Text();
        setBottomText(Side.WHITE.turnMessage());
        bottomBox.getChildren().add(bottomText);
        borderPane.setBottom(bottomBox);

        File cssFile = new File("style.css");
        Scene scene = new Scene(borderPane, 800, 800);
        scene.getStylesheets().clear();
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Java Chess");
        primaryStage.show();
    }

    private void modeSelected(RadioMenuItem selectedItem) {
        String selectedString = selectedItem.getText();
        if (selectedString == "Human vs Human") {
            throwNewGameConfirmation();
            isVsComputer = false;
            newGame();
        } else if (selectedString == "Human vs Computer") {
            throwNewGameConfirmation();
            isVsComputer = true;
            newGame();
        } else {
            throwNewGameConfirmation();
            isVsComputer = true;
            newGame();
        }
    }

    private void throwNewGameConfirmation() {
        Optional<ButtonType> result = newGameConfirmation.showAndWait();
        if (result.get() == ButtonType.OK){
//            newGame(true);
        } else {
            // ... user chose CANCEL or closed the dialog
            System.out.println("cancelled");
        }
    }

    private void setupNewGameConfirmation() {
        newGameConfirmation.setTitle("Are You Sure?");
        newGameConfirmation.setHeaderText("This action will start a new game.");
        newGameConfirmation.setContentText("Do you want to continue?");
    }

    public static void throwPlayerInCheckMessage() {
        playerInCheckMessage.showAndWait();
    }

    private void setupPlayerInCheckMessage() {
        playerInCheckMessage.setTitle("Warning");
        playerInCheckMessage.setHeaderText("Illegal Move!");
        playerInCheckMessage.setContentText("You're in Check!");
    }

    // General setup method for preparing certain elements
    private void setup() {
        GameUtilities.setUpMateAlert(mateAlert);
        setupNewGameConfirmation();
        setupPlayerInCheckMessage();
        setupMovesPane();
    }


    public static void drawUpdatedBoard() {
        pane.getChildren().clear();
        for (int i = 0; i < 64; i++) {
            pane.add(activeGameBoard.getBoardSquare(i), (i % 8), (i / 8));
        }
    }

    public static void newGame() {
        activeGameBoard = Board.createInitialPosition(isVsComputer);
        drawUpdatedBoard();
        sideToMove = Side.WHITE;
        bottomText.setText("White to Move!");
        moveCounter = 1;
        movesPane.getChildren().clear();
        setupMovesPane();
    }

    private static void setupMovesPane() {
        MovesTableCell whiteCell = new MovesTableCell();
        whiteCell.setText("White");
        whiteCell.setStyle("-fx-font: 10 arial;");

        MovesTableCell blackCell = new MovesTableCell();
        blackCell.setText("Black");
        blackCell.setStyle("-fx-font: 10 arial;");

        movesPane.add(whiteCell, 0, 0);
        movesPane.add(blackCell, 1, 0);
    }

    public static void blankBoard(GridPane pane) {

    }

    public static void setBottomText(String text) {
        bottomText.setText(text);
        bottomText.setTextAlignment(TextAlignment.CENTER);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
